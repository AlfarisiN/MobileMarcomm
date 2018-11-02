package com.android.mobilemarcom.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.filter.SouvenirStockFilter;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestSouvenirStockAddItem;
import com.android.mobilemarcom.viewholder.ViewHolderSouvenirStok;

import java.util.List;

public class SouvenirStokAdapter extends RecyclerView.Adapter<ViewHolderSouvenirStok> implements Filterable  {
    public List<RequestSouvenirStockAddItem> souvenirStokList,filterList;
    private Context mContext;
    public SouvenirStockFilter filter;

    public SouvenirStokAdapter(Context mContext, List<RequestSouvenirStockAddItem> souvenirStokList){
        this.mContext = mContext;
        this.souvenirStokList = souvenirStokList;
        this.filterList = souvenirStokList;
    }

    @Override
    public ViewHolderSouvenirStok onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_t_souvenir_list,
                        parent,
                        false);
        return new ViewHolderSouvenirStok(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolderSouvenirStok holder, int position) {
        if(souvenirStokList.size()>0){
            RequestSouvenirStockAddItem dataList = souvenirStokList.get(position);
            holder.setModelSouvenirStok(mContext, dataList);
        }
    }


    @Override
    public int getItemCount() {
        if (souvenirStokList != null) {
            return souvenirStokList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new SouvenirStockFilter(filterList, this);
        }
        return filter;
    }
}