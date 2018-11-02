package com.android.mobilemarcom.souvenirRequest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.AutoCompleteSouvenirAdapter;
import com.android.mobilemarcom.adapters.SouvenirReqAddAdapter;
import com.android.mobilemarcom.model.ModelSouvenir.RequestAutoCompleteSouvenir;
import com.android.mobilemarcom.model.ModelSouvenir.ResponseAutoCompleteSouvenir;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestGetItemSouvenirStok;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseGetItemSouvenirStok;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseSouvenirStockItem;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SouvenirReqAddItemActivity extends Activity {
    private RecyclerView recyclerSouvenirAddItem;
    private Context context = this;
    private AutoCompleteTextView autoCompleteTextSouvenir;
    private EditText edtQuantity,edtNotes;
    private Button btnSave, btnCancel;
    private List<RequestGetItemSouvenirStok> listSouvenirAddItem = new ArrayList<>();
    private List<RequestAutoCompleteSouvenir> dataLists = new ArrayList<>();
    private RequestAPIServices apiServices;
    private int id_souvenir_add;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_souvenir_stok_additem);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            id = extras.getInt("id");
        }
        autoCompleteTextSouvenir = (AutoCompleteTextView) findViewById(R.id.daftar_t_souvenir_souvenir);
        autoCompleteTextSouvenir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RequestAutoCompleteSouvenir obj = (RequestAutoCompleteSouvenir) autoCompleteTextSouvenir.getAdapter().getItem(position);
                id_souvenir_add = obj.getId();
            }
        });
        edtQuantity = (EditText) findViewById(R.id.daftar_t_souvenir_quantity);
        edtNotes = (EditText) findViewById(R.id.daftar_t_souvenir_notes);
        recyclerSouvenirAddItem = (RecyclerView) findViewById(R.id.recycler_souvenir_additem);


        btnSave = (Button) findViewById(R.id.btn_daftar_t_souvenir_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasiInput();
            }
        });

        btnCancel = (Button) findViewById(R.id.btn_daftar_t_souvenir_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tampilkanAutoComplete("g");
        tampilkanListAddItem(id);
    }

    private void validasiInput(){
        if (autoCompleteTextSouvenir.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Name is not null!", Toast.LENGTH_SHORT).show();
        } else if(edtQuantity.getText().toString().trim().length() == 0){
            Toast.makeText(context, "Notes is not null!", Toast.LENGTH_SHORT).show();
        } else {
            //logic insert buku ke database
            String souvenir = autoCompleteTextSouvenir.getText().toString();
            String quanity = edtQuantity.getText().toString();
            String notes = edtNotes.getText().toString();
            createSouvenirItem(id_souvenir_add, quanity, notes);
            finish();
        }
    }

    private void tampilkanListAddItem(final int id){
        String contentType = "application/json";
        final String  tokenAuthorization = SessionManager.getToken(context);
        apiServices = APIUtilities.getAPIServices();
        apiServices.getSouvenirStockAddItem(contentType, tokenAuthorization, id).enqueue(new Callback<ResponseGetItemSouvenirStok>() {
            @Override
            public void onResponse(Call<ResponseGetItemSouvenirStok> call, Response<ResponseGetItemSouvenirStok> response) {
                try{
                    if (response.code() == 200){
                        List<RequestGetItemSouvenirStok> tmp = response.body().getDataList();
                        for (int i = 0; i < tmp.size(); i++){
                            RequestGetItemSouvenirStok dataList = tmp.get(i);
                            listSouvenirAddItem.add(dataList);
                        }
                        final SouvenirReqAddAdapter addAdapter = new SouvenirReqAddAdapter(context, listSouvenirAddItem, id);
                        recyclerSouvenirAddItem.setAdapter(addAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                                LinearLayout.VERTICAL,false);
                        recyclerSouvenirAddItem.setLayoutManager(layoutManager);
                        recyclerSouvenirAddItem.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e(this.getClass().toString(),"onResponse error Exception : " + "\n"+ e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<ResponseGetItemSouvenirStok> call, Throwable t) {
                Toast.makeText(context, "onFailure :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tampilkanAutoComplete(String keyword){
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
                        autoCompleteTextSouvenir.setAdapter(souvenirAdapterAuto);
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

    private void createSouvenirItem(int souvenir, String quantity, String notes){
        String contentType = "application/json";
        String json = APIUtilities.generateSouvenirItem(souvenir, quantity, notes);
        final String  tokenAuthorization = SessionManager.getToken(context);
        RequestBody bodyRequest = RequestBody.create(APIUtilities.mediaType(), json);

        apiServices = APIUtilities.getAPIServices();
        apiServices.createSouvenirStockItem(contentType, tokenAuthorization, bodyRequest,1).enqueue(new Callback<ResponseSouvenirStockItem>() {
            @Override
            public void onResponse(Call<ResponseSouvenirStockItem> call, Response<ResponseSouvenirStockItem> response) {
                try{
                    if (response.code() == 201){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "onResponse Error : "+response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(context, "onResponse Error Execption : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseSouvenirStockItem> call, Throwable t) {
                Toast.makeText(context, "Error onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}