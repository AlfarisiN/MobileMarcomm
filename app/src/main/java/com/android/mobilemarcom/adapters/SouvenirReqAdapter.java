package com.android.mobilemarcom.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.filter.SouvenirRequestFilter;
import com.android.mobilemarcom.model.souvenir_request.DataList;
import com.android.mobilemarcom.viewholder.ViewHolderSouvenirReq;

import java.util.List;

public class SouvenirReqAdapter extends RecyclerView.Adapter<ViewHolderSouvenirReq> {
    private Context context;
    public List<DataList> dataLists,filterList;
    public SouvenirRequestFilter filter;

    public SouvenirReqAdapter(Context context, List<DataList> dataLists) {
        this.context = context;
        this.dataLists = dataLists;
        this.filterList = dataLists;
    }

    @NonNull
    @Override
    public ViewHolderSouvenirReq onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View customView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.custom_souvenir_request_list,
                viewGroup,
                false
        );
        return new ViewHolderSouvenirReq(customView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSouvenirReq viewHolderSouvenirReq, int i) {
        if(dataLists.size()>0){
//            DataList dataList = dataLists.get(i);
//            viewHolderSouvenirReq.setModelSouvenirReq(context,dataList);
        }
    }

    @Override
    public int getItemCount() {
        if(dataLists!=null){
            return dataLists.size();
        }else{
            return 0;
        }
    }


    public Filter getFilter() {
        if(filter==null)
        {
            filter=new SouvenirRequestFilter(filterList,this);
        }

        return filter;
    }
}
