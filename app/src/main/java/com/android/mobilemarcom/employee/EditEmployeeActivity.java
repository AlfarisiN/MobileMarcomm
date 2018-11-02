package com.android.mobilemarcom.employee;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.android.mobilemarcom.employee.API.DataListAuto;
import com.android.mobilemarcom.employee.API.EmployeeAPIUtilities;
import com.android.mobilemarcom.employee.API.ExampleAuto;
import com.android.mobilemarcom.employee.API.ExampleEdit;
import com.android.mobilemarcom.employee.API.RequestAPI;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEmployeeActivity extends AppCompatActivity {
    private Context context = this;

    private EditText idNumberEdit;
    private EditText firstNameEdit;
    private EditText lastNameEdit;
    private AutoCompleteTextView companyEdit;
    private TextInputLayout companyEditLayout;
    private EditText emailEdit;
    private EditText codeEdit;
    private Button saveDataEdit;
    private Button cancelSaveEdit;

    private RequestAPI requestAPI;
    private List<DataListAuto> list = new ArrayList<>();
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        final String firstName, lastName, code;
//        final int id;
//        idNumberEdit = (EditText) findViewById(R.id.idNumberEdit);
        codeEdit = (EditText) findViewById(R.id.codeEdit);
        firstNameEdit = (EditText) findViewById(R.id.firstNameEdit);
        lastNameEdit = (EditText) findViewById(R.id.lastNameEdit);
        emailEdit = (EditText) findViewById(R.id.emailEdit);
        companyEditLayout = (TextInputLayout) findViewById(R.id.companyEditLayout);
        companyEdit = (AutoCompleteTextView) findViewById(R.id.companyEdit);
        companyEdit.addTextChangedListener(new TextWatcher() {
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
        Bundle bd = getIntent().getExtras();
//        firstName = bd.getString("firstname");
        if(bd == null){
//            id = 0;
            code = null;
            firstName = null;
            lastName = null;
        }else{
//            id = bd.getInt("id");
//            code = bd.getString("code");
            firstName = bd.getString("firstName");
            lastName = bd.getString("lastName");
        }
//        idNumberEdit.setText(id);
//        codeEdit.setText(code);
        lastNameEdit.setText(lastName);
        firstNameEdit.setText(firstName);
        saveDataEdit = (Button) findViewById(R.id.saveDataEdit);
        saveDataEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiEdit();
            }
        });
        cancelSaveEdit = (Button) findViewById(R.id.cancelSaveEdit);
        cancelSaveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tampilautocomplete("g");
    }

    private void tampilautocomplete(String keyword) {
        String contentType = "application/json";
        String tokenAuthorization = SessionManager.getToken(context);
        requestAPI = EmployeeAPIUtilities.getAPI();
        requestAPI.autoComplete(contentType, tokenAuthorization, keyword).enqueue(new Callback<ExampleAuto>() {
            @Override
            public void onResponse(Call<ExampleAuto> call, Response<ExampleAuto> response) {
                if (response.code() == 200) {
                    List<String> str = new ArrayList<String>();
                    list=response.body().getDataListAuto();
                    for (DataListAuto s : response.body().getDataListAuto()) {
                        str.add(s.getName().toString());
                    }
                    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,
                            android.R.layout.select_dialog_item, str.toArray(new String[0]));
                    companyEdit.setThreshold(1);
                    companyEdit.setAdapter(adapter1);
                    companyEdit.setTextColor(Color.RED);
                }
            }

            @Override
            public void onFailure(Call<ExampleAuto> call, Throwable t) {

            }
        });
    }
    private void equal(){
        for(int i = 1; i<list.size();i++){

            if(list.get(i).getName().equals(companyEdit.getText().toString())){
                id = list.get(i).getId();
                Log.i("idYangDicari",id+"");
            }
        }
        Log.i("idYangDicari",companyEdit.getText().toString());
    }

    private void validasiEdit() {
//        if(idNumberEdit.getText().toString().trim().length() == 0){
//            Toast.makeText(context, "ID tidak boleh kosong", Toast.LENGTH_SHORT).show();
//        }
        if(codeEdit.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Code tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(firstNameEdit.getText().toString().trim().length() == 0){
            Toast.makeText(context, "First name tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(lastNameEdit.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Last name tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(companyEdit.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Company tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(!validateEmail()){
            String input = "Email: " + emailEdit.getText().toString();
            Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
            return;
        }else{
            callAPIEditEmployee();
        }
    }

    private void callAPIEditEmployee() {
        requestAPI = EmployeeAPIUtilities.getAPI();

        String contentType = "application/json";

        ExampleEdit edit = new ExampleEdit();
//        edit.setId(Integer.valueOf(idNumberEdit.getText().toString()));
        edit.setCode(codeEdit.getText().toString());
        edit.setFirstName(firstNameEdit.getText().toString());
        edit.setLastName(lastNameEdit.getText().toString());
        edit.setMCompanyId(id+"");
        edit.setEmail(emailEdit.getText().toString());

        requestAPI.editEmployee(contentType, edit)
                .enqueue(new Callback<ExampleEdit>() {
                    @Override
                    public void onResponse(Call<ExampleEdit> call, Response<ExampleEdit> response) {
                        if (response.code() == 200){
                            notifSuccess();
//                            idNumberEdit.setText(response.body().getId());
                        }
                    }

                    @Override
                    public void onFailure(Call<ExampleEdit> call, Throwable t) {

                    }
                });
    }
    private void notifSuccess() {
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle("notif")
                .setMessage("success edit")
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

    private boolean validateEmail() {
        String emailInput = emailEdit.getText().toString().trim();

        if (emailInput.isEmpty()) {
            emailEdit.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            emailEdit.setError("masukkan email valid");
            return false;
        } else {
            emailEdit.setError(null);
            return true;
        }
    }

}
