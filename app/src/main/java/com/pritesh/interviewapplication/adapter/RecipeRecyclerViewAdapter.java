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
        dataViewHolder.mTextViewTitle.setText(dm.getTitle());
        dataViewHolder.mTextViewPublisher.setText("Publisher : " + dm.getPublisher());
        dataViewHolder.mTextViewRatings.setText("Ratings : " + dm.getSocialRank());

        Glide.with(mActivity).load(dm.getImageUrl())
                .thumbnail(1)
                .into(dataViewHolder.mImageViewRecipe);
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_recepies, viewGroup, false);

        return new DataViewHolder(itemView);
    }

    public void clear() {
        mDataModelList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<RecipeItem> list) {
        mDataModelList.addAll(list);
        notifyDataSetChanged();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextViewTitle, mTextViewPublisher, mTextViewRatings;
        ImageView mImageViewRecipe;

        DataViewHolder(View view)
        {
            super(view);
            mTextViewTitle = (TextView) view.findViewById(R.id.txtRecipeTitle);
            mImageViewRecipe = (ImageView) view.findViewById(R.id.imgRecipe);
            mTextViewPublisher = (TextView) view.findViewById(R.id.txtPublisher);
            mTextViewRatings = (TextView) view.findViewById(R.id.txtRatings);
        }
    }
}

