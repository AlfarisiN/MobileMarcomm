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
import com.android.mobilemarcom.filter.Userfilter;
import com.android.mobilemarcom.model.User;
import com.android.mobilemarcom.model.user.DataList;
import com.android.mobilemarcom.viewholder.ViewHolderUser;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<ViewHolderUser> implements Filterable{
    private Context context;
    public List<DataList> dataListList,filterList;
    public Userfilter filter;

    public UsersAdapter(Context context,List<DataList> dataListList) {
        this.context = context;
        this.dataListList = dataListList;
        this.filterList = dataListList;
    }

    @NonNull
    @Override
    public ViewHolderUser onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_list_user,viewGroup,false);
        return new ViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUser viewHolder, int i) {
        if(dataListList.size()>0){
            DataList dataList = dataListList.get(i);
            viewHolder.setModel(context,dataList);
        }

//        User user = userArrayList.get(i);

//        if(EMP_NAMES.length>0){
//            String employe_name = EMP_NAMES[i];
//            String username_employe = USERNAME[i];
//            String role_employe = ROLE[i];
//            String status_employe = STATUS[i];
//            viewHolder.setModel(context,employe_name,username_employe,role_employe,status_employe);
//            System.out.println(EMP_NAMES.length);
//        }
    }

    @Override
    public int getItemCount() {
        if(dataListList!=null){
            return dataListList.size();
        }else{
            return 0;
        }

    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new Userfilter(filterList,this);
        }

        return filter;
    }
}
