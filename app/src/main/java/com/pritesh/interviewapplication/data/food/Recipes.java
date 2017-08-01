package com.pritesh.interviewapplication.data.food;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Author: pritesh.patel
 * Created by: ModelGenerator on 2017-06-23
 */
public class Recipes
{
    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public List<RecipeItem> getRecipes()
    {
        return recipes;
    }

    public void setRecipes(List<RecipeItem> recipes)
    {
        this.recipes = recipes;
    }

    @SerializedName("count")
    private int count;
    @SerializedName("recipes")
    private List<RecipeItem> recipes;
}