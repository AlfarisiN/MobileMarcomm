package com.android.mobilemarcom.filter;

import android.widget.Filter;

import com.android.mobilemarcom.adapters.UsersAdapter;
import com.android.mobilemarcom.model.User;
import com.android.mobilemarcom.model.user.DataList;

import java.util.ArrayList;
import java.util.List;

public class Userfilter extends Filter{
    private UsersAdapter usersAdapter;
    private List<DataList> dataLists;

    public Userfilter(List<DataList> dataLists,UsersAdapter usersAdapter) {
        this.usersAdapter = usersAdapter;
        this.dataLists = dataLists;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results=new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if(constraint != null && constraint.length() > 0)
        {
            //CHANGE TO UPPER
            constraint=constraint.toString().toUpperCase();
            //STORE OUR FILTERED PLAYERS
            List<DataList> filteredPlayers=new ArrayList<>();
            for (int i=0;i<dataLists.size();i++)
            {
                //CHECK
                if(dataLists.get(i).getUsername().toUpperCase().contains(constraint))
                {
                    //ADD PLAYER TO FILTERED PLAYERS
                    filteredPlayers.add(dataLists.get(i));
                }
            }

            results.count=filteredPlayers.size();
            results.values=filteredPlayers;
        }else
        {
            results.count=dataLists.size();
            results.values=dataLists;

        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        usersAdapter.dataListList= (List<DataList>) results.values;

        //REFRESH
        usersAdapter.notifyDataSetChanged();
    }

}
