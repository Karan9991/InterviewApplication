package com.pritesh.interviewapplication.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pritesh.interviewapplication.R;
import com.pritesh.interviewapplication.data.food.Favorite;

import java.util.List;

import io.realm.Realm;

/**
 * Created by pritesh.patel on 2017-05-25, 2:53 PM.
 * ADESA, Canada
 */

public class FavoriteRecipeRecyclerViewAdapter extends RecyclerView.Adapter<FavoriteRecipeRecyclerViewAdapter.DataViewHolder> implements View.OnClickListener
{
    private List<Favorite> mDataModelList;
    private static Activity mActivity;

    public FavoriteRecipeRecyclerViewAdapter(Activity activity, List<Favorite> dataModelList)
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
        Favorite dm = mDataModelList.get(i);
        dataViewHolder.mTextViewTitle.setText(dm.getTitle());
        dataViewHolder.mImageViewRemove.setTag(dm);
        dataViewHolder.mImageViewRemove.setOnClickListener(this);

        Glide.with(mActivity).load(dm.getImageUrl())
                .thumbnail(1)
                .into(dataViewHolder.mImageViewRecipe);

        dataViewHolder.txtRatings.setText("Social Ratings : " + dm.getSocialRank());
        dataViewHolder.txtPublisher.setText("Publisher : " + dm.getPublisher());
        dataViewHolder.txtSourceUrl.setText("Url : " + dm.getSourceUrl());
        dataViewHolder.txtPublisherUrl.setText("Publisher info : " + dm.getPublisherUrl());

    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_list_favorite_recepies, viewGroup, false);

        return new DataViewHolder(itemView);
    }

    public void clear() {
        mDataModelList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<Favorite> list) {
        mDataModelList.addAll(0,list);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view)
    {
        Favorite fr = (Favorite) view.getTag();
        if(view.getId() == R.id.imgRemove)
        {
            Realm mRealmFavorite = Realm.getDefaultInstance();
            //Favorite favorite = mRealmFavorite.where(Favorite.class).equalTo("recipeId", fr.getRecipeId()).findFirst();
            mRealmFavorite.beginTransaction();
            fr.deleteFromRealm();
            mRealmFavorite.commitTransaction();
            notifyDataSetChanged();
            Toast.makeText(mActivity, "Remove", Toast.LENGTH_SHORT).show();
        }
    }

    static class DataViewHolder extends RecyclerView.ViewHolder
    {
        TextView mTextViewTitle, txtPublisher, txtRatings, txtSourceUrl, txtPublisherUrl;
        ImageView mImageViewRecipe, mImageViewRemove;

        DataViewHolder(View view)
        {
            super(view);
            mTextViewTitle = (TextView) view.findViewById(R.id.txtRecipeTitle);
            mImageViewRecipe = (ImageView) view.findViewById(R.id.imgRecipe);
            mImageViewRemove = (ImageView) view.findViewById(R.id.imgRemove);
            txtRatings = (TextView) view.findViewById(R.id.txtRatings);
            txtPublisher = (TextView) view.findViewById(R.id.txtPublisher);
            txtSourceUrl = (TextView) view.findViewById(R.id.txtUrl);
            txtSourceUrl.setSelected(true);
            txtPublisherUrl = (TextView) view.findViewById(R.id.txtPublisherUrl);
            txtPublisherUrl.setSelected(true);
        }
    }
}

