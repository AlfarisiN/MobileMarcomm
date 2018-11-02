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
import com.android.mobilemarcom.model.ModelSouvenir.RequestAutoCompleteSouvenir;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteSouvenirAdapter extends ArrayAdapter<RequestAutoCompleteSouvenir> {
    private Context context;
    private int resourceId;
    private List<RequestAutoCompleteSouvenir> dataList, tempItems, suggestions;


    public AutoCompleteSouvenirAdapter(@NonNull Context context, int resourceId, ArrayList<RequestAutoCompleteSouvenir> dataLists) {
        super(context, resourceId);
        this.dataList = dataLists;
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
            final RequestAutoCompleteSouvenir dataList = getItem(position);
            TextView textViewNameSouvenir = (TextView) view.findViewById(R.id.textViewNameSouvenir);
            textViewNameSouvenir.setText(dataList.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Nullable
    @Override
    public RequestAutoCompleteSouvenir getItem(int position) {
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
            RequestAutoCompleteSouvenir dataListChar = (RequestAutoCompleteSouvenir) resultValue;
            return dataListChar.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (RequestAutoCompleteSouvenir dataList: tempItems) {
                    if (dataList.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
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
            ArrayList<RequestAutoCompleteSouvenir> tempValues = (ArrayList<RequestAutoCompleteSouvenir>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (RequestAutoCompleteSouvenir dataList : tempValues) {
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
