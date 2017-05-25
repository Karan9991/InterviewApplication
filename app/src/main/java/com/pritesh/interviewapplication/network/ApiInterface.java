package com.pritesh.interviewapplication.network;

/**
 * Created by pritesh.patel on 2017-05-25, 12:35 PM.
 * ADESA, Canada
 */

import com.pritesh.interviewapplication.data.AllData;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiInterface {
    @GET("test/images.json")
    Call<AllData> getData();
}