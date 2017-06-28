package com.pritesh.interviewapplication.data.food;

import com.google.gson.annotations.SerializedName;

/**
 * Author: pritesh.patel
 * Created by: ModelGenerator on 2017-06-23
 */
public class RecipeItem
{
    @SerializedName("publisher")
    public String publisher;
    @SerializedName("fFUrl")
    public String fFUrl;
    @SerializedName("title")
    public String title;
    @SerializedName("sourceUrl")
    public String sourceUrl;
    @SerializedName("recipeId")
    public String recipeId;
    @SerializedName("imageUrl")
    public String imageUrl;
    @SerializedName("socialRank")
    public double socialRank;
    @SerializedName("publisherUrl")
    public String publisherUrl;
}