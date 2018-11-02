package com.android.mobilemarcom.filter;

import android.widget.Filter;

import com.android.mobilemarcom.adapters.SouvenirStokAdapter;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestSouvenirStockAddItem;

import java.util.ArrayList;
import java.util.List;

public class SouvenirStockFilter extends Filter {
    private SouvenirStokAdapter souvenirAdapter;
    private List<RequestSouvenirStockAddItem> dataLists;

    public SouvenirStockFilter(List<RequestSouvenirStockAddItem> dataLists, SouvenirStokAdapter souvenirAdapter){
        this.souvenirAdapter = souvenirAdapter;
        this.dataLists = dataLists;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();

            List<RequestSouvenirStockAddItem> filteredPlayer = new ArrayList<>();
            for (int i = 0; i < dataLists.size(); i++){
                if (dataLists.get(i).getReceivedBy().getFirstName().toUpperCase().contains(constraint)){
                    filteredPlayer.add(dataLists.get(i));
                }
            }

            results.count=filteredPlayer.size();
            results.values=filteredPlayer;
        } else {
            results.count=dataLists.size();
            results.values=dataLists;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        souvenirAdapter.souvenirStokList = (List<RequestSouvenirStockAddItem>) results.values;
        souvenirAdapter.notifyDataSetChanged();
    }
}
