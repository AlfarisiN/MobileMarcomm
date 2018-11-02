package com.android.mobilemarcom.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.modelevent.DataList;
import com.android.mobilemarcom.viewholder.ViewHolderEvent;

import java.util.List;

/**
 * Created by Irfan Naufal Ridi on 24/10/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<ViewHolderEvent> {

    private Context context;
    private List<DataList> eventList;

    public EventAdapter(Context context, List<DataList> eventList) {
        this.context = context;
        this.eventList = eventList;
    }

    @NonNull
    @Override
    public ViewHolderEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_event, parent, false);
        return new ViewHolderEvent(customView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderEvent holder, int position) {
        final DataList events = eventList.get(position);
        holder.setModelEvent(context, events);
        holder.eventListEdit(events);
    }

    @Override
    public int getItemCount() {
        if(eventList != null){
            return eventList.size();
        }
        else{
            return 0;
        }
    }

    public void filterList(List<DataList> filterList){
        eventList = filterList;
        notifyDataSetChanged();
    }
}
