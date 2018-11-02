package com.android.mobilemarcom.t_souvenir;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.AutoCompleteSouvenirStokAdapter;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestSouvenirStockAutoComplete;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseSouvenirStockAutoComplete;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseSouvenirStokCreate;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SouvenirStokActivity extends Activity {
    private Context context = this;
    private AutoCompleteTextView add_t_souvenir_stok_received_by;
    private EditText add_t_souvenir_stok_received_tgl, add_t_souvenir_notes;
    private Button btn_add_t_souvenir_stok_save,btn_add_t_souvenir_stok_cancel;
    private List<RequestSouvenirStockAutoComplete> dataLists = new ArrayList<>();
    private RequestAPIServices apiServices;
    private int id_employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_souvenir_stok);

        add_t_souvenir_stok_received_by = (AutoCompleteTextView) findViewById(R.id.add_t_souvenir_stok_received_by);
        add_t_souvenir_stok_received_by.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestSouvenirStockAutoComplete obj = (RequestSouvenirStockAutoComplete) add_t_souvenir_stok_received_by.getAdapter().getItem(position);
                id_employee = obj.getId();
            }
        });
        Calendar today = Calendar.getInstance();
        final int yearStart = today.get(Calendar.YEAR);
        final int monthStart = today.get(Calendar.MONTH);
        final int dateStart = today.get(Calendar.DATE);
        add_t_souvenir_stok_received_tgl = (EditText) findViewById(R.id.add_t_souvenir_stok_received_tgl);
        add_t_souvenir_stok_received_tgl.setFocusable(false);
        add_t_souvenir_stok_received_tgl.setClickable(true);
        add_t_souvenir_stok_received_tgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        R.style.DatePickerBirthdate,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar selected = Calendar.getInstance();
                                selected.set(year, month, dayOfMonth);

                                //untuk formating date :
                                SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
                                String birtDate = formatDate.format(selected.getTime());
                                try{
                                    add_t_souvenir_stok_received_tgl.setText(birtDate);
                                }catch (Exception p){

                                }
                            }
                        }, yearStart, monthStart, dateStart
                        );
                        datePickerDialog.getDatePicker().setSpinnersShown(true);
                        datePickerDialog.getDatePicker().setCalendarViewShown(false);
                        datePickerDialog.show();
            }
        });

        add_t_souvenir_notes = (EditText) findViewById(R.id.add_t_souvenir_notes);
        
        btn_add_t_souvenir_stok_save = (Button) findViewById(R.id.btn_add_t_souvenir_stok_save);
        btn_add_t_souvenir_stok_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiInput();
            }
        });
        btn_add_t_souvenir_stok_cancel = (Button) findViewById(R.id.btn_add_t_souvenir_stok_cancel);
        btn_add_t_souvenir_stok_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        add_t_souvenir_stok_received_by.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (add_t_souvenir_stok_received_by.getText().toString().length() != 0){
                    tampilkanAutoComplete(add_t_souvenir_stok_received_by.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        tampilkanAutoComplete("g");
    }

    
    private void validasiInput(){
        if (add_t_souvenir_stok_received_by.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Received by is not null !", Toast.LENGTH_SHORT).show();
        } else if (add_t_souvenir_stok_received_tgl.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Received date is not null", Toast.LENGTH_SHORT).show();
        } else {
            String received_by = add_t_souvenir_stok_received_by.getText().toString();
            String received_date = add_t_souvenir_stok_received_tgl.getText().toString();
            String notes = add_t_souvenir_notes.getText().toString();
            saveSouvenirStock(id_employee, received_date, notes );
            finish();
        }
    }

    private void tampilkanAutoComplete(String keyword){
        dataLists = new ArrayList<>();
        String contentType = "application/json";
        final String  tokenAuthorization = SessionManager.getToken(context);
        apiServices = APIUtilities.getAPIServices();
        apiServices.autocompleteSouvenirStok(contentType, tokenAuthorization, keyword).enqueue(new Callback<ResponseSouvenirStockAutoComplete>() {
            @Override
            public void onResponse(Call<ResponseSouvenirStockAutoComplete> call, Response<ResponseSouvenirStockAutoComplete> response) {
                if (response.isSuccessful() && response.code() == 200){
                    if (response.body().getMessage() != null){
                        List<RequestSouvenirStockAutoComplete> tmp = response.body().getDataList();
                        for (int i = 0; i <tmp.size(); i++){
                            RequestSouvenirStockAutoComplete dataListAutoCompleteSouvenirStok = tmp.get(i);
                            dataLists.add(dataListAutoCompleteSouvenirStok);
                        }
                        AutoCompleteSouvenirStokAdapter souvenirStokAdapterAuto = new AutoCompleteSouvenirStokAdapter(context,
                                R.layout.custom_autocomplete_souvenir_stok,
                                (ArrayList<RequestSouvenirStockAutoComplete>)dataLists);
                        add_t_souvenir_stok_received_by.setAdapter(souvenirStokAdapterAuto);
                    } else {
                        Toast.makeText(context, "Data not Found..!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseSouvenirStockAutoComplete> call, Throwable t) {
                Toast.makeText(context, "onFailure Error : "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSouvenirStock(Object received_by, String received_date, String notes){
        String contentType = "application/json";
        String json = APIUtilities.createSouvenirStok(received_by, received_date, notes );
        final String  tokenAuthorization = SessionManager.getToken(context);
        final RequestBody bodyRequest = RequestBody.create(APIUtilities.mediaType(), json);

        apiServices = APIUtilities.getAPIServices();
        apiServices.createSouvenirStok(contentType, tokenAuthorization, bodyRequest).enqueue(new Callback<ResponseSouvenirStokCreate>() {
            @Override
            public void onResponse(Call<ResponseSouvenirStokCreate> call, Response<ResponseSouvenirStokCreate> response) {
                try {
                    if (response.code() == 201) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(context, response.code()+ " : "+response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e(this.getClass().toString(),"onResponse error Exception" + bodyRequest+ "\n"+ e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<ResponseSouvenirStokCreate> call, Throwable t) {
                Toast.makeText(context, "Error OnFailure : "+bodyRequest + "\n" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
