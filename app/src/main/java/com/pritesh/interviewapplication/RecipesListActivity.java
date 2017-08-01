package com.pritesh.interviewapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.pritesh.interviewapplication.adapter.RecipeRecyclerViewAdapter;
import com.pritesh.interviewapplication.data.food.RecipeItem;
import com.pritesh.interviewapplication.data.food.Recipes;
import com.pritesh.interviewapplication.network.ApiClient;
import com.pritesh.interviewapplication.network.ApiInterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesListActivity extends AppCompatActivity
{
    final static String TAG = RecipesListActivity.class.getCanonicalName();
    final String API_KEY_FOOD3FORK = "daa96adf20a389f3b63634535ec8a938";

    RecyclerView mRecyclerView;
    RecipeRecyclerViewAdapter mDataRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_list);
        mRecyclerView = (RecyclerView)findViewById(R.id.rvDataRecipe);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(RecyclerViewDataActivity.this, 3);
        //mRecyclerView.setLayoutManager(gridLayoutManager);
        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        getRetrofitSupport();
    }

    private void getRetrofitSupport()
    {
        ApiInterface apiService =
                ApiClient.getClientFoodFork().create(ApiInterface.class);
        Map<String, String> data = new HashMap<>();
        data.put("key", API_KEY_FOOD3FORK);
        Call<Recipes> apiServiceData = apiService.getAllRecepies(data);

        apiServiceData.enqueue(new Callback<Recipes>()
        {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response)
            {
                //Recipes recipesList = response.body();
                List<RecipeItem> mArrayDataList = response.body().getRecipes();
                mDataRecyclerViewAdapter = new RecipeRecyclerViewAdapter(RecipesListActivity.this,mArrayDataList);
                mRecyclerView.setAdapter(mDataRecyclerViewAdapter);
                Log.d(TAG, "onResponse: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t)
            {
                Log.d(TAG, "onFailure :" );
                t.printStackTrace();
            }
        });
    }
}
