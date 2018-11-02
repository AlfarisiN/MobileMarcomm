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
import com.android.mobilemarcom.model.user.DataListAutoCompleteEmploye;

import java.util.ArrayList;
import java.util.List;

public class EmployeAdapterAutoComplete extends ArrayAdapter<DataListAutoCompleteEmploye> {
    private Context context;
    private int resourceId;
    private List<DataListAutoCompleteEmploye> datalist, tempItems, suggestions;

    public EmployeAdapterAutoComplete(@NonNull Context context, int resourceId, ArrayList<DataListAutoCompleteEmploye> dataLists) {
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
            final DataListAutoCompleteEmploye dataList = getItem(position);
            TextView textViewIdEmploye1 = (TextView) view.findViewById(R.id.textViewIdEmploye1);
            TextView textViewNameEmploye1 = (TextView) view.findViewById(R.id.textViewNameEmploye1);
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
    public DataListAutoCompleteEmploye getItem(int position) {
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
            DataListAutoCompleteEmploye dataList = (DataListAutoCompleteEmploye) resultValue;
            return dataList.getName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (DataListAutoCompleteEmploye dataList: tempItems) {
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
            ArrayList<DataListAutoCompleteEmploye> tempValues = (ArrayList<DataListAutoCompleteEmploye>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (DataListAutoCompleteEmploye dataList : tempValues) {
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


