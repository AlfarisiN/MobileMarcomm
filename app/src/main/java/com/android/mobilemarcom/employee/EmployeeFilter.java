package com.android.mobilemarcom.employee;

import android.widget.Filter;

import com.android.mobilemarcom.employee.API.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeFilter extends Filter {
    private EmployeeAdapter employeeAdapter;
    private List<Employee> employeeModel;

    public EmployeeFilter(List<Employee> employeeModel, EmployeeAdapter employeeAdapter){
        this.employeeAdapter = employeeAdapter;
        this.employeeModel = employeeModel;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        if (constraint != null && constraint.length() > 0){
            constraint = constraint.toString().toUpperCase();

            List<Employee> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < employeeModel.size(); i++){
                if (employeeModel.get(i).getFirstName().toUpperCase().contains(constraint)){
                    filteredPlayers.add(employeeModel.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = employeeModel.size();
            results.values = employeeModel;
        }

        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        employeeAdapter.modelEmployees = (List<Employee>) results.values;

        //Refresh
        employeeAdapter.notifyDataSetChanged();
    }
}
