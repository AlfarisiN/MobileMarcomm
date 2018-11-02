package com.android.mobilemarcom.souvenir;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.AutoCompleteSouvenirAdapter;
import com.android.mobilemarcom.model.ModelSouvenir.DataListModel;
import com.android.mobilemarcom.model.ModelSouvenir.DataListSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.RequestAutoCompleteSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.ResponseAutoCompleteSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.ResponseSouvenirCreate;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SouvenirDaftarActivity extends Activity {
    private Context context = this;
    private EditText daftar_souvenir_name, daftar_souvenir_notes;
    private AutoCompleteTextView daftar_souvenir_unit;
    private List<DataListModel> dataListModels = new ArrayList<>();
    private List<RequestAutoCompleteSouvenir> dataLists = new ArrayList<>();
    private Button btn_daftar_souvenir_save, btn_daftar_souvenir_cancel;
    private android.support.v7.widget.Toolbar toolbarDaftarSouvenir;
    private RequestAPIServices apiServices;
    private int id_souvenir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenir_daftar);

        toolbarDaftarSouvenir = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_souvenirdaftar);
        daftar_souvenir_name = (EditText) findViewById(R.id.daftar_souvenir_name);
        daftar_souvenir_notes = (EditText) findViewById(R.id.daftar_souvenir_notes);
        daftar_souvenir_unit = (AutoCompleteTextView) findViewById(R.id.daftar_souvenir_unit);
        daftar_souvenir_unit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestAutoCompleteSouvenir obj = (RequestAutoCompleteSouvenir) daftar_souvenir_unit.getAdapter().getItem(position);
                id_souvenir = obj.getId();
            }
        });
        btn_daftar_souvenir_save = (Button) findViewById(R.id.btn_daftar_souvenir_save);
        btn_daftar_souvenir_cancel = (Button) findViewById(R.id.btn_daftar_souvenir_cancel);
        btn_daftar_souvenir_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiInput();
            }
        });

        btn_daftar_souvenir_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarDaftarSouvenir.setTitle("Daftar Souvenir");
        toolbarDaftarSouvenir.setNavigationIcon(R.mipmap.icon_back_white);
        toolbarDaftarSouvenir.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        daftar_souvenir_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (daftar_souvenir_unit.getText().toString().length() != 0){
                    tampilkanAutoComplete(daftar_souvenir_unit.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tampilkanAutoComplete("q");
        getCannotName();
    }

    private void validasiInput(){
        String [] temp;
        temp = daftar_souvenir_name.getText().toString().split(" ");

        if (daftar_souvenir_name.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Name is not null!", Toast.LENGTH_SHORT).show();
        }
//        else if(daftar_souvenir_notes.getText().toString().trim().length() == 0){
//            Toast.makeText(context, "Notes is not null!", Toast.LENGTH_SHORT).show();
//        }
        else if (nametidakbolehsama(temp[0] /*daftar_souvenir_name.getText().toString())*/)){
            Toast.makeText(context, "Nama depan tidak boleh sama", Toast.LENGTH_SHORT).show();
        } else {
            //logic insert buku ke database
            String souvenir_name = daftar_souvenir_name.getText().toString();
            String souvenir_notes = daftar_souvenir_notes.getText().toString();
            saveSouvenir(souvenir_name,id_souvenir,souvenir_notes);
        }
    }

    private void getCannotName(){
        String contentType = "application/json";
        final String  tokenAuthorization = SessionManager.getToken(context);
        apiServices = APIUtilities.getAPIServices();
        apiServices.searchSouvenirName(contentType, tokenAuthorization, "g").enqueue(new Callback<DataListSouvenir>() {
            @Override
            public void onResponse(Call<DataListSouvenir> call, Response<DataListSouvenir> response) {
                if (response.code() == 200){
                    List<DataListModel> tmp = response.body().getDataList();
                    for (int i = 0; i < tmp.size(); i++){
                        DataListModel dataListModel = tmp.get(i);
                        dataListModels.add(dataListModel);
                    }

                } else {
                    Toast.makeText(context, "Failure Search Item RequestGetItemSouvenirStokSouvenir : "+response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataListSouvenir> call, Throwable t) {
                Toast.makeText(context, "onFailure RequestGetItemSouvenirStokSouvenir : "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean nametidakbolehsama(String souvenir_name) {
        boolean hasil = false;
        for (int i = 0; i < dataListModels.size(); i++) {
           String temp = dataListModels.get(i).getName();
           String [] tempData = temp.split(" ");
            if (souvenir_name.toLowerCase().equals(tempData[0].toLowerCase())) {
                hasil = true;
            }
        }
        return hasil;
    }

    private void tampilkanAutoComplete(String keyword){
        dataLists = new ArrayList<>();
        String contentType = "application/json";
        final String  tokenAuthorization = SessionManager.getToken(context);
        apiServices = APIUtilities.getAPIServices();
        apiServices.autocompleteSouvenir(contentType, tokenAuthorization, keyword).enqueue(new Callback<ResponseAutoCompleteSouvenir>() {
            @Override
            public void onResponse(Call<ResponseAutoCompleteSouvenir> call, Response<ResponseAutoCompleteSouvenir> response) {
                if (response.code() == 200){
                    try{
                        if (response.body().getMessage() != null){
                            List<RequestAutoCompleteSouvenir> tmp = response.body().getDataList();
                            for (int i = 0; i<tmp.size(); i++){
                                RequestAutoCompleteSouvenir dataListAutoCompleteSouvenir = tmp.get(i);
                                dataLists.add(dataListAutoCompleteSouvenir);
                            }
                            AutoCompleteSouvenirAdapter souvenirAdapterAuto = new AutoCompleteSouvenirAdapter(context,
                                    R.layout.custom_autocomplete_souvenir,
                                    (ArrayList<RequestAutoCompleteSouvenir>) dataLists);
                            daftar_souvenir_unit.setAdapter(souvenirAdapterAuto);
                        } else {
                            Toast.makeText(context, "Data not Found..!!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        Toast.makeText(context, "onResponse Error Exeception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseAutoCompleteSouvenir> call, Throwable t) {
                Toast.makeText(context, "onFailure Error : "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveSouvenir(String souvenir_name, Object souvenir_unit, String souvenir_notes){
        String contentType = "application/json";
        String json = APIUtilities.generateSouvenirMap(souvenir_name, souvenir_unit, souvenir_notes);
        final String  tokenAuthorization = SessionManager.getToken(context);
        RequestBody bodyRequest = RequestBody.create(APIUtilities.mediaType(), json);

        apiServices = APIUtilities.getAPIServices();
        apiServices.createSouvenir(contentType, tokenAuthorization, bodyRequest).enqueue(new Callback<ResponseSouvenirCreate>() {
            @Override
            public void onResponse(Call<ResponseSouvenirCreate> call, Response<ResponseSouvenirCreate> response) {
                try{
                    if (response.isSuccessful() && response.code() == 201){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else {
                        Toast.makeText(context, "Create RequestSouvenirStockItemId Gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(context, "onResponse Error Exeception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSouvenirCreate> call, Throwable t) {
                Toast.makeText(context, "Error onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
