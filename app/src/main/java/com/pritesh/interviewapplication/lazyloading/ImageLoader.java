package com.pritesh.interviewapplication.lazyloading;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.widget.ImageView;

import com.pritesh.interviewapplication.utils.BitmapPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageLoader
{
    MemoryCache memoryCache = new MemoryCache();
    FileCache fileCache;
    ExecutorService executorService;
    Handler handler = new Handler();
    BitmapPool bitmapool;
    private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    public ImageLoader(Context context)
    {
        fileCache = new FileCache(context);
        executorService = Executors.newFixedThreadPool(5);
        bitmapool = new BitmapPool(100, 100, Config.RGB_565);
    }

    public static Bitmap rotate(Bitmap b, int degrees)
    {
        if(degrees != 0 && b != null)
        {
            Matrix m = new Matrix();

            m.setRotate(degrees, (float)b.getWidth() / 2, (float)b.getHeight() / 2);
            try
            {
                Bitmap b2 = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), m, true);
                if(b != b2)
                {
                    b.recycle();
                    b = b2;
                }
            }
            catch(OutOfMemoryError ex)
            {
                throw ex;
            }
        }
        return b;
    }

    public void DisplayImage(String url, ImageView imageView)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if(bitmap != null)
        {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
        }
    }

    private void queuePhoto(String url, ImageView imageView)
    {
        PhotoToLoad p = new PhotoToLoad(url, imageView);

        executorService.submit(new PhotosLoader(p));
    }

    public void DisplayImageRotated(String url, ImageView imageView)
    {
        imageViews.put(imageView, url);
        Bitmap bitmap = memoryCache.get(url);
        if(bitmap != null)
        {
            imageView.setImageBitmap(bitmap);
        }
        else
        {
            queuePhoto(url, imageView);
        }
    }

    private Bitmap getBitmap(String url)
    {
        File f = fileCache.getFile(url);
        Bitmap b = decodeFile(f);
        if(b != null)
            return b;
        try
        {
            Bitmap bitmap = null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            conn.disconnect();
            bitmap = decodeFile(f);
            return bitmap;
        }
        catch(Throwable ex)
        {
            ex.printStackTrace();
            if(ex instanceof OutOfMemoryError)
                memoryCache.clear();
            return null;
        }
    }

    private Bitmap decodeFile(File f)
    {
        try
        {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();
            final int REQUIRED_SIZE = 85;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while(true)
            {
                if(width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        }
        catch(FileNotFoundException e)
        {
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    boolean imageViewReused(PhotoToLoad photoToLoad)
    {
        String tag = imageViews.get(photoToLoad.imageView);
        if(tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    public void clearCache()
    {
        memoryCache.clear();
        fileCache.clear();
    }

    private class PhotoToLoad
    {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i)
        {
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable
    {
        PhotoToLoad photoToLoad;

        PhotosLoader(PhotoToLoad photoToLoad)
        {
            this.photoToLoad = photoToLoad;
        }

        @Override
        public void run()
        {
            try
            {
                if(imageViewReused(photoToLoad))
                    return;
                Bitmap bmp = getBitmap(photoToLoad.url);
                memoryCache.put(photoToLoad.url, bmp);
                if(imageViewReused(photoToLoad))
                    return;
                BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad);
                handler.post(bd);
            }
            catch(Throwable th)
            {
                th.printStackTrace();
            }
        }
    }

    class BitmapDisplayer implements Runnable
    {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p)
        {
            bitmap = b;
            photoToLoad = p;
        }

        @Override
        public void run()
        {
            if(imageViewReused(photoToLoad))
                return;
            if(bitmap != null)
                photoToLoad.imageView.setImageBitmap(bitmap);
        }
    }
}