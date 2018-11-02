package com.android.mobilemarcom.souvenir;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import com.android.mobilemarcom.adapters.AutoCompleteSouvenirAdapter;
import com.android.mobilemarcom.model.ModelSouvenir.DataListModel;
import com.android.mobilemarcom.model.ModelSouvenir.DataListSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.RequestAutoCompleteSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.ResponseAutoCompleteSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.ResponseSouvenirEdit;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditSouvenirActivity extends Activity {
    private Context context = this;
    private EditText edit_souvenir_name,edit_souvenir_notes;
    private AutoCompleteTextView edit_souvenir_unit;
    private Button btn_edit_souvenir_save, btn_edit_souvenir_cancel;
    private Toolbar toolbarSouvenirEdit;
    private RequestAPIServices apiServices;
    private int id_souvenir_edit;
    private List<DataListModel> dataListModels = new ArrayList<>();
    private List<RequestAutoCompleteSouvenir> dataLists = new ArrayList<>();
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenir_edit);
        final String name, unit;
        toolbarSouvenirEdit = (Toolbar) findViewById(R.id.toolbar_souveniredit);
        edit_souvenir_name = (EditText) findViewById(R.id.edit_souvenir_name);
        edit_souvenir_notes = (EditText) findViewById(R.id.edit_souvenir_notes);
        edit_souvenir_unit = (AutoCompleteTextView) findViewById(R.id.edit_souvenir_unit);
        toolbarSouvenirEdit.setTitle("Edit Souvenir");
        toolbarSouvenirEdit.setNavigationIcon(R.mipmap.icon_back_white);

        edit_souvenir_unit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestAutoCompleteSouvenir obj = (RequestAutoCompleteSouvenir) edit_souvenir_unit.getAdapter().getItem(position);
                id_souvenir_edit = obj.getId();
            }
        });

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            name = extras.getString("name");
            unit = extras.getString("unit");
            id = extras.getInt("id");
        } else {
            name = null;
            unit = null;
        }

        edit_souvenir_name.setText(name);
        edit_souvenir_unit.setText(unit);

        id_souvenir_edit = extras.getInt("id");

        btn_edit_souvenir_save = (Button) findViewById(R.id.btn_edit_souvenir_save);
        btn_edit_souvenir_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiInput();
            }
        });
        btn_edit_souvenir_cancel = (Button) findViewById(R.id.btn_edit_souvenir_cancel);
        btn_edit_souvenir_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit_souvenir_unit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (edit_souvenir_unit.getText().toString().length() != 0){
                    tampilkanAutoComplete(edit_souvenir_unit.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        toolbarSouvenirEdit.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tampilkanAutoComplete("g");
        getNameEquals();
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
                    if (response.body().getMessage() != null){
                        List<RequestAutoCompleteSouvenir> tmp = response.body().getDataList();
                        for (int i = 0; i<tmp.size(); i++){
                            RequestAutoCompleteSouvenir dataListAutoCompleteSouvenir = tmp.get(i);
                            dataLists.add(dataListAutoCompleteSouvenir);
                        }
                        AutoCompleteSouvenirAdapter souvenirAdapterAuto = new AutoCompleteSouvenirAdapter(context,
                                R.layout.custom_autocomplete_souvenir,
                                (ArrayList<RequestAutoCompleteSouvenir>) dataLists);
                        edit_souvenir_unit.setAdapter(souvenirAdapterAuto);
                    } else {
                        Toast.makeText(context, "Data not Found..!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseAutoCompleteSouvenir> call, Throwable t) {
                Toast.makeText(context, "onFailure Error : "+t.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editSouvenir(Object id, String name, Object id_souvenir_edit, String notes){
        String json = APIUtilities.editSouvenir(id,name,id_souvenir_edit,notes);
        RequestBody requestBody = RequestBody.create(APIUtilities.mediaType(),json);
        String contentType = "application/json";
        final String token = SessionManager.getToken(context);

        apiServices = APIUtilities.getAPIServices();
        apiServices.updateSouvenir(contentType, token, requestBody).enqueue(new Callback<ResponseSouvenirEdit>() {
            @Override
            public void onResponse(Call<ResponseSouvenirEdit> call, Response<ResponseSouvenirEdit> response) {
                if (response.code() == 200){
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(context, "Data gagal diedit", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSouvenirEdit> call, Throwable t) {
                Toast.makeText(context, "Error onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getNameEquals(){
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
//            String temp = dataListModels.get(i).getName();
//            String [] tempData = temp.split(" ");
            if (souvenir_name.toLowerCase().equals(dataListModels.get(i).getName().toLowerCase()/*tempData[0].toLowerCase())*/)) {
                hasil = true;
            }
        }
        return hasil;
    }

    private void validasiInput(){
//        String [] temp;
//        temp = edit_souvenir_name.getText().toString().split(" ");
        if (edit_souvenir_name.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Name is not null!", Toast.LENGTH_SHORT).show();
        }
//        else if(edit_souvenir_notes.getText().toString().trim().length() == 0){
//            Toast.makeText(context, "Notes is not null!", Toast.LENGTH_SHORT).show();
//        }
//        else if (nametidakbolehsama(edit_souvenir_name.getText().toString()/*temp[0])*/)){
//            Toast.makeText(context, "Nama depan tidak boleh sama", Toast.LENGTH_SHORT).show();
//        }
        else {
            editSouvenir(id, edit_souvenir_name.getText().toString(), id_souvenir_edit, edit_souvenir_notes.getText().toString());
        }
    }
}