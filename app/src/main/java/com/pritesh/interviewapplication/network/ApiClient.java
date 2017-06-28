package com.pritesh.interviewapplication.network;

/**
 * Created by pritesh.patel on 2017-05-25, 12:33 PM.
 * ADESA, Canada
 */

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final String BASE_URL = "https://s3.amazonaws.com/mobile-tor/";
    private static final String BASE_URL_5000 = "https://jsonplaceholder.typicode.com";
    private static final String BASE_URL_FOOD2FORK = "http://food2fork.com/api/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient5000() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_5000)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClientFoodFork() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_FOOD2FORK)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
