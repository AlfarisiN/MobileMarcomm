package com.android.mobilemarcom.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.viewholder.ViewHolderCrud;

import java.util.List;


public class CrudAdapter extends RecyclerView.Adapter<ViewHolderCrud>  {
    private Context context;
    private List<String> codeBc;
    private List<String> namaBc;

    public CrudAdapter(Context context,
                       List<String> codeBc,
                       List<String> namaBc){
        this.context = context;
        this.codeBc = codeBc;
        this.namaBc = namaBc;

    }

    @NonNull
    @Override
    public ViewHolderCrud onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.custom_crud_list,
                parent,
                false
        );
        return new ViewHolderCrud(customView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCrud holder,  final int position) {
        if (codeBc.size() > 0){
            String code = codeBc.get(position);
            String nama = namaBc.get(position);

            holder.setCrud(context, code, nama);
        }
    }

    @Override
    public int getItemCount() {
        if (codeBc == null){
            return 0;
        }
        else {
            return codeBc.size();
        }
    }
}
