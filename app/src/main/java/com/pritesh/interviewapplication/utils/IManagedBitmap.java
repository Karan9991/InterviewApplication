package com.pritesh.interviewapplication.utils;

import android.graphics.Bitmap;

public interface IManagedBitmap
{
    Bitmap getBitmap();

    void recycle();

    IManagedBitmap retain();
}