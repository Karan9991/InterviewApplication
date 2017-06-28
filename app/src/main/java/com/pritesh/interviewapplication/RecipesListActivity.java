package com.pritesh.interviewapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.pritesh.interviewapplication.data.food.Recipes;
import com.pritesh.interviewapplication.network.ApiClient;
import com.pritesh.interviewapplication.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesListActivity extends AppCompatActivity
{
    final static String TAG = RecipesListActivity.class.getCanonicalName();
    final String API_KEY_FOOD3FORK = "daa96adf20a389f3b63634535ec8a938";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_list);
        getRetrofitSupport();
    }

    private void getRetrofitSupport()
    {
        ApiInterface apiService =
                ApiClient.getClientFoodFork().create(ApiInterface.class);

        Call<List<Recipes>> apiServiceData = apiService.getAllRecepies(API_KEY_FOOD3FORK);

        apiServiceData.enqueue(new Callback<List<Recipes>>()
        {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response)
            {
                List<Recipes>recipesList = response.body();
                Log.d(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t)
            {
                Log.d(TAG, "onFailure");
            }
        });
    }
}
