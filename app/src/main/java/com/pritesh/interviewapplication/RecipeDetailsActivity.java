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
import com.pritesh.interviewapplication.data.food.RecipeItem;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Add to Favourite list", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
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
