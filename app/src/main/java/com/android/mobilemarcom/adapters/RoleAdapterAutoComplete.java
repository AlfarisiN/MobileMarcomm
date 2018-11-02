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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.user.DataList;
import com.android.mobilemarcom.model.user.DataListAutoCompleteRole;

import java.util.ArrayList;
import java.util.List;

public class RoleAdapterAutoComplete extends ArrayAdapter<DataListAutoCompleteRole> {
    private Context context;
    private int resourceId;
    private List<DataListAutoCompleteRole> datalist, tempItems, suggestions;

    public RoleAdapterAutoComplete(@NonNull Context context, int resourceId, ArrayList<DataListAutoCompleteRole> dataLists) {
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
            final DataListAutoCompleteRole dataList = getItem(position);
            TextView textViewIdEmploye = (TextView) view.findViewById(R.id.textViewIdEmploye);
            TextView textViewNameEmploye = (TextView) view.findViewById(R.id.textViewNameEmploye);
            LinearLayout linearLayoutAutoCompleteEmploye = (LinearLayout) view.findViewById(R.id.linearLayoutAutoCompleteEmploye);
            textViewIdEmploye.setText(""+dataList.getId());
            textViewIdEmploye.setVisibility(View.GONE);
            textViewNameEmploye.setText(dataList.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public DataListAutoCompleteRole getItem(int position) {
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
            DataListAutoCompleteRole dataList = (DataListAutoCompleteRole) resultValue;
            return dataList.getName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (DataListAutoCompleteRole dataList: tempItems) {
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
            ArrayList<DataListAutoCompleteRole> tempValues = (ArrayList<DataListAutoCompleteRole>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (DataListAutoCompleteRole dataList : tempValues) {
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
