package com.pritesh.interviewapplication.network.callback;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by pritesh.patel on 2017-07-31, 2:53 PM.
 * ADESA, Canada
 */

public class AsyncNetworkCall extends AsyncTask<String, Void, String>
{
    private static final  String TAG = AsyncNetworkCall.class.getName();
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

        byte [] compressed = compress(result);
        result = decompress(compressed);
        return result;
    }

    byte[] compress(String string) throws IOException {
        Log.d(TAG, "compress start");
        ByteArrayOutputStream os = new ByteArrayOutputStream(string.length());
        GZIPOutputStream gos = new GZIPOutputStream(os);
        gos.write(string.getBytes());
        gos.close();
        byte[] compressed = os.toByteArray();
        os.close();
        Log.d(TAG, "compress end");
        return compressed;
    }

    public String decompress(byte[] compressed) throws IOException {
        Log.d(TAG, "decompress start");
        final int BUFFER_SIZE = 32;
        ByteArrayInputStream is = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(is, BUFFER_SIZE);
        StringBuilder string = new StringBuilder();
        byte[] data = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = gis.read(data)) != -1) {
            string.append(new String(data, 0, bytesRead));
        }
        gis.close();
        is.close();
        Log.d(TAG, "decompress ends");
        return string.toString();
    }
}
