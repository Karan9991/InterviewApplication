package com.pritesh.interviewapplication.data.food;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Favorite extends RealmObject
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


    private String publisher;
    private String fFUrl;
    private String title;
    private String sourceUrl;
    @PrimaryKey
    private String recipeId;
    private String imageUrl;
    private double socialRank;
    private String publisherUrl;
}