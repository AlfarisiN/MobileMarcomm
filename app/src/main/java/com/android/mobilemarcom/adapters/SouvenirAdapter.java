package com.android.mobilemarcom.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.filter.SouvenirFilter;
import com.android.mobilemarcom.model.ModelSouvenir.DataListModel;
import com.android.mobilemarcom.viewholder.ViewHolderSouvenir;

import java.util.List;

public class SouvenirAdapter extends RecyclerView.Adapter<ViewHolderSouvenir> implements Filterable {
    private Context context;
    public List<DataListModel> souvenirList, filterList;
    public SouvenirFilter filter;

    public SouvenirAdapter(Context context, List<DataListModel> souvenirList) {
        this.context = context;
        this.souvenirList = souvenirList;
        this.filterList = souvenirList;
    }

    @NonNull
    @Override
    public ViewHolderSouvenir onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_souvenir_list,
                parent,
                false
        );
        return new ViewHolderSouvenir(customView);
    }

    @Override
    public void onBindViewHolder(final ViewHolderSouvenir holder, final int position) {
       if (souvenirList.size() > 0){
           DataListModel dataListModel = souvenirList.get(position);
           holder.setModelSouvenir(context, dataListModel);
       }

    }

    @Override
    public int getItemCount() {
        if(souvenirList != null){
            return souvenirList.size();
        }
        return 0;
    }


    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new SouvenirFilter(filterList, this);
        }
        return filter;
    }
}
