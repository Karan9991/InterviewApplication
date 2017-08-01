package com.pritesh.interviewapplication.network;

/**
 * Created by pritesh.patel on 2017-05-25, 12:35 PM.
 * ADESA, Canada
 */

import com.pritesh.interviewapplication.data.AllData;
import com.pritesh.interviewapplication.data.DataModel5000;
import com.pritesh.interviewapplication.data.food.Recipes;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;


public interface ApiInterface {
    @GET("/mobile-tor/test/images.json")
    Call<AllData> getData();

    @GET("/photos")
    Call<List<DataModel5000>> getData5000();

    //http://food2fork.com/api/search?key=daa96adf20a389f3b63634535ec8a938  //Default First 30 Products
    //http://food2fork.com/api/search?key=daa96adf20a389f3b63634535ec8a938&page=1 //Applying Paging
    //http://food2fork.com/api/search?key=daa96adf20a389f3b63634535ec8a938&page=1@sort=r //Ratings
    //http://food2fork.com/api/search?key=daa96adf20a389f3b63634535ec8a938&page=1@sort=7 //Trend
    @GET("/api/search")
    Call<Recipes> getAllRecepies(@QueryMap Map<String, String> options);

    //http://food2fork.com/api/search?key=daa96adf20a389f3b63634535ec8a938&q=shredded%20chicken
    @GET("/api/search")
    Call<List<Recipes>> searchRecepie(@Query("key") String apiKey,
                                      @Query("q") String searchString);
}