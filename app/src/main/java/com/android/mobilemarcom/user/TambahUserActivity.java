package com.android.mobilemarcom.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.EmployeAdapterAutoComplete;
import com.android.mobilemarcom.adapters.RoleAdapterAutoComplete;
import com.android.mobilemarcom.model.user.DataList;
import com.android.mobilemarcom.model.user.DataListAutoCompleteEmploye;
import com.android.mobilemarcom.model.user.DataListAutoCompleteRole;
import com.android.mobilemarcom.model.user.Example;
import com.android.mobilemarcom.model.user.ExampleAutoCompleteEmploye;
import com.android.mobilemarcom.model.user.ExampleAutoCompleteRole;
import com.android.mobilemarcom.model.user.UserAdd;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahUserActivity extends Activity {

    private Context context = this;
    private Button button1,button2;
    private Toolbar mToolbar;
    private TextInputLayout role_text_input_layout,employee_text_input_layout,username_text_input_layout,retypepassword_text_input_layout,password_text_input_layout;
    private EditText etUsername,etPassword,etRetypepassword;
    private AutoCompleteTextView autoCompleteRole,autoCompleteEmploye;
    private RequestAPIServices requestAPIServices;
    private int id_employee,id_role;
    private List<DataList> datalistusername = new ArrayList<>();
    private List<DataListAutoCompleteRole> dataLists = new ArrayList<>();
    private List<DataListAutoCompleteEmploye> dataLists1 = new ArrayList<>();
    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +      //any letter
                    ".{3,}" +               //at least 4 characters
                    "$");
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_user);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_tambah);
        etUsername = (EditText) findViewById(R.id.etUsername_tambah);
        etPassword = (EditText) findViewById(R.id.etPassword_tambah);
        etRetypepassword = (EditText) findViewById(R.id.etRetypepassword_tambah);
        username_text_input_layout = (TextInputLayout) findViewById(R.id.username_text_input_layout_tambah);
        role_text_input_layout = (TextInputLayout) findViewById(R.id.role_text_input_layout_tambah);
        employee_text_input_layout = (TextInputLayout) findViewById(R.id.employee_text_input_layout_tambah);
        retypepassword_text_input_layout = (TextInputLayout) findViewById(R.id.retypepassword_text_input_layout_tambah);
        password_text_input_layout = (TextInputLayout) findViewById(R.id.password_text_input_layout_tambah);
        button1 = (Button) findViewById(R.id.button1_tambah);
        button2 = (Button) findViewById(R.id.button2_tambah);
        autoCompleteRole = (AutoCompleteTextView) findViewById(R.id.autoCompleteRole_tambah);
        autoCompleteEmploye = (AutoCompleteTextView) findViewById(R.id.autoCompleteEmploye_tambah);
        mToolbar.setTitle("Tambah User");
        mToolbar.setNavigationIcon(R.mipmap.icon_back_white);
        autoCompleteRole.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataListAutoCompleteRole obj= (DataListAutoCompleteRole) autoCompleteRole.getAdapter().getItem(position);
                id_role = obj.getId();
            }
        });

        autoCompleteEmploye.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataListAutoCompleteEmploye obj= (DataListAutoCompleteEmploye) autoCompleteEmploye.getAdapter().getItem(position);
                id_employee = obj.getId();
            }
        });

        autoCompleteEmploye.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(autoCompleteEmploye.getText().toString().length()!=0){
                    tampil_auto_complete_employee(autoCompleteEmploye.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        autoCompleteRole.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(autoCompleteRole.getText().toString().length()!=0){
                    tampil_auto_complete_role(autoCompleteRole.getText().toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getUsername();
    }

    private void getUsername(){
        String contentType = "application/json";
        String token = SessionManager.getToken(context);
        requestAPIServices = APIUtilities.getAPIServices();
        requestAPIServices.getDataListUser(contentType,token,"q").enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.code()==200){
                    if(response.body().getMessage()!=null){
                        List<DataList> tmp = response.body().getDataList();
                        for(int i=0;i<tmp.size();i++){
                            DataList dataList = tmp.get(i);
                            datalistusername.add(dataList);
                        }
                    }
                }else{
                    Toast.makeText(context,"Failure "+response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                System.out.println("Failure "+t.getMessage());
            }
        });
    }

    private void tampil_auto_complete_role(String keyword){
        dataLists = new ArrayList<>();
        String contentType = "application/json";
        String token = SessionManager.getToken(context);
        requestAPIServices = APIUtilities.getAPIServices();
        requestAPIServices.roleautocompletee(contentType,token,keyword).enqueue(new Callback<ExampleAutoCompleteRole>() {
            @Override
            public void onResponse(Call<ExampleAutoCompleteRole> call, Response<ExampleAutoCompleteRole> response) {
                if(response.code()==200){
                    if(response.body().getMessage()!=null){
                        List<DataListAutoCompleteRole> tmp = response.body().getDataList();
                        for(int i=0;i<tmp.size();i++){
                            DataListAutoCompleteRole dataListAutoCompleteRole = tmp.get(i);
                            dataLists.add(dataListAutoCompleteRole);
                        }
                        RoleAdapterAutoComplete roleAdapterAutoComplete= new RoleAdapterAutoComplete(context, R.layout.custom_autocomplete_role, (ArrayList<DataListAutoCompleteRole>) dataLists);
                        autoCompleteRole.setAdapter(roleAdapterAutoComplete);
                    }else{
                        Toast.makeText(context,"Data not found",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ExampleAutoCompleteRole> call, Throwable t) {
                System.out.println("Error "+t.getMessage());
            }
        });
    }

    private void tampil_auto_complete_employee(String keyword){
        dataLists1 = new ArrayList<>();
        String contentType = "application/json";
        String token = SessionManager.getToken(context);
        requestAPIServices = APIUtilities.getAPIServices();
        requestAPIServices.employeautocompletee(contentType,token,keyword).enqueue(new Callback<ExampleAutoCompleteEmploye>() {
            @Override
            public void onResponse(Call<ExampleAutoCompleteEmploye> call, Response<ExampleAutoCompleteEmploye> response) {
                if(response.body().getMessage()!=null){
                    List<DataListAutoCompleteEmploye> tmp = response.body().getDataList();
                    for(int i=0;i<tmp.size();i++){
                        DataListAutoCompleteEmploye dataListAutoCompleteEmploye = tmp.get(i);
                        dataLists1.add(dataListAutoCompleteEmploye);
                    }
                    EmployeAdapterAutoComplete employeAdapterAutoComplete= new EmployeAdapterAutoComplete(context, R.layout.custom_autocomplete_employe, (ArrayList<DataListAutoCompleteEmploye>) dataLists1);
                    autoCompleteEmploye.setAdapter(employeAdapterAutoComplete);
                }else{
                    Toast.makeText(context,"Data not found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ExampleAutoCompleteEmploye> call, Throwable t) {
                System.out.println("Error "+t.getMessage());
            }
        });
    }

    private void validasi(){
        if (!validateEmployee() | !validateRole() | !validateUsername() | !validatePassword() | !validateRetypePassword()) {
            return;
        } else{
            String username = etUsername.getText().toString();
            String password = md5(etPassword.getText().toString());
            tambahUser(username,password,id_role,id_employee);
        }
    }

    private void tambahUser(String username,String password,Object mRoleId,Object mEmployeeId){
        String json = APIUtilities.addUser(username,password,mRoleId,mEmployeeId);
        RequestBody requestBody = RequestBody.create(APIUtilities.mediaType(),json);

        final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,
                "Waiting...");
        loading.show();

        String contentType = "application/json";
        String token = SessionManager.getToken(context);

        requestAPIServices = APIUtilities.getAPIServices();
        requestAPIServices.createUser(contentType,token,requestBody).enqueue(new Callback<UserAdd>() {
            @Override
            public void onResponse(Call<UserAdd> call, Response<UserAdd> response) {
                loading.dismiss();
                if(response.code()==201){
                    if(response.body().getMessage()!=null){
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }else{
                    Toast.makeText(context,"Data gagal ditambahkan",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<UserAdd> call, Throwable t) {
                loading.dismiss();
                System.out.println("Error "+t.getMessage());
            }
        });
    }

    private boolean validateRole(){
        String check = autoCompleteRole.getText().toString().trim();
        if(check.isEmpty()){
            role_text_input_layout.setError("Role tidak boleh kosong");
            return false;
        }else{
            role_text_input_layout.setError(null);
            return true;
        }
    }

    private boolean validateEmployee(){
        String check = autoCompleteEmploye.getText().toString().trim();
        if(check.isEmpty()){
            employee_text_input_layout.setError("Employee tidak boleh kosong");
            return false;
        }else{
            employee_text_input_layout.setError(null);
            return true;
        }
    }

    private boolean validateUsername(){
        String check = etUsername.getText().toString().trim();
        if(check.isEmpty()){
            username_text_input_layout.setError("Username tidak boleh kosong");
            return false;
        }else if(check.length()<8){
            username_text_input_layout.setError("Minimal 8 karakter");
            return false;
        }
        else if(!USERNAME_PATTERN.matcher(check).matches()){
            username_text_input_layout.setError("Kombinasi Huruf dan Angka");
            return false;
        }else if(this.usernametidakbolehsama(check)==true){
            username_text_input_layout.setError("Username tidak boleh sama");
            return false;
        } else{
            username_text_input_layout.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String check = etPassword.getText().toString().trim();
        if (check.isEmpty()){
            password_text_input_layout.setError("Password tidak boleh kosong");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(check).matches()){
            password_text_input_layout.setError("Harus ada huruf besar dan Kombinasi huruf dan angka");
            return false;
        }else{
            password_text_input_layout.setError(null);
            return true;
        }
    }

    private boolean validateRetypePassword(){
        String check = etRetypepassword.getText().toString().trim();
        String check1 = etPassword.getText().toString().trim();
        if(check.isEmpty()){
            retypepassword_text_input_layout.setError("Retype Password tidak boleh kosong");
            return false;
        }else if(!check.equals(check1)){
            retypepassword_text_input_layout.setError("Retype Password harus sesuai dengan password");
            return false;
        }else{
            retypepassword_text_input_layout.setError(null);
            return true;
        }
    }

    private boolean usernametidakbolehsama(String a){
        boolean hasil = false;
        for (int i=0; i<datalistusername.size();i++){
            if(a.toLowerCase().equals(datalistusername.get(i).getUsername().toLowerCase())){
                hasil = true;
            }
        }
        return hasil;
    }

}









