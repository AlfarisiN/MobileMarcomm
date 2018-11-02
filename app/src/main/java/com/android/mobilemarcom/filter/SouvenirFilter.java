package com.android.mobilemarcom.filter;

import android.widget.Filter;

import com.android.mobilemarcom.adapters.SouvenirAdapter;
import com.android.mobilemarcom.model.ModelSouvenir.DataListModel;

import java.util.ArrayList;
import java.util.List;

public class SouvenirFilter extends Filter {
    private SouvenirAdapter souvenirAdapter;
    private List<DataListModel> dataListModels;

    public SouvenirFilter(List<DataListModel> dataListModels, SouvenirAdapter souvenirAdapter){
        this.souvenirAdapter = souvenirAdapter;
        this.dataListModels = dataListModels;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();

            List<DataListModel> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < dataListModels.size(); i++){
                if (dataListModels.get(i).getName().toUpperCase().contains(constraint)){
                    filteredPlayers.add(dataListModels.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = dataListModels.size();
            results.values = dataListModels;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        souvenirAdapter.souvenirList = (List<DataListModel>) results.values;

        //Refresh
        souvenirAdapter.notifyDataSetChanged();
    }
}
