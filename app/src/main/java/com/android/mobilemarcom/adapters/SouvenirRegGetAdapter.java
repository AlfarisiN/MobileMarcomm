package com.android.mobilemarcom.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.souvenir_request.DataListSouvenirRequest;
import com.android.mobilemarcom.viewholder.ViewJolderSouvenirGetItem;

import java.util.List;

public class SouvenirRegGetAdapter extends RecyclerView.Adapter<ViewJolderSouvenirGetItem>{
    private Context context;
    private List<DataListSouvenirRequest> dataListSouvenirRequestList;

    public SouvenirRegGetAdapter(Context context, List<DataListSouvenirRequest> dataListSouvenirRequestList) {
        this.context = context;
        this.dataListSouvenirRequestList = dataListSouvenirRequestList;
    }

    @NonNull
    @Override
    public ViewJolderSouvenirGetItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View customView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.custom_souvenir_get_request_list,
                viewGroup,
                false
        );
        return new ViewJolderSouvenirGetItem(customView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewJolderSouvenirGetItem viewJolderSouvenirGetItem, int i) {
        if(dataListSouvenirRequestList.size()>0){
            DataListSouvenirRequest dataList = dataListSouvenirRequestList.get(i);
            viewJolderSouvenirGetItem.setModelSouvenirReqItem(context,dataList);
        }
    }

    @Override
    public int getItemCount() {
        if(dataListSouvenirRequestList!=null){
            return  dataListSouvenirRequestList.size();
        }else{
            return 0;
        }
    }
}
