package com.pritesh.interviewapplication.data;

import com.google.gson.annotations.SerializedName;

/**
 * Created by pritesh.patel on 2017-05-23, 2:02 PM.
 * ADESA, Canada
 */

public class DataModel
{
    @SerializedName("text")
    public String text;
    @SerializedName("url")
    public String url;
    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }


}
