package com.android.mobilemarcom.souvenir_request;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.souvenir_request.ApprovalSouvenirRequest;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RejectRequestActivity extends Activity {
    private Context context = this;
    private Toolbar toolbar;
    private EditText notes_reject;
    private Button btnReject_reject,btnCancel_reject;
    private int id;
    private RequestAPIServices requestAPIServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_request);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        notes_reject = (EditText) findViewById(R.id.notes_reject);
        btnReject_reject = (Button) findViewById(R.id.btnReject_reject);
        btnCancel_reject = (Button) findViewById(R.id.btnCancel_reject);

        toolbar.setTitle("REJECT REASON");
        toolbar.setNavigationIcon(R.mipmap.icon_back_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Bundle extras = getIntent().getExtras();
        if(extras==null){
            id = 0;
        }else{
            id =  extras.getInt("id_request");
        }
        btnReject_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi(id,notes_reject.getText().toString());
            }
        });
        btnCancel_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void validasi(int x,String a){
        if(a.equals("")){
            Toast.makeText(context,"Notes tidak boleh kososng",Toast.LENGTH_SHORT).show();
        }else{
            addReject(x,a);
        }
    }

    private void addReject(int y,String b){
        String contentType = "application/json";
        String tokenAuthorization = SessionManager.getToken(context);
        requestAPIServices = APIUtilities.getAPIServices();
        requestAPIServices.souvenirReqGetItemReguestReject(contentType,tokenAuthorization,y,b).enqueue(new Callback<ApprovalSouvenirRequest>() {
            @Override
            public void onResponse(Call<ApprovalSouvenirRequest> call, Response<ApprovalSouvenirRequest> response) {
                if(response.code()==200){
                    if(response.body().getMessage()!=null){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(context, "Data not found : " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "Search Reject Item Souvenir Request Gagal : " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApprovalSouvenirRequest> call, Throwable t) {
                System.out.println("Error "+t.getMessage());
            }
        });
    }
}
