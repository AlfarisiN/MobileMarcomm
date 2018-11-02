package com.android.mobilemarcom.souvenirRequest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseGetItemSouvenirStok;
import com.android.mobilemarcom.souvenirRequest.API.APIUtilities;
import com.android.mobilemarcom.souvenirRequest.API.RequestAPIServices;
import com.android.mobilemarcom.souvenirRequest.API.SouvenirAddItem;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddItemReqSouv extends AppCompatActivity {
    private Context context = this;
    private AutoCompleteTextView daftar_t_souvenir_souvenir;
    private EditText daftar_t_souvenir_quantity;
    private EditText daftar_t_souvenir_notes;
    private RecyclerView recycler_souvenir;

    private Button btn_daftar_t_souvenir_save;
    private Button btn_daftar_t_souvenir_cancel;
    private String id;


    private RequestAPIServices requestAPI;
    private List<com.android.mobilemarcom.souvenirRequest.d.DataList> listSouvenirAddItem = new ArrayList<>();
    private List<SouvenirAddItem> dataList2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_souvenir_additem);

        daftar_t_souvenir_quantity = (EditText) findViewById(R.id.daftar_t_souvenir_quantity);
        daftar_t_souvenir_notes = (EditText) findViewById(R.id.daftar_t_souvenir_notes);

        recycler_souvenir = (RecyclerView) findViewById(R.id.recycler_souvenir);

        daftar_t_souvenir_souvenir = (AutoCompleteTextView) findViewById(R.id.daftar_t_souvenir_souvenir);

        btn_daftar_t_souvenir_save = (Button) findViewById(R.id.btn_daftar_t_souvenir_save);
        btn_daftar_t_souvenir_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validasiInput();
            }
        });
        tampilkanAutoComplete();
        tampilkanListAddItem();

    }
    private void tampilkanListAddItem(){
        String contentType = "application/json";
        final String  tokenAuthorization = SessionManager.getToken(context);
        requestAPI = APIUtilities.getAPI();
        requestAPI.souvenirAddItem(contentType, tokenAuthorization, "1").enqueue(new Callback<com.android.mobilemarcom.souvenirRequest.d.Example>() {
            @Override
            public void onResponse(Call<com.android.mobilemarcom.souvenirRequest.d.Example> call, Response<com.android.mobilemarcom.souvenirRequest.d.Example> response) {
                try{
                    if (response.code() == 200){
                        List<com.android.mobilemarcom.souvenirRequest.d.DataList> tmp = response.body().getDataList();
                        for (int i = 0; i < tmp.size(); i++) {
                            com.android.mobilemarcom.souvenirRequest.d.DataList dataListModel = tmp.get(i);
//                            Log.d("sd"+tmp.size());
                            listSouvenirAddItem.add(dataListModel);
                        }
//                        Toast.makeText(context,response.body().getDataListAddItem().size(),Toast.LENGTH_SHORT).show();
                        final SouvenirReqItemAdapter addAdapter = new SouvenirReqItemAdapter(context, response.body().getDataList());
                        addAdapter.notifyDataSetChanged();
                        recycler_souvenir.setAdapter(addAdapter);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                                LinearLayout.VERTICAL,false);
                        recycler_souvenir.setLayoutManager(layoutManager);
                        recycler_souvenir.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e(this.getClass().toString(),"onResponse error Exception : " + "\n"+ e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<com.android.mobilemarcom.souvenirRequest.d.Example> call, Throwable t) {
                Toast.makeText(context, "onFailure :"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tampilkanAutoComplete(){
        String contentType = "application/json";
        final String  tokenAuthorization = SessionManager.getToken(context);
        requestAPI = APIUtilities.getAPI();
        requestAPI.souvenirReqAuto(contentType, tokenAuthorization, "g").enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                try {
                    if (response.code() == 200){
                        List<String> strings = new ArrayList<>();
                        for (DataList s  : response.body().getDataList()){
                            strings.add(""+s.getName());
                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                                android.R.layout.select_dialog_item,
                                strings.toArray(new String[0]));
                        daftar_t_souvenir_souvenir.setThreshold(1);
                        daftar_t_souvenir_souvenir.setAdapter(adapter);
                    } else {
                        Toast.makeText(context, "onResponse error : "+response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e(this.getClass().toString(),"onResponse error Exception :" + "\n"+ e.getMessage(), e);
                }
            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(context, "Error onFailure", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void validasiInput() {
        if (daftar_t_souvenir_souvenir.getText().toString().trim().length() == 0){
            Toast.makeText(context, "souvenir is not null!", Toast.LENGTH_SHORT).show();
        } else if(daftar_t_souvenir_quantity.getText().toString().trim().length() == 0){
            Toast.makeText(context, "quantity is not null!", Toast.LENGTH_SHORT).show();
        } else {
            //logic insert buku ke database
            String souvenir = daftar_t_souvenir_souvenir.getText().toString();
            String quantity = daftar_t_souvenir_quantity.getText().toString();
            String notes = daftar_t_souvenir_notes.getText().toString();
            createSouvenirReq(id);
            finish();
        }
    }

    private void createSouvenirReq(String id){
        String contentType = "application/json";
//        String json = com.android.mobilemarcom.souvenirRequest.API.APIUtilities.generateSouvenirAddItem(souvenir, quantity, notes);
        String  tokenAuthorization = SessionManager.getToken(context);
//        RequestBody bodyRequest = RequestBody.create(com.android.mobilemarcom.souvenirRequest.API.APIUtilities.mediaType(), json);

        requestAPI = com.android.mobilemarcom.souvenirRequest.API.APIUtilities.getAPI();
        requestAPI.souvenirCreateAdd(contentType, tokenAuthorization, Integer.parseInt(id)).enqueue(new Callback<ResponseGetItemSouvenirStok>() {
            @Override
            public void onResponse(Call<ResponseGetItemSouvenirStok> call, Response<ResponseGetItemSouvenirStok> response) {
                try{
                    if (response.code() == 201){
                        Toast.makeText(context, "Data Added"+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "onResponse Error : "+response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    Toast.makeText(context, "onResponse Error Execption : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseGetItemSouvenirStok> call, Throwable t) {
                Toast.makeText(context, "Error onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
