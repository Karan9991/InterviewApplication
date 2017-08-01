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
import com.pritesh.interviewapplication.data.food.RecipeItem;

import java.util.List;

/**
 * Created by pritesh.patel on 2017-05-25, 2:53 PM.
 * ADESA, Canada
 */

public class RecipeRecyclerViewAdapter extends RecyclerView.Adapter<RecipeRecyclerViewAdapter.DataViewHolder>
{
    private List<RecipeItem> mDataModelList;
    private Activity mActivity;

    public RecipeRecyclerViewAdapter(Activity activity, List<RecipeItem> dataModelList)
    {
        mActivity = activity;
        this.mDataModelList = dataModelList;
    }

    @Override
    public int getItemCount()
    {
        return mDataModelList.size();
    }

    @Override
    public void onBindViewHolder(DataViewHolder dataViewHolder, int i)
    {
        RecipeItem dm = mDataModelList.get(i);
        dataViewHolder.mTextView.setText(dm.getTitle());

        Glide.with(mActivity).load(dm.getImageUrl())
                .thumbnail(1)
                .into(dataViewHolder.mImageView);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_recepies, viewGroup, false);

        return new DataViewHolder(itemView);
    }

    static class DataViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextView;
        ImageView mImageView;

        DataViewHolder(View view)
        {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.txtRecipeTitle);
            mImageView = (ImageView) view.findViewById(R.id.imgRecipe);
        }
    }
}

