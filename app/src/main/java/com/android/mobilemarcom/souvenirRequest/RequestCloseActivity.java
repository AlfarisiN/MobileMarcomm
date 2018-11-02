package com.android.mobilemarcom.souvenirRequest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.SouvenirRegGetAdapter;
import com.android.mobilemarcom.model.souvenir_request.CloseSouvenirRequest;
import com.android.mobilemarcom.model.souvenir_request.DataListSouvenirRequest;
import com.android.mobilemarcom.model.souvenir_request.ExampleSouvenirRequest;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCloseActivity extends Activity {
    private Context context= this;
    private TextView app_detail_start_close,app_detail_end_close,app_detail_budget_close,app_detail_notes_close;
    private RecyclerView recycler_requst_approval_close;
    private String request_data,code,event;
    private int id;
    private Toolbar mToolbar;
    private RequestAPIServices requestAPIServices;
    private List<DataListSouvenirRequest> dataListSouvenirRequestList = new ArrayList<>();
    private Button btnApp_close,btnCancel_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_close);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        app_detail_start_close = (TextView) findViewById(R.id.app_detail_start_close);
        app_detail_end_close = (TextView) findViewById(R.id.app_detail_end_close);
        app_detail_budget_close = (TextView) findViewById(R.id.app_detail_budget_close);
        app_detail_notes_close = (TextView) findViewById(R.id.app_detail_notes_close);
        recycler_requst_approval_close = (RecyclerView) findViewById(R.id.recycler_requst_approval_close);
        btnApp_close = (Button) findViewById(R.id.btnApp_close);
        btnCancel_close = (Button) findViewById(R.id.btnCancel_close);

        mToolbar.setTitle("Close Request");
        mToolbar.setNavigationIcon(R.mipmap.icon_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle extras = getIntent().getExtras();
        if(extras==null){
            request_data = null;
            code = null;
            event = null;
            id = 0;
        }else{
            request_data = extras.getString("request_data");
            code = extras.getString("code");
            event = extras.getString("event");
            id =  extras.getInt("id");
        }

        app_detail_start_close.setText(code);
        app_detail_end_close.setText(event);
        app_detail_budget_close.setText(request_data);
        app_detail_notes_close.setText("-");
        tampil_item_souvenir_request(id);
        btnApp_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelGetItemRequest(id);
            }
        });

        btnCancel_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void tampil_item_souvenir_request(int a){
        if(a>0){
            String contentType = "application/json";
            String tokenAuthorization = SessionManager.getToken(context);
            requestAPIServices = APIUtilities.getAPIServices();
            requestAPIServices.souvenirReqGetItemReguest(contentType,tokenAuthorization,a).enqueue(new Callback<ExampleSouvenirRequest>() {
                @Override
                public void onResponse(Call<ExampleSouvenirRequest> call, Response<ExampleSouvenirRequest> response) {
                    if(response.code()==200){
                        if(response.body().getMessage()!=null){
                            if(response.body().getDataList().size()>0){
                                final SouvenirRegGetAdapter adapter=new SouvenirRegGetAdapter(context,response.body().getDataList());
                                recycler_requst_approval_close.setAdapter(adapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                                        LinearLayout.VERTICAL, false);
                                recycler_requst_approval_close.setLayoutManager(layoutManager);
                            }else{
                                Toast.makeText(context, "Hasil pencarian kosong : " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(context, "Data not found : " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Search Item Souvenir Request Gagal : " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ExampleSouvenirRequest> call, Throwable t) {
                    System.out.println("Error "+t.getMessage());
                }
            });
        }
    }

    public void CancelGetItemRequest(int j){
        if(j>0){
            String contentType = "application/json";
            String tokenAuthorization = SessionManager.getToken(context);
            requestAPIServices = APIUtilities.getAPIServices();
            requestAPIServices.souvenirReqGetItemReguestClose(contentType,tokenAuthorization,j).enqueue(new Callback<CloseSouvenirRequest>() {
                @Override
                public void onResponse(Call<CloseSouvenirRequest> call, Response<CloseSouvenirRequest> response) {
                    if(response.code()==200){
                        if(response.body().getMessage()!=null){
                            Toast.makeText(context, response.body().getMessage() , Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(context, "Request Failed : " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(context, "Data not found : " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<CloseSouvenirRequest> call, Throwable t) {
                    System.out.println("Error "+t.getMessage());
                }
            });
        }
    }
}
