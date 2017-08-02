package com.pritesh.interviewapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.pritesh.interviewapplication.adapter.FavoriteRecipeRecyclerViewAdapter;
import com.pritesh.interviewapplication.data.food.Favorite;

import io.realm.Realm;
import io.realm.RealmResults;

public class FavoriteRecipeListActivity extends Activity
{
    final static String TAG = FavoriteRecipeListActivity.class.getCanonicalName();
    RecyclerView mRecyclerView;
    FavoriteRecipeRecyclerViewAdapter mFavoriteRecipeRecyclerViewAdapter;
    RealmResults<Favorite> mFavoriteList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_recipe_list);

        mRecyclerView = (RecyclerView)findViewById(R.id.rvFavoriteRecipes);

        getAllFavoriteRecipes();
    }

    private void getAllFavoriteRecipes()
    {
        Realm realmFavorite = Realm.getDefaultInstance();
        mFavoriteList = realmFavorite.where(Favorite.class).findAll();
        if(mFavoriteList.size() == 0)
        {
            Toast.makeText(FavoriteRecipeListActivity.this,"No recipes in favorite list", Toast.LENGTH_LONG).show();
        }
        else
        {
            mFavoriteRecipeRecyclerViewAdapter = new FavoriteRecipeRecyclerViewAdapter(FavoriteRecipeListActivity.this,mFavoriteList);
            LinearLayoutManager llm = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setAdapter(mFavoriteRecipeRecyclerViewAdapter);
        }
    }
}
