package com.pritesh.interviewapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.pritesh.interviewapplication.adapter.DataRecyclerViewAdapter;
import com.pritesh.interviewapplication.data.AllData;
import com.pritesh.interviewapplication.data.DataModel;
import com.pritesh.interviewapplication.network.ApiClient;
import com.pritesh.interviewapplication.network.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewDataActivity extends AppCompatActivity
{

    RecyclerView mRecyclerView;
    DataRecyclerViewAdapter mDataRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_data);

        mRecyclerView = (RecyclerView)findViewById(R.id.rvDataList);
        mRecyclerView.setHasFixedSize(true);
        //LinearLayoutManager llm = new LinearLayoutManager(this);
        //llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        //llm.setOrientation(LinearLayoutManager.VERTICAL);
        //mRecyclerView.setLayoutManager(llm);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(RecyclerViewDataActivity.this, 3);
        //mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        getRetrofitSupport();
    }

    private void getRetrofitSupport()
    {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<AllData> apiServiceData = apiService.getData();
        apiServiceData.enqueue(new Callback<AllData>()
        {
            @Override
            public void onResponse(Call<AllData> call, Response<AllData> response)
            {
                ArrayList<DataModel>mArrayDataList = response.body().images;
                mDataRecyclerViewAdapter = new DataRecyclerViewAdapter(RecyclerViewDataActivity.this,mArrayDataList, DataRecyclerViewAdapter.LAYOUT.STAGGERED_GRID_LAYOUT);
                mRecyclerView.setAdapter(mDataRecyclerViewAdapter);
            }

            @Override
            public void onFailure(Call<AllData> call, Throwable t)
            {

            }
        });
    }
}
