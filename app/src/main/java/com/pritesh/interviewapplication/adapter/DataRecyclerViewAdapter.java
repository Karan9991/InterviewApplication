package com.pritesh.interviewapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pritesh.interviewapplication.R;
import com.pritesh.interviewapplication.data.DataModel;

import java.util.List;

/**
 * Created by pritesh.patel on 2017-05-25, 2:53 PM.
 * ADESA, Canada
 */

public class DataRecyclerViewAdapter extends RecyclerView.Adapter<DataRecyclerViewAdapter.DataViewHolder>
{
    private List<DataModel> mDataModelList;
    private Activity mActivity;
    LAYOUT mLAYOUT;

    public enum LAYOUT{
        LINEAR_LAYOUT,
        GRID_LAYOUT
    };

    public DataRecyclerViewAdapter(Activity activity, List<DataModel> dataModelList, LAYOUT layout)
    {
        mActivity = activity;
        this.mDataModelList = dataModelList;
        mLAYOUT = layout;
    }

    @Override
    public int getItemCount()
    {
        return mDataModelList.size();
    }

    @Override
    public void onBindViewHolder(DataViewHolder dataViewHolder, int i)
    {
        DataModel dm = mDataModelList.get(i);
        dataViewHolder.mTextView.setVisibility(View.GONE);
        if(mLAYOUT == LAYOUT.LINEAR_LAYOUT)
        {
            dataViewHolder.mTextView.setVisibility(View.VISIBLE);
            dataViewHolder.mTextView.setText(dm.getText());
        }

        Glide.with(mActivity).load(dm.getUrl())
                .thumbnail(1)
                .into(dataViewHolder.mImageView);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_data_list, viewGroup, false);

        return new DataViewHolder(itemView);
    }

    static class DataViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;
        ImageView mImageView;

        DataViewHolder(View view)
        {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.txtTitle);
            mImageView = (ImageView) view.findViewById(R.id.imgData);
        }
    }
}

