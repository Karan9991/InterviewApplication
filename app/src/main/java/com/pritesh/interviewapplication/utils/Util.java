package com.pritesh.interviewapplication.utils;

/**
 * Created by pritesh.patel on 2017-05-25, 12:11 PM.
 * ADESA, Canada
 */

public class Util
{
    public static String replaceHttps(String url)
    {
        if(url != null && url.startsWith("https://"))
            url = url.replaceAll("https://", "http://");
        return url;
    }
}
