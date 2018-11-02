package com.android.mobilemarcom.employee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.employee.API.Employee;
import com.android.mobilemarcom.viewholder.ViewHolderEmployee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<ViewHolderEmployee> implements Filterable {
    private Context context;
    public List<Employee> modelEmployees, filterList;
    public EmployeeFilter filter;
    public EmployeeAdapter(Context context, List<Employee> modelEmployees) {
        this.context = context;
        this.modelEmployees = modelEmployees;
        this.filterList = modelEmployees;
    }

    @NonNull
    @Override
    public ViewHolderEmployee onCreateViewHolder(ViewGroup parent, int viewType) {
        View customView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.custom_employee_list,
                parent,
                false
        );
        return new ViewHolderEmployee(customView);
    }

    @Override
    public void onBindViewHolder(ViewHolderEmployee holder, int position) {
        if(modelEmployees.size()>0){
            Employee employee = modelEmployees.get(position);
            holder.setModelEmployee(context,employee);
        }

    }

    @Override
    public int getItemCount() {
        if(modelEmployees != null){
            return modelEmployees.size();
        }
        return 0;
    }

    public void filterList(ArrayList<Employee> filterList){
        modelEmployees = filterList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new EmployeeFilter(filterList, this);
        }
        return filter;
    }
}
