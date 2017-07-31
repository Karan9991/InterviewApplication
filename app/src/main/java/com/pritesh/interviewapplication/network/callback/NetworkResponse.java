package com.pritesh.interviewapplication.network.callback;

/**
 * Created by pritesh.patel on 2017-07-31, 2:52 PM.
 * ADESA, Canada
 */

public interface NetworkResponse
{
    void onSuccess(String response);
    void onError(String errorMessage);
}
