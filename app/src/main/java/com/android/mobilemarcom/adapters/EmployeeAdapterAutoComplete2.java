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
import com.android.mobilemarcom.employee.API.DataListAuto;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapterAutoComplete2 extends ArrayAdapter<DataListAuto> {
    private Context context;
    private int resourceId;
    private List<DataListAuto> datalist, tempItems, suggestions;

    public EmployeeAdapterAutoComplete2(@NonNull Context context, int resourceId, ArrayList<DataListAuto> dataLists) {
        super(context, resourceId, dataLists);
        this.datalist = dataLists;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<>(dataLists);
        suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            final DataListAuto dataList = getItem(position);
            TextView textViewIdEmploye1 = (TextView) view.findViewById(R.id.textViewIdEmploye2);
            TextView textViewNameEmploye1 = (TextView) view.findViewById(R.id.textViewNameEmploye2);
            textViewIdEmploye1.setText(""+dataList.getId());
            textViewIdEmploye1.setVisibility(View.GONE);
            textViewNameEmploye1.setText(dataList.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public DataListAuto getItem(int position) {
        return datalist.get(position);
    }
    @Override
    public int getCount() {
        return datalist.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @NonNull
    @Override
    public Filter getFilter() {
        return fruitFilter;
    }
    private Filter fruitFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            DataListAuto dataList = (DataListAuto) resultValue;
            return dataList.getName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (DataListAuto dataList: tempItems) {
                    if (dataList.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
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
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<DataListAuto> tempValues = (ArrayList<DataListAuto>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (DataListAuto dataList : tempValues) {
                    add(dataList);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}



