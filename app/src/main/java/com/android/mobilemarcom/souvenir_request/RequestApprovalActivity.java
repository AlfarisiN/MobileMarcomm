package com.android.mobilemarcom.souvenir_request;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.android.mobilemarcom.adapters.SouvenirReqAdapter;
import com.android.mobilemarcom.model.souvenir_request.ApprovalSouvenirRequest;
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

public class RequestApprovalActivity extends Activity {
    private Context context= this;
    private TextView app_detail_start,app_detail_end,app_detail_budget,app_detail_notes;
    private RecyclerView recycler_requst_approval;
    private String request_data,code,event;
    private int id;
    private Toolbar mToolbar;
    private RequestAPIServices requestAPIServices;
    private List<DataListSouvenirRequest> dataListSouvenirRequestList = new ArrayList<>();
    private Button btnApp,btnReject,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_approval);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        app_detail_start = (TextView) findViewById(R.id.app_detail_start);
        app_detail_end = (TextView) findViewById(R.id.app_detail_end);
        app_detail_budget = (TextView) findViewById(R.id.app_detail_budget);
        app_detail_notes = (TextView) findViewById(R.id.app_detail_notes);
        recycler_requst_approval = (RecyclerView) findViewById(R.id.recycler_requst_approval);
        btnApp = (Button) findViewById(R.id.btnApp);
        btnReject = (Button) findViewById(R.id.btnReject);
        btnCancel = (Button) findViewById(R.id.btnCancel);

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
        mToolbar.setTitle("Request Approval");
        mToolbar.setNavigationIcon(R.mipmap.icon_back_white);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        app_detail_start.setText(code);
        app_detail_end.setText(event);
        app_detail_budget.setText(request_data);
        app_detail_notes.setText("-");
        tampil_item_souvenir_request(id);

        btnApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApproveGetItemRequest(id);
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,RejectRequestActivity.class);
                intent.putExtra("id_request",id);
                context.startActivity(intent);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
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
                                recycler_requst_approval.setAdapter(adapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                                        LinearLayout.VERTICAL, false);
                                recycler_requst_approval.setLayoutManager(layoutManager);
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

    public void ApproveGetItemRequest(int j){
        if(j>0){
            String contentType = "application/json";
            String tokenAuthorization = SessionManager.getToken(context);
            requestAPIServices = APIUtilities.getAPIServices();
            requestAPIServices.souvenirReqGetItemReguestApprove(contentType,tokenAuthorization,j).enqueue(new Callback<ApprovalSouvenirRequest>() {
                @Override
                public void onResponse(Call<ApprovalSouvenirRequest> call, Response<ApprovalSouvenirRequest> response) {
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
                public void onFailure(Call<ApprovalSouvenirRequest> call, Throwable t) {
                    System.out.println("Error "+t.getMessage());
                }
            });
        }
    }

}
