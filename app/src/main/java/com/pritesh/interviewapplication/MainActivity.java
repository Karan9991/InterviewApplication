package com.pritesh.interviewapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pritesh.interviewapplication.adapter.DataAdapter;
import com.pritesh.interviewapplication.data.AllData;
import com.pritesh.interviewapplication.data.DataModel;
import com.pritesh.interviewapplication.data.DataModel5000;
import com.pritesh.interviewapplication.network.ApiClient;
import com.pritesh.interviewapplication.network.ApiInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    private  String TAG = MainActivity.class.getCanonicalName();
    ListView lstData;
    DataAdapter mDataAdapter;
    ProgressDialog mProgressDialog;
    ArrayList<DataModel> mArrayDataList;
    String BASE_URL = "https://s3.amazonaws.com/mobile-tor/test/images.json";
    String BASE_URL_5000 = "https://jsonplaceholder.typicode.com/photos";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstData = (ListView)findViewById(R.id.lstData);
        mArrayDataList = new ArrayList<>();

        //Traditional Async Call
        //new DownloadData().execute(BASE_URL);

        //Retrofit Call
        //getRetrofitSupport();
        getRetrofitSupport5000();

    }

    private void getRetrofitSupport()
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<AllData> apiServiceData = apiService.getData();
        apiServiceData.enqueue(new Callback<AllData>()
        {
            @Override
            public void onResponse(Call<AllData> call, Response<AllData> response)
            {
                mArrayDataList = response.body().images;
                mDataAdapter = new DataAdapter(MainActivity.this,mArrayDataList);
                lstData.setAdapter(mDataAdapter);
            }

            @Override
            public void onFailure(Call<AllData> call, Throwable t)
            {

            }
        });
    }

    private void getRetrofitSupport5000()
    {
        final ProgressDialog pd = ProgressDialog.show(this,"Fetch Data","Loading..");
        ApiInterface apiService =
                ApiClient.getClient5000().create(ApiInterface.class);

        Call<List<DataModel5000>> apiServiceData = apiService.getData5000();
        apiServiceData.enqueue(new Callback<List<DataModel5000>>()
        {
            @Override
            public void onResponse(Call<List<DataModel5000>> call, Response<List<DataModel5000>> response)
            {
                List<DataModel5000>dataModel5000 = response.body();
                mArrayDataList = new ArrayList<DataModel>();
                DataModel dtm = new DataModel();
                for (DataModel5000 dm: dataModel5000)
                {
                    dtm.setUrl(dm.getUrl());
                    dtm.setText(dm.getTitle());
                    mArrayDataList.add(dtm);
                }
                mDataAdapter = new DataAdapter(MainActivity.this,mArrayDataList);
                lstData.setAdapter(mDataAdapter);
                pd.hide();
                //Log.d(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<DataModel5000>> call, Throwable t)
            {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    // DownloadImage AsyncTask
    private class DownloadData extends AsyncTask<String, Void, String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Data");
            mProgressDialog.setMessage("Download...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... URL)
        {

            String dataUrl = URL[0];

            String jsonString = null;
            try
            {
                InputStream inputStream;
                HttpURLConnection urlConnection;

                URL url = new URL(dataUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.setRequestMethod("GET");
                int statusCode = urlConnection.getResponseCode();


                if (statusCode == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    jsonString = convertInputStreamToString(inputStream);
                    return jsonString;
                } else {
                    Log.d(TAG, "Failed to fetch data!!");
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            try {
                JSONObject response = new JSONObject(result);
                JSONArray posts = response.optJSONArray("images");

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.optJSONObject(i);

                    //Traditional Method to grab JSON data
                    //DataModel dm = new DataModel();
                    //dm.setText(post.getString("text"));
                    //dm.setUrl(post.getString("url"));

                    //Use Gson library to convert json string to POJO
                    Gson gson = new GsonBuilder().create();
                    DataModel dm = gson.fromJson(post.toString(), DataModel.class);

                    mArrayDataList.add(dm);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            mDataAdapter = new DataAdapter(MainActivity.this,mArrayDataList);
            lstData.setAdapter(mDataAdapter);
            mProgressDialog.dismiss();
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException
    {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";

        while ((line = bufferedReader.readLine()) != null)
        {
            result += line;
        }

        if (null != inputStream)
        {
            inputStream.close();
        }

        return result;
    }
}
