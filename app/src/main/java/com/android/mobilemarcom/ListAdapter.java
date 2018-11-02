package com.android.mobilemarcom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ViewHolderList> {
    private Context context;
    private List<Header> dataList;

    public ListAdapter(Context context, List<Header> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public ViewHolderList onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.custom_list_test_data,
                parent,
                false
        );

        return new ViewHolderList(customView);
    }

    @Override
    public void onBindViewHolder(ViewHolderList holder, int position) {
        if(dataList.size() > 0) {
            Header buku = dataList.get(position);
            holder.setData(context, buku);
        }
    }

    @Override
    public int getItemCount() {
        if(dataList == null) {
            return 0;
        }
        else{
            return dataList.size();
        }
    }
}

