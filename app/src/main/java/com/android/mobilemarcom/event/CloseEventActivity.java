package com.android.mobilemarcom.event;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.modelevent.DataList;
import com.android.mobilemarcom.model.modelevent.ModelEventRetrofit;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.LoadingClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CloseEventActivity extends AppCompatActivity {

    final Context context = this;
    TextView textCode,textName,textPlace,textStartDate,textEndDate,textBudget,textNotes,textRequestedBy,textRequestDate,textStatus,textAssignTo;
    Button buttonClose,buttonCancel;
    Toolbar toolbar;

    DataList dataList;

    RequestAPIServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_event);

        dataList = new DataList();
        apiServices = APIUtilities.getAPIServices();
        textCode = (TextView)findViewById(R.id.eventCloseTextCode);
        textName = (TextView)findViewById(R.id.eventCloseTextName);
        textPlace = (TextView)findViewById(R.id.eventCloseTextPlace);
        textStartDate = (TextView)findViewById(R.id.eventCloseTextStartDate);
        textEndDate = (TextView)findViewById(R.id.eventCloseTextEndDate);
        textBudget = (TextView)findViewById(R.id.eventCloseTextBudget);
        textNotes = (TextView)findViewById(R.id.eventCloseTextNotes);
        textRequestedBy = (TextView)findViewById(R.id.eventCloseTextRequestedBy);
        textRequestDate = (TextView)findViewById(R.id.eventCloseTextRequestDate);
        textStatus = (TextView)findViewById(R.id.eventCloseTextStatus);
        textAssignTo = (TextView)findViewById(R.id.eventCloseTextAssignTo);
        buttonCancel = (Button)findViewById(R.id.eventCloseButtonCancel);
        buttonClose = (Button)findViewById(R.id.eventCloseButtonClose);
        toolbar = (Toolbar)findViewById(R.id.eventCloseToolBar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        dataList.setId(Integer.parseInt(bundle.get("dataID").toString()));
        dataList.setCode(bundle.get("dataCode").toString());
        dataList.setEventName(bundle.get("dataName").toString());
        dataList.setPlace(bundle.get("dataPlace").toString());
        dataList.setStartDate(bundle.get("dataStartDate").toString());
        dataList.setEndDate(bundle.get("dataEndDate").toString());
        dataList.setBudget(bundle.get("dataBudget").toString());
        dataList.setNotes(bundle.get("dataNotes").toString());
        dataList.setAssignTo(bundle.get("dataAssign").toString());
        dataList.setRequestedBy(bundle.get("dataRequestedBy"));
        dataList.setRequestedDate(bundle.get("dataRequestDate"));
        dataList.setStatus(bundle.get("dataStatus"));

        dataDisplay();
        pushedButton();

        getSupportActionBar().setTitle("Close Event");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    public void dataDisplay(){
        textName.setText(": "+dataList.getEventName());
        textCode.setText(": "+dataList.getCode());
        textPlace.setText(": "+dataList.getPlace().toString());
        textStartDate.setText(": "+dataList.getStartDate());
        textEndDate.setText(": "+dataList.getEndDate().toString());
        textBudget.setText(": "+dataList.getBudget().toString());
        textNotes.setText(": "+dataList.getNotes().toString());
        textAssignTo.setText(": "+dataList.getAssignTo().toString());
        textRequestedBy.setText(": "+dataList.getRequestedBy().toString());
        textRequestDate.setText(": "+dataList.getRequestedDate().toString());
        textStatus.setText(": "+dataList.getStatus().toString());
    }

    public void pushedButton(){
        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeEvents();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void closeEvents(){
        final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,"");
        loading.show();

        apiServices.closeEvent(APIUtilities.CONTENT_HEADER,APIUtilities.AUTHORIZATION_UNIT_SEARCH,dataList.getId().toString())
                .enqueue(new Callback<ModelEventRetrofit>() {
                    @Override
                    public void onResponse(Call<ModelEventRetrofit> call, Response<ModelEventRetrofit> response) {
                        loading.dismiss();
                        Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ModelEventRetrofit> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(context,"Close Event Failed",Toast.LENGTH_LONG).show();
                    }
                });
    }
}
