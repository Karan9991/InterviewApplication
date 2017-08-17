package com.pritesh.interviewapplication

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.pritesh.interviewapplication.data.food.Favorite
import com.pritesh.interviewapplication.data.food.RecipeItem
import io.realm.Realm
import io.realm.exceptions.RealmException
import io.realm.exceptions.RealmPrimaryKeyConstraintException

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var txtPublisher: TextView
    private lateinit var txtRatings: TextView
    private lateinit var txtSourceUrl: TextView
    private lateinit var txtPublisherUrl: TextView
    private lateinit var mImageViewRecipe: ImageView
    private lateinit var mRecipeItem: RecipeItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)

        mRecipeItem = intent.getSerializableExtra("recipe") as RecipeItem

        this.title = mRecipeItem.title

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        //Back Button support
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            val realmFavorite = Realm.getDefaultInstance()
            val mFavorite = Favorite()
            mFavorite.setfFUrl(mRecipeItem.getfFUrl())
            mFavorite.imageUrl = mRecipeItem.imageUrl
            mFavorite.publisher = mRecipeItem.publisher
            mFavorite.publisherUrl = mRecipeItem.publisherUrl
            mFavorite.recipeId = mRecipeItem.recipeId
            mFavorite.socialRank = mRecipeItem.socialRank
            mFavorite.title = mRecipeItem.title
            mFavorite.sourceUrl = mRecipeItem.sourceUrl

            realmFavorite.executeTransaction {
                try {
                    // This will create a new object in Realm or throw an exception if the
                    // object already exists (same primary key)
                    realmFavorite.copyToRealm(mFavorite)
                    Snackbar.make(view, "Add to Favourite list", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show()

                    // This will update an existing object with the same primary key
                    // or create a new object if an object with no primary key
                    //realmUser.copyToRealmOrUpdate(mUser);
                } catch (re: RealmPrimaryKeyConstraintException) {
                    Snackbar.make(view, "Item already in favorite list", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show()
                } catch (re: RealmException) {
                    Snackbar.make(view, "Item already in favorite list", Snackbar.LENGTH_SHORT).setAction("Action", null).show()
                }
            }
            realmFavorite.close()
        }

        mImageViewRecipe = findViewById(R.id.ivRecipe) as ImageView
        Glide.with(this).load(mRecipeItem.imageUrl)
                .thumbnail(1f)
                .into(mImageViewRecipe)

        txtRatings = findViewById(R.id.txtRatings) as TextView
        txtRatings.text = "Social Ratings : " + mRecipeItem.socialRank

        txtPublisher = findViewById(R.id.txtPublisher) as TextView
        txtPublisher.text = "Publisher : " + mRecipeItem.publisher

        txtSourceUrl = findViewById(R.id.txtUrl) as TextView
        txtSourceUrl.isSelected = true
        txtSourceUrl.text = "Url : " + mRecipeItem.sourceUrl

        txtPublisherUrl = findViewById(R.id.txtPublisherUrl) as TextView
        txtPublisherUrl.isSelected = true
        txtPublisherUrl.text = "Publisher info : " + mRecipeItem.publisherUrl
    }

}
