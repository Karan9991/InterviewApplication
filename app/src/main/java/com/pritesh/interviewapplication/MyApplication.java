package com.pritesh.interviewapplication;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by pritesh.patel on 2017-08-02, 9:38 AM.
 * ADESA, Canada
 */

public class MyApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);
    }
}
