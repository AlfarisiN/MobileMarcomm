package com.android.mobilemarcom.event;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.modelevent.DataList;
import com.android.mobilemarcom.model.modelevent.ModelEventRetrofit;
import com.android.mobilemarcom.model.user.DataListAutoCompleteEmploye;
import com.android.mobilemarcom.model.user.ExampleAutoCompleteEmploye;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventApprovalActivity extends AppCompatActivity {

    final Context context = this;
    TextView textCode,textName,textPlace,textStartDate,textEndDate,textBudget,textNotes,textRequestedBy,textRequestDate,textStatus;
    AutoCompleteTextView inputAssignTo;
    Button buttonApprove,buttonReject,buttonCancel;
    Toolbar toolbar;

    RequestAPIServices apiServices;
    List<DataListAutoCompleteEmploye> listAssign = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    DataList eventImported = new DataList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_approval);

        textCode = (TextView)findViewById(R.id.eventApprovalTextCode);
        textName = (TextView)findViewById(R.id.eventApprovalTextName);
        textPlace = (TextView)findViewById(R.id.eventApprovalTextPlace);
        textStartDate = (TextView)findViewById(R.id.eventApprovalTextStartDate);
        textEndDate = (TextView)findViewById(R.id.eventApprovalTextEndDate);
        textBudget = (TextView)findViewById(R.id.eventApprovalTextBudget);
        textNotes = (TextView)findViewById(R.id.eventApprovalTextNotes);
        textRequestedBy = (TextView)findViewById(R.id.eventApprovalTextRequestedBy);
        textRequestDate = (TextView)findViewById(R.id.eventApprovalTextRequestDate);
        textStatus = (TextView)findViewById(R.id.eventApprovalTextStatus);
        inputAssignTo = (AutoCompleteTextView) findViewById(R.id.eventApprovalAssignTo);
        buttonApprove = (Button)findViewById(R.id.eventApprovalButtonApprove);
        buttonReject = (Button)findViewById(R.id.eventApprovalButtonReject);
        buttonCancel = (Button)findViewById(R.id.eventApprovalButtonCancel);
        toolbar = (Toolbar)findViewById(R.id.eventApprovalToolBar);

        setDisplayApproval();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Event Approval");

        pushedButton();

        inputAssignTo.setThreshold(1);
        inputAssignTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                arrayAdapter = getAutofillAdapter(context,s.toString());

                inputAssignTo.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    private void setDisplayApproval(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        eventImported.setId(Integer.parseInt(bundle.get("dataID").toString()));
        eventImported.setCode(bundle.get("dataCode").toString());
        eventImported.setEventName(bundle.get("dataName").toString());
        eventImported.setPlace(bundle.get("dataPlace").toString());
        eventImported.setStartDate(bundle.get("dataStartDate").toString());
        eventImported.setEndDate(bundle.get("dataEndDate").toString());
        eventImported.setBudget(bundle.get("dataBudget").toString());
        eventImported.setNotes(bundle.get("dataNotes").toString());
        eventImported.setRequestedBy(bundle.get("dataRequestedBy").toString());
        eventImported.setRequestedDate(bundle.get("dataRequestDate").toString());
        eventImported.setStatus(bundle.get("dataStatus").toString());

        textCode.setText(": "+bundle.get("dataCode"));
        textName.setText(": "+bundle.get("dataName"));
        textPlace.setText(": "+bundle.get("dataPlace"));
        textStartDate.setText(": "+bundle.get("dataStartDate"));
        textEndDate.setText(": "+bundle.get("dataEndDate"));
        textBudget.setText(": "+bundle.get("dataBudget"));
        textNotes.setText(": "+bundle.get("dataNotes"));
        textRequestedBy.setText(": "+bundle.get("dataRequestedBy"));
        textRequestDate.setText(": "+bundle.get("dataRequestDate"));
        textStatus.setText(": "+bundle.get("dataStatus"));
    }

    private ArrayAdapter<String> getAutofillAdapter(Context context,String path) {

        eventAutoComplete(path);

        String[] addresses = new String[listAssign.size()];
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = listAssign.get(i).getName();
        }

        return new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, addresses);
    }

    public void eventAutoComplete(String path){
        apiServices = APIUtilities.getAPIServices();


//        String dataJson = APIUtilities.addEvents(tempData.getEventName(),tempData.getStartDate().toString(),
//                tempData.getEndDate().toString(),tempData.getPlace().toString(),
//                Integer.parseInt(tempData.getBudget().toString()),tempData.getNotes().toString());
        final String  tokenAuthorization = SessionManager.getToken(context);
//        final RequestBody bodyRequest = RequestBody.create(APIUtilities.mediaType(), dataJson);

        if(inputAssignTo.getText() != null){
            apiServices.employeautocompletee(APIUtilities.CONTENT_HEADER,tokenAuthorization,path)
                    .enqueue(new Callback<ExampleAutoCompleteEmploye>() {
                        @Override
                        public void onResponse(Call<ExampleAutoCompleteEmploye> call, Response<ExampleAutoCompleteEmploye> response) {
                            List<DataList> tempUnitList;

                            if(listAssign.size() == 0) {
                                if(response.code()==200){
                                    listAssign = response.body().getDataList();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ExampleAutoCompleteEmploye> call, Throwable t) {

                        }
                    });
        }


    }

    public void eventRejection(){
        LinearLayout linearLayout = new LinearLayout(context);
        final EditText rejectReason = new EditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(10,10,10,10);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        rejectReason.setHorizontallyScrolling(false);
        rejectReason.setMinHeight(3);
        rejectReason.setHint("Notes");

        linearLayout.setLayoutParams(params);
        linearLayout.addView(rejectReason);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle("Reject Reason")
                .setView(linearLayout)
                .setPositiveButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog holdDialog = builder.create();
        holdDialog.show();
        holdDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rejectReason.getText().toString().isEmpty()){
                    Toast.makeText(context,"Notes tidak Boleh Kosong",Toast.LENGTH_LONG).show();
                }
                else {
                    rejectMethod(eventImported.getId().toString(),rejectReason.getText().toString());
                    Toast.makeText(context,"Data successfully updated",Toast.LENGTH_SHORT).show();
                    holdDialog.dismiss();
                }
            }
        });

    }

    public void rejectMethod(String id,String reason){
        final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,"");
        loading.show();
        apiServices = APIUtilities.getAPIServices();

        apiServices.rejectEvent(APIUtilities.CONTENT_HEADER,APIUtilities.AUTHORIZATION_UNIT_SEARCH,id,reason)
                .enqueue(new Callback<ModelEventRetrofit>() {
                    @Override
                    public void onResponse(Call<ModelEventRetrofit> call, Response<ModelEventRetrofit> response) {
                        loading.dismiss();
                        if(response.code() == 200){
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelEventRetrofit> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(context,"Reject Event Failed",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void approveMethod(){
        final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,"");
        loading.show();

        String selectedIDAssign = eventAssignIDCheck(inputAssignTo.getText().toString());
        String idEventApproval = eventImported.getId().toString();
        apiServices = APIUtilities.getAPIServices();

        apiServices.approvalEvent(APIUtilities.CONTENT_HEADER,APIUtilities.AUTHORIZATION_UNIT_SEARCH,idEventApproval,selectedIDAssign)
                .enqueue(new Callback<ModelEventRetrofit>() {
                    @Override
                    public void onResponse(Call<ModelEventRetrofit> call, Response<ModelEventRetrofit> response) {
                        loading.dismiss();
                        if(response.code() == 200){
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else{
                            Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelEventRetrofit> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(context,"Approval Failed",Toast.LENGTH_LONG).show();
                    }
                });

    }

    private String eventAssignIDCheck(String text){
        String temp="";

        for (int i = 0; i<listAssign.size();i++){
            if(listAssign.get(i).getName().equals(text)){
                temp=listAssign.get(i).getId().toString();
            }
        }

        return temp;
    }

    private void pushedButton(){

            buttonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            buttonApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(inputAssignTo.getText().toString().trim().isEmpty()){
                        Toast.makeText(context,"Isikan Form isian terlebih dahulu",Toast.LENGTH_LONG).show();
                    }
                    else{
                        approveMethod();
                    }
                }
            });

            buttonReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    eventRejection();
                }
            });

    }
}
