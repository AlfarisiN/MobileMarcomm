package com.android.mobilemarcom.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestSouvenirStockAutoComplete;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteSouvenirStokAdapter extends ArrayAdapter<RequestSouvenirStockAutoComplete> {
    private Context context;
    private int resourceId;
    private List<RequestSouvenirStockAutoComplete> dataList, tempItems, suggestions;

    public AutoCompleteSouvenirStokAdapter(@NonNull Context context, int resourceId, ArrayList<RequestSouvenirStockAutoComplete> dataLists) {
        super(context, resourceId);
        this.context = context;
        this.resourceId = resourceId;
        this.dataList = dataLists;
        tempItems = new ArrayList<>(dataLists);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        try{
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            final RequestSouvenirStockAutoComplete dataList = getItem(position);
            TextView textViewNameEmployee = (TextView) view.findViewById(R.id.textViewNameSouvenirStok);
            textViewNameEmployee.setText(dataList.getName());
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public RequestSouvenirStockAutoComplete getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            RequestSouvenirStockAutoComplete dataListChar = (RequestSouvenirStockAutoComplete) resultValue;
            return dataListChar.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null){
                suggestions.clear();
                for (RequestSouvenirStockAutoComplete dataList : tempItems){
                    if (dataList.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())){
                        suggestions.add(dataList);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<RequestSouvenirStockAutoComplete> tempValues = (ArrayList<RequestSouvenirStockAutoComplete>) results.values;
            if (results != null && results.count > 0){
                clear();
                for (RequestSouvenirStockAutoComplete dataListRes : tempValues){
                    add(dataListRes);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}
