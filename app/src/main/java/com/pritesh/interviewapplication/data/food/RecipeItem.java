package com.pritesh.interviewapplication.data.food;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: pritesh.patel
 * Created by: ModelGenerator on 2017-06-23
 */
public class RecipeItem implements Serializable
{
    public String getPublisher()
    {
        return publisher;
    }

    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    public String getfFUrl()
    {
        return fFUrl;
    }

    public void setfFUrl(String fFUrl)
    {
        this.fFUrl = fFUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSourceUrl()
    {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl)
    {
        this.sourceUrl = sourceUrl;
    }

    public String getRecipeId()
    {
        return recipeId;
    }

    public void setRecipeId(String recipeId)
    {
        this.recipeId = recipeId;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public double getSocialRank()
    {
        return socialRank;
    }

    public void setSocialRank(double socialRank)
    {
        this.socialRank = socialRank;
    }

    public String getPublisherUrl()
    {
        return publisherUrl;
    }

    public void setPublisherUrl(String publisherUrl)
    {
        this.publisherUrl = publisherUrl;
    }

    @SerializedName("publisher")
    private String publisher;
    @SerializedName("f2f_url")
    private String fFUrl;
    @SerializedName("title")
    private String title;
    @SerializedName("source_url")
    private String sourceUrl;
    @SerializedName("recipe_id")
    private String recipeId;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("social_rank")
    private double socialRank;
    @SerializedName("publisher_url")
    private String publisherUrl;
}