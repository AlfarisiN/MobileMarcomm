package com.android.mobilemarcom.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestGetItemSouvenirStok;
import com.android.mobilemarcom.viewholder.ViewHolderSouvenirStokAdd;

import java.util.List;

public class SouvenirStokAddAdapter extends RecyclerView.Adapter<ViewHolderSouvenirStokAdd> {
    private Context context;
    private List<RequestGetItemSouvenirStok> souvenirGetItemList;
    private int id;


    public SouvenirStokAddAdapter(Context context, List<RequestGetItemSouvenirStok> souvenirGetItemList, int id){
        this.context = context;
        this.souvenirGetItemList = souvenirGetItemList;
        this.id = id;
    }

    @NonNull
    @Override
    public ViewHolderSouvenirStokAdd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_t_souvenir_additem_list,
                        parent,
                        false);
        return new ViewHolderSouvenirStokAdd(customView, id);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderSouvenirStokAdd holder, int position) {
        if (souvenirGetItemList.size() > 0){
            RequestGetItemSouvenirStok dataListModel = souvenirGetItemList.get(position);
            holder.setModelAddItemSouvenir(context, dataListModel);
        }
    }

    @Override
    public int getItemCount() {
        if (souvenirGetItemList != null){
            return souvenirGetItemList.size();
        }
        return 0;
    }
}
