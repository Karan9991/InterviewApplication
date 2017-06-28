package com.pritesh.interviewapplication.data.food;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Author: pritesh.patel
 * Created by: ModelGenerator on 2017-06-23
 */
public class Recipes
{
    @SerializedName("count")
    public int count;
    @SerializedName("recipes")
    public ArrayList<RecipeItem> recipes;
}