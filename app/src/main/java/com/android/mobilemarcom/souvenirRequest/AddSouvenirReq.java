package com.android.mobilemarcom.souvenirRequest;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.modelevent.ModelEventRetrofit;
import com.android.mobilemarcom.souvenirRequest.API.APIUtilities;
import com.android.mobilemarcom.souvenirRequest.API.ExampleAdd;
import com.android.mobilemarcom.souvenirRequest.API.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSouvenirReq extends AppCompatActivity {private Context context = this;

    private EditText add_request_souvenir_duedate;
    private EditText add_request_souvenir_notes;
    private EditText lastName;
    private AutoCompleteTextView add_request_souvenir_event;
    private EditText email;
    private Button btn_request_souvenir_save;
    private Button btn_request_souvenir_cancel;
    private TextInputLayout layoutReqSouv;

    private RequestAPIServices requestAPI;
    private com.android.mobilemarcom.retrofit.RequestAPIServices apiServices;
    private List<DataList> dataListAuto;
    private List<com.android.mobilemarcom.model.modelevent.DataList> auto2;
    KArrayAdapter<com.android.mobilemarcom.model.modelevent.DataList> adapter;
    private boolean isNameSelected;
    private int aidi;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_souvenir_req);
//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setTitle("Input Biodata");

        Calendar today = Calendar.getInstance();
        final int yearStart = today.get(Calendar.YEAR);
        final int monthStart = today.get(Calendar.MONTH);
        final int dateStart = today.get(Calendar.DATE);

        add_request_souvenir_duedate = (EditText) findViewById(R.id.add_request_souvenir_duedate);
        add_request_souvenir_duedate.setFocusable(false);
        add_request_souvenir_duedate.setClickable(true);
        add_request_souvenir_duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        R.style.DatePickerBirthdate,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selected = Calendar.getInstance();
                                selected.set(year, month, dayOfMonth);

                                //utk formatting date & konversi ke string
                                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                                String birthDate = formatDate.format(selected.getTime());

                                add_request_souvenir_duedate.setText(birthDate);
                            }
                        }, yearStart, monthStart, dateStart
                );
                datePickerDialog.getDatePicker().setSpinnersShown(true);
                datePickerDialog.getDatePicker().setCalendarViewShown(false);
                datePickerDialog.show();
            }
        });
        add_request_souvenir_notes = (EditText) findViewById(R.id.add_request_souvenir_notes);
//        lastName = (EditText) findViewById(R.id.lastName);
//        email = (EditText) findViewById(R.id.email);
        add_request_souvenir_event = (AutoCompleteTextView) findViewById(R.id.add_request_souvenir_event);
        add_request_souvenir_event.setThreshold(1);
        add_request_souvenir_event.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isNameSelected = true;
                add_request_souvenir_event.setError(null);

                com.android.mobilemarcom.model.modelevent.DataList selected = (com.android.mobilemarcom.model.modelevent.DataList) parent.getAdapter().getItem(position);
                aidi = selected.getId();
                Toast.makeText(context," "+aidi,Toast.LENGTH_LONG).show();
            }
        });
        add_request_souvenir_event.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                isNameSelected = false;
                add_request_souvenir_event.setError("Event must from list");
                auto2 = new ArrayList<>();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(add_request_souvenir_event.getText().toString().trim().length() != 0){
//                    String contentType = "application/json";
                    String keyword = add_request_souvenir_event.getText().toString().trim();
                    getAutoCompleteAPI(keyword);
                }
            }
        });
        layoutReqSouv = (TextInputLayout) findViewById(R.id.layoutReqSouv);
//

        btn_request_souvenir_save = (Button) findViewById(R.id.btn_request_souvenir_save);
        btn_request_souvenir_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiInput();
            }
        });

        btn_request_souvenir_cancel = (Button) findViewById(R.id.btn_request_souvenir_cancel);
        btn_request_souvenir_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
//        tampilautocomplete(add_request_souvenir_event.getText().toString());
    }

    private void getAutoCompleteAPI(String keyword) {
        String contentType = "application/json";
        String tokenAuthorization = SessionManager.getToken(context);
        apiServices = com.android.mobilemarcom.retrofit.APIUtilities.getAPIServices();
        apiServices.autoCompleteEvent(tokenAuthorization, keyword).enqueue(new Callback<ModelEventRetrofit>() {
            @Override
            public void onResponse(Call<ModelEventRetrofit> call, Response<ModelEventRetrofit> response) {
                if(response.code()==200){
                    List<com.android.mobilemarcom.model.modelevent.DataList> tmp = response.body().getDataList();
                    auto2 = response.body().getDataList();
                    getAutoCompletAdapter();
                }
                Toast.makeText(context,""+response.body().getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ModelEventRetrofit> call, Throwable t) {

            }
        });
    }

//    private void tampilautocomplete(String keyword){
//        String contentType = "application/json";
//        String tokenAuthorization = SessionManager.getToken(context);
//        requestAPI = APIUtilities.getAPI();
//        requestAPI.souvenirReqAuto(contentType, tokenAuthorization, keyword).enqueue(new Callback<Example>() {
//            @Override
//            public void onResponse(Call<Example> call, Response<Example> response) {
//                if(response.code()==200){
//                    List<String> str = new ArrayList<String>();
//                    for (DataList dataListAuto : response.body().getDataList()){
//                        str.add(dataListAuto.getName());
//                    }
//                    final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context,android.R.layout.select_dialog_item,str.toArray(new String[0]));
//                    add_request_souvenir_event.setThreshold(1);
//                    add_request_souvenir_event.setAdapter(adapter1);
//                    add_request_souvenir_event.setTextColor(Color.RED);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Example> call, Throwable t) {
//
//            }
//        });
//    }

    private void validasiInput() {
        if(add_request_souvenir_notes.getText().toString().trim().length() == 0){
            Toast.makeText(context, "tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(add_request_souvenir_event.getText().toString().trim().length() == 0){
            Toast.makeText(context, "tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if(add_request_souvenir_duedate.getText().toString().trim().length() == 0){
            Toast.makeText(context, "tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else{
            createSouvenirReq(aidi, add_request_souvenir_duedate.getText().toString().trim(), add_request_souvenir_notes.getText().toString().trim());
        }
    }

    private void createSouvenirReq(int souvenir_name, String dueDate, String notes){
        String contentType = "application/json";
        String tokenAuthorization = SessionManager.getToken(context);
        String json = APIUtilities.generateSouvenirReqAdd(souvenir_name, dueDate, notes);
        RequestBody bodyRequest = RequestBody.create(APIUtilities.mediaType(), json);

        requestAPI = APIUtilities.getAPI();
        requestAPI.souvenirReqAdd(contentType, tokenAuthorization, bodyRequest).enqueue(new Callback<ExampleAdd>() {
            @Override
            public void onResponse(Call<ExampleAdd> call, Response<ExampleAdd> response) {
                if(response.code() == 201){
                    Toast.makeText(context, "Success Added", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(context, "Create SouvenirAddItem Gagal : "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ExampleAdd> call, Throwable t) {
                Toast.makeText(context, "Create SouvenirAddItem onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAutoCompletAdapter() {
        adapter = new KArrayAdapter<>
                (context, android.R.layout.simple_list_item_1, auto2);
        add_request_souvenir_event.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}