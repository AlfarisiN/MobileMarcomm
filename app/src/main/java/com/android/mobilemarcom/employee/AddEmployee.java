package com.android.mobilemarcom.employee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.employee.API.DataList;
import com.android.mobilemarcom.employee.API.DataListAuto;
import com.android.mobilemarcom.employee.API.Employee;
import com.android.mobilemarcom.employee.API.EmployeeAPIUtilities;
import com.android.mobilemarcom.employee.API.Example;
import com.android.mobilemarcom.employee.API.ExampleAuto;
import com.android.mobilemarcom.employee.API.RequestAPI;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEmployee extends Activity {
    private Context context = this;

    private EditText idNumber;
    private EditText firstName;
    private EditText lastName;
    private AutoCompleteTextView company;
    private EditText email;
    private Button saveData;
    private Button cancelSave;
    private TextInputLayout layoutCompany;

    private RequestAPI requestAPI;
    private List<Employee> stringList = new ArrayList<Employee>();
    private List<DataList> listData = new ArrayList<>();
    private List<DataListAuto> list = new ArrayList<>();

    int id =0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("Input Biodata");

        idNumber = (EditText) findViewById(R.id.idNumber);
        firstName = (EditText) findViewById(R.id.firstName);
        lastName = (EditText) findViewById(R.id.lastName);
        final String emailPattern = "@";
        email = (EditText) findViewById(R.id.email);
        final String email2 = email.getText().toString().trim();
//        email.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if(email2.matches(emailPattern) && editable.length()>0){
//                    Toast.makeText(getApplicationContext(), "valid email", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplicationContext(), "invalid email", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        company = (AutoCompleteTextView) findViewById(R.id.company);
        company.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                equal();
            }
        });
        layoutCompany = (TextInputLayout) findViewById(R.id.layoutCompany);


        saveData = (Button) findViewById(R.id.saveData);
        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiInput();
            }
        });

        cancelSave = (Button) findViewById(R.id.cancelSave);
        cancelSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tampilautocomplete("g");

    }
    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("masukkan email valid");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private void tampilautocomplete(String keyword){
        String contentType = "application/json";
        String tokenAuthorization = SessionManager.getToken(context);
        requestAPI = EmployeeAPIUtilities.getAPI();
        requestAPI.autoComplete(contentType, tokenAuthorization, keyword).enqueue(new Callback<ExampleAuto>() {
            @Override
            public void onResponse(Call<ExampleAuto> call, Response<ExampleAuto> response) {
                if(response.code()==200){
                    List<String> str = new ArrayList<String>();
                    list=response.body().getDataListAuto();
                    for (DataListAuto s : response.body().getDataListAuto()){
                        str.add(s.getName().toString());
                    }

                    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,
                            android.R.layout.select_dialog_item,str.toArray(new String[0]));
                    company.setThreshold(1);
                    company.setAdapter(adapter1);
                    company.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFailure(Call<ExampleAuto> call, Throwable t) {

            }
        });
    }
    private void equal(){
        for(int i = 1; i<list.size();i++){

            if(list.get(i).getName().equals(company.getText().toString())){
                id = list.get(i).getId();
                Log.i("idYangDicari",id+"");
            }
        }
        Log.i("idYangDicari",company.getText().toString());
    }

    private void validasiInput() {
        String [] temp;
        temp = firstName.getText().toString().split(" ");
        if(idNumber.getText().toString().trim().length() == 0){
            Toast.makeText(context, "ID tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(firstName.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Nama depan tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(lastName.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Last name tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(company.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Company tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        else if(!validateEmail()){
            String input = "Email: " + email.getText().toString();
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            callAPICreateEmployee();

        }
    }

    private void callAPICreateEmployee() {
        requestAPI = EmployeeAPIUtilities.getAPI();

        Example emp = new Example();
        emp.setCode(idNumber.getText().toString());
        emp.setFirstName(firstName.getText().toString());
        emp.setLastName(lastName.getText().toString());
        emp.setMCompanyId(id+"");
        emp.setEmail(email.getText().toString());
        String contentType = "application/json";
        String tokenAuthorization = SessionManager.getToken(context);

        requestAPI.createNewEmployee(contentType, tokenAuthorization, emp)
                .enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                        if (response.code() == 201){
                            notifSuccess();

                        }
                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void notifSuccess() {
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("notif")
                .setMessage("success added")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        finish();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
