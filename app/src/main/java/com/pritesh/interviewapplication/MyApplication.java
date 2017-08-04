package com.pritesh.interviewapplication;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by pritesh.patel on 2017-08-02, 9:38 AM.
 * ADESA, Canada
 */

public class MyApplication extends Application
{
    String mUserEmail;
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);
    }

    public void setLoggedInUserEmail(String mUserEmail)
    {
        this.mUserEmail = mUserEmail;
    }

    public String getLoggedInUserEmail()
    {
        return this.mUserEmail;
    }
}
