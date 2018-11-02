package com.android.mobilemarcom.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.unit.modelunit.DataList;
import com.android.mobilemarcom.viewholder.ViewHolderUnit;

import java.util.List;

/**
 * Created by Irfan Naufal Ridi on 23/10/2018.
 */

public class UnitAdapter extends RecyclerView.Adapter<ViewHolderUnit> {
    private Context context;
    public static List<DataList> unitList;

    public UnitAdapter(Context context, List<DataList> unitList) {
        this.context = context;
        this.unitList = unitList;
    }

    @NonNull
    @Override
    public ViewHolderUnit onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_unit, parent, false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        return new ViewHolderUnit(customView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderUnit viewHolderUnit, int i) {
        final DataList unit = unitList.get(i);
        viewHolderUnit.setModelUnit(context, unit);
        viewHolderUnit.unitDeactiveAndEdit(unit);
    }


    @Override
    public int getItemCount() {
        if(unitList != null){
            return unitList.size();
        }
        else{
            return 0;
        }
    }

    public void filterList(List<DataList> filterList){
        unitList = filterList;
        notifyDataSetChanged();
    }
}
