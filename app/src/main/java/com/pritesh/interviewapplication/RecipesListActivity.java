package com.pritesh.interviewapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.pritesh.interviewapplication.adapter.RecipeRecyclerViewAdapter;
import com.pritesh.interviewapplication.data.food.RecipeItem;
import com.pritesh.interviewapplication.data.food.Recipes;
import com.pritesh.interviewapplication.network.ApiClient;
import com.pritesh.interviewapplication.network.ApiInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipesListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener
{
    final static String TAG = RecipesListActivity.class.getCanonicalName();
    final String API_KEY_FOOD3FORK = "daa96adf20a389f3b63634535ec8a938";
    private SwipeRefreshLayout swipeContainer;

    RecyclerView mRecyclerView;
    RecipeRecyclerViewAdapter mDataRecyclerViewAdapter;
    List<RecipeItem> mArrayDataList;
    static int pageIndex = 1;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepie_list);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        mArrayDataList = new ArrayList<>();

        mRecyclerView = (RecyclerView)findViewById(R.id.rvDataRecipe);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

        //GridLayoutManager gridLayoutManager = new GridLayoutManager(RecyclerViewDataActivity.this, 3);
        //mRecyclerView.setLayoutManager(gridLayoutManager);

        //mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mDataRecyclerViewAdapter = new RecipeRecyclerViewAdapter(RecipesListActivity.this,mArrayDataList);
        mRecyclerView.setAdapter(mDataRecyclerViewAdapter);

        getRetrofitSupport();

        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRetrofitSupport();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);


    }

    private void getRetrofitSupport()
    {
        ApiInterface apiService =
                ApiClient.getClientFoodFork().create(ApiInterface.class);
        Map<String, String> data = new HashMap<>();
        data.put("key", API_KEY_FOOD3FORK);
        data.put("page", String.valueOf(pageIndex++));
        Call<Recipes> apiServiceData = apiService.getAllRecipes(data);

        apiServiceData.enqueue(new Callback<Recipes>()
        {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response)
            {
                //Recipes recipesList = response.body();
                mDataRecyclerViewAdapter.addAll(response.body().getRecipes());
                //Log.d(TAG, "onResponse: " + response.body().toString());
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t)
            {
                Log.d(TAG, "onFailure :" );
                t.printStackTrace();
            }
        });
    }

    private void getRecipeSearch(String searchQuery)
    {
        ApiInterface apiService =
                ApiClient.getClientFoodFork().create(ApiInterface.class);
        Map<String, String> data = new HashMap<>();
        data.put("key", API_KEY_FOOD3FORK);
        data.put("page", String.valueOf(pageIndex++));
        data.put("q", searchQuery);
        Call<Recipes> apiServiceData = apiService.getAllRecipes(data);

        apiServiceData.enqueue(new Callback<Recipes>()
        {
            @Override
            public void onResponse(Call<Recipes> call, Response<Recipes> response)
            {
                if(response.body().getRecipes().size() !=0)
                {
                    mDataRecyclerViewAdapter.clear();
                    mDataRecyclerViewAdapter.addAll(response.body().getRecipes());
                    swipeContainer.setRefreshing(false);
                }
                else
                {
                    Toast.makeText(RecipesListActivity.this, "No Data Found...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Recipes> call, Throwable t)
            {
                Log.d(TAG, "onFailure :" );
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement the filter logic
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        // Close the soft keyboard from a Test
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(searchView.getWindowToken(), 0);
        Log.d(TAG, "onQueryTextSubmit: "  + query);
        getRecipeSearch(query);
        return true;
    }

}
