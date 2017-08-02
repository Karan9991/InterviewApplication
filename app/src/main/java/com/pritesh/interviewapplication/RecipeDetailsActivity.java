package com.pritesh.interviewapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pritesh.interviewapplication.data.food.Favorite;
import com.pritesh.interviewapplication.data.food.RecipeItem;

import io.realm.Realm;
import io.realm.exceptions.RealmException;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;

public class RecipeDetailsActivity extends AppCompatActivity
{

    TextView txtPublisher, txtRatings;
    ImageView mImageViewRecipe;
    RecipeItem mRecipeItem;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        mRecipeItem = (RecipeItem)getIntent().getSerializableExtra("recipe");

        this.setTitle(mRecipeItem.getTitle());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Back Button support
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final View mView = view;
                final Realm realmFavorite = Realm.getDefaultInstance();
                final Favorite mFavorite = new Favorite();
                mFavorite.setfFUrl(mRecipeItem.getfFUrl());
                mFavorite.setImageUrl(mRecipeItem.getImageUrl());
                mFavorite.setPublisher(mRecipeItem.getPublisher());
                mFavorite.setPublisherUrl(mRecipeItem.getPublisherUrl());
                mFavorite.setRecipeId(mRecipeItem.getRecipeId());
                mFavorite.setSocialRank(mRecipeItem.getSocialRank());
                mFavorite.setTitle(mRecipeItem.getTitle());
                mFavorite.setSourceUrl(mRecipeItem.getSourceUrl());

                realmFavorite.executeTransaction(new Realm.Transaction()
                {
                    @Override
                    public void execute(Realm realm)
                    {
                        try
                        {
                            // This will create a new object in Realm or throw an exception if the
                            // object already exists (same primary key)
                            realmFavorite.copyToRealm(mFavorite);
                            Snackbar.make(mView, "Add to Favourite list", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();

                            // This will update an existing object with the same primary key
                            // or create a new object if an object with no primary key
                            //realmUser.copyToRealmOrUpdate(mUser);
                        } catch(RealmPrimaryKeyConstraintException | RealmException re)
                        {
                            Snackbar.make(mView, "Item already in favorite list", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                        }

                    }
                });
                realmFavorite.close();
            }
        });

        mImageViewRecipe = (ImageView)findViewById(R.id.ivRecipe);
        Glide.with(this).load(mRecipeItem.getImageUrl())
                .thumbnail(1)
                .into(mImageViewRecipe);

        txtRatings = (TextView) findViewById(R.id.txtRatings);
        txtRatings.setText("Ratings : " + mRecipeItem.getSocialRank());

        txtPublisher = (TextView) findViewById(R.id.txtPublisher);
        txtPublisher.setText("Publisher : " + mRecipeItem.getPublisher());

    }

}
