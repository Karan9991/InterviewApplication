package com.pritesh.interviewapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.pritesh.interviewapplication.adapter.DataAdapter;
import com.pritesh.interviewapplication.data.DataModel;

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

public class MainActivity extends AppCompatActivity
{
    private  String TAG = MainActivity.class.getCanonicalName();
    ListView lstData;
    DataAdapter mDataAdapter;
    ProgressDialog mProgressDialog;
    ArrayList<DataModel> mArrayDataList;
    String BASE_URL = "https://s3.amazonaws.com/mobile-tor/test/images.json";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lstData = (ListView)findViewById(R.id.lstData);
        mArrayDataList = new ArrayList<>();
        new DownloadData().execute(BASE_URL);

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
                    DataModel dm = new DataModel();
                    dm.setText(post.getString("text"));
                    dm.setUrl(post.getString("url"));

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
