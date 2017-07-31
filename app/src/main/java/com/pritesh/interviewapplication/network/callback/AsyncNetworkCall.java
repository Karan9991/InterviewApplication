package com.pritesh.interviewapplication.network.callback;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by pritesh.patel on 2017-07-31, 2:53 PM.
 * ADESA, Canada
 */

public class AsyncNetworkCall extends AsyncTask<String, Void, String>
{
    NetworkResponse mNetworkResponse;
    public AsyncNetworkCall(NetworkResponse mNetworkResponse)
    {
        this.mNetworkResponse = mNetworkResponse;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... urls)
    {
        String dataUrl = urls[0];

        String jsonString;
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

            if (statusCode == 200)
            {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                jsonString = convertInputStreamToString(inputStream);
                return jsonString;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result)
    {
        if(result != null)
        {
            mNetworkResponse.onSuccess(result);
        }
        else
        {
            mNetworkResponse.onError("Failed to fetch data!!!");
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

        inputStream.close();

        return result;
    }
}
