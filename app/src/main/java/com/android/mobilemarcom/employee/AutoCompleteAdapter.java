package com.android.mobilemarcom.employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.android.mobilemarcom.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteAdapter extends BaseAdapter implements Filterable {
    private List<String> stringList;
    private Context context;
    private List<String> filterList;
    private LayoutInflater inflater;

    public AutoCompleteAdapter(Context context, List<String> stringList) {
        this.context = context;
        this.stringList = stringList;
        this.filterList = stringList;
    }

    @Override
    public int getCount() {
        return filterList.size();
    }

    @Override
    public Object getItem(int position) {
        return filterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_spinner, null);
        }
        TextView description = (TextView) convertView.findViewById(R.id.valueSpinner);
        String value = filterList.get(position);
        description.setText(value);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if(charString.isEmpty()){
                    filterList = stringList;
                }
                else{
                    List<String> filteredTmp = new ArrayList<>();
                    for(int x=0; x<stringList.size(); x++){
                        if (stringList.get(x).toLowerCase().contains(charString.toLowerCase())){
                            filteredTmp.add(stringList.get(x));
                        }
                    }
                    filterList = filteredTmp;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filterList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                filterList = (ArrayList<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
