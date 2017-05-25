package com.pritesh.interviewapplication.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.pritesh.interviewapplication.R;
import com.pritesh.interviewapplication.data.DataModel;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by pritesh.patel on 2017-05-23, 2:03 PM.
 * ADESA, Canada
 */

public class DataAdapter extends BaseAdapter
{
    ArrayList<DataModel>mDataModelArrayList;
    Activity mContext;
    com.pritesh.interviewapplication.lazyloading.ImageLoader imageLoader;
    public DataAdapter(Activity context, ArrayList<DataModel> dataModelArrayList)
    {
        mContext = context;
        mDataModelArrayList = dataModelArrayList;
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mContext).build();
        ImageLoader.getInstance().init(config);
        imageLoader = new com.pritesh.interviewapplication.lazyloading.ImageLoader(context);


    }
    @Override
    public int getCount()
    {
        return mDataModelArrayList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDataModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if(convertView == null)
        {
            LayoutInflater inflater = mContext.getLayoutInflater();
            convertView = inflater.inflate(R.layout.item_data_list, null, true);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.txtTitle);
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.imgData);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder)convertView.getTag();

        DataModel dm = mDataModelArrayList.get(position);
        viewHolder.mTextView.setText(dm.getText());
        //Picasso.with(mContext).load(dm.getUrl()).into(viewHolder.mImageView);
        //ImageLoader.getInstance().displayImage(dm.getUrl(),viewHolder.mImageView);
        //Glide.with(mContext).load(dm.getUrl())
        //        .thumbnail(1)
        //        .into(viewHolder.mImageView);

        //LazyLoading Custom Library in the Project
        imageLoader.DisplayImageRotated(dm.getUrl(),viewHolder.mImageView);

        //This will crash the app due to OOM error
        //Can be implemented in Service Class
        //new DownloadImage(viewHolder.mImageView).execute(dm.getUrl());

        return convertView;
    }

    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap>
    {

        private final WeakReference<ImageView> mImageView;
        DownloadImage(ImageView imageView)
        {
            mImageView = new WeakReference<>(imageView);
        }
        @Override
        protected Bitmap doInBackground(String... URL)
        {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try
            {
                InputStream input = new java.net.URL(imageURL).openStream();
                bitmap = BitmapFactory.decodeStream(input);
                return getBitmap(imageURL);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result)
        {
            // Set the bitmap into ImageView
            if(result==null)
                mImageView.get().setImageResource(R.mipmap.ic_launcher);
            else
                mImageView.get().setImageBitmap(result);
        }
    }

    private Bitmap getBitmap(String url)
    {

        //from web
        try
        {
            Bitmap bitmap;
            java.net.URL imageUrl = new java.net.URL(url);
            HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            //conn.setReadTimeout(30000);
            //conn.setInstanceFollowRedirects(true);
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (Exception ex)
        {
            //ex.printStackTrace();
            return null;
        }
    }

    private class ViewHolder
    {
        TextView mTextView;
        ImageView mImageView;
    }
}
