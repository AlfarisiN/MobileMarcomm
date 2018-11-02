package com.android.mobilemarcom.souvenirRequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.employee.EmployeeFilter;
import com.android.mobilemarcom.souvenirRequest.API.DataList;
import com.android.mobilemarcom.viewholder.ViewHolderSouvenirReq;

import java.util.List;

public class SouvenirReqAdapter extends RecyclerView.Adapter<ViewHolderSouvenirReq> {
    private Context context;
    public List<DataList> modelSouvReq;
    public List<DataList> filterList;
    public EmployeeFilter filter;
    public SouvenirReqAdapter(Context context, List<DataList> modelSouvReq) {
        this.context = context;
        this.modelSouvReq = modelSouvReq;
//        this.filterList = modelSouvReq;
    }

    @NonNull
    @Override
    public ViewHolderSouvenirReq onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.custom_souvenir_request_list,
                parent,
                false
        );
        return new ViewHolderSouvenirReq(customView);
    }

    @Override
    public void onBindViewHolder(ViewHolderSouvenirReq holder, int position) {
        if(modelSouvReq.size()>0){
            DataList employee = modelSouvReq.get(position);
//            DataListAddItem dataList1 = modelSouvReq.get(position);
            holder.setModelSouvenirReq(context,employee);
        }

    }

    @Override
    public int getItemCount() {
        if(modelSouvReq != null){
            return modelSouvReq.size();
        }
        return 0;
    }

//    public void filterList(ArrayList<DataListAddItem> filterList){
//        modelSouvReq = filterList;
//        notifyDataSetChanged();
//    }


//    @Override
//    public Filter getFilter() {
//        if(filter == null){
//            filter = new EmployeeFilter(filterList, this);
//        }
//        return filter;
//    }
}
