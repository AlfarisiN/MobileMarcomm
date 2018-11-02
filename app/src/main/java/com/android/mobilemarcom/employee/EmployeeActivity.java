package com.android.mobilemarcom.employee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.employee.API.DataList;
import com.android.mobilemarcom.employee.API.Employee;
import com.android.mobilemarcom.employee.API.EmployeeAPIUtilities;
import com.android.mobilemarcom.employee.API.ExampleGet;
import com.android.mobilemarcom.employee.API.RequestAPI;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmployeeActivity extends Fragment {
    private RecyclerView recyclerUser;
    private EmployeeAdapter userListAdapter;
    private List<Employee> stringList = new ArrayList<Employee>();
    private List<DataList> listData = new ArrayList<>();
    private DialogAutoComplete dialogChooseCompany;
    private RequestAPI requestAPI;
    private EditText nameSearchUser;
    private Button buttonSearchName;
    private ImageView addData;
    private boolean isStillLoading = false;
    private ArrayList<Employee> filteredList = new ArrayList<>();
    private EmployeeAdapter employeeAdapter;

    // ListArray of Sugesstion need to Load data from API
    public EmployeeActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_employee2, container, false);
        recyclerUser = (RecyclerView) view.findViewById(R.id.recyclerUser);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerUser.setLayoutManager(layoutManager);
        nameSearchUser = (EditText) view.findViewById(R.id.nameSearchUser);
        addData = (ImageView) view.findViewById(R.id.addData);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddEmployee.class);
                startActivity(intent);
            }
        });

        getDataFromAPI();
        nameSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (nameSearchUser.getText().toString().trim().length() == 0){
                    recyclerUser.setVisibility(View.INVISIBLE);
                } else{
                    recyclerUser.setVisibility(View.VISIBLE);
                    filter(s.toString());
                }
            }
        });
        tampilkanListBiodata();
        return view;
    }
    private void getDataFromAPI() {
        String contentType = "application/json";
        String token = SessionManager.getToken(getContext());
        final ProgressDialog loading = LoadingClass.loadingAnimationAndText(getContext(),"API Create New User...");
        loading.show();
        requestAPI = EmployeeAPIUtilities.getAPI();
        requestAPI.getListEmployee(contentType,token,"d").enqueue(new Callback<ExampleGet>() {
            @Override
            public void onResponse(Call<ExampleGet> call, Response<ExampleGet> response) {
                loading.dismiss();
                if(response.code()==200){
                    List<DataList> tmp = response.body().getDataList();
                    for (int i = 0; i<tmp.size();i++){
                        DataList data = tmp.get(i);
                        stringList.add(data.getEmployee());
                    }
                } else{
                    Toast.makeText(getContext(), "Gagal Mendapatkan List Biodata: " + response.code() + " msg: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ExampleGet> call, Throwable t) {
                Toast.makeText(getContext(), "List Biodata onFailure: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void filter(String text) {
        ArrayList<Employee> filteredList = new ArrayList<>();

        for (Employee item : stringList) {
            if (item.getLastName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        employeeAdapter.filterList(filteredList);
    }

    private void tampilkanListBiodata() {
        //addDummyList();
        if (employeeAdapter == null) {
            employeeAdapter = new EmployeeAdapter(getContext(), stringList);
            recyclerUser.setAdapter(employeeAdapter);
        }
    }

}