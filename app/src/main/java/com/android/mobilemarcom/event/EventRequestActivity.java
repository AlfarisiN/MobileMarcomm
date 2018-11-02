package com.android.mobilemarcom.event;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.modelevent.DataList;
import com.android.mobilemarcom.model.modelevent.ModelEventRetrofit;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventRequestActivity extends AppCompatActivity{

    Context context = this;
    EditText eventInputName,eventInputPlace,eventInputStartDate,eventInputEndDate,eventInputNotes,eventInputBudget;
    Button eventButtonSave,eventButtonCancel;
    Toolbar eventToolbar;

    boolean isEdit=false;

    RequestAPIServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_request);

        eventButtonCancel = (Button)findViewById(R.id.eventRequestButtonCancel);
        eventButtonSave = (Button)findViewById(R.id.eventRequestButtonSave);

        eventInputName = (EditText)findViewById(R.id.inputEventName);
        eventInputPlace = (EditText)findViewById(R.id.inputEventPlace);
        eventInputStartDate = (EditText)findViewById(R.id.inputEventStartDate);
        eventInputEndDate = (EditText)findViewById(R.id.inputEventEndDate);
        eventInputBudget = (EditText)findViewById(R.id.inputEventBudget);
        eventInputNotes = (EditText)findViewById(R.id.inputEventNote);
        eventToolbar = (Toolbar)findViewById(R.id.eventRequestToolBar);

        datePickerStartEnd();
        buttonSelected();

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("edited",false);

        if(isEdit){
            Bundle bundle = intent.getExtras();

            eventInputName.setText(bundle.get("dataName").toString());
            eventInputPlace.setText(bundle.get("dataPlace").toString());
            eventInputStartDate.setText(bundle.get("dataStartDate").toString());
            eventInputEndDate.setText(bundle.get("dataEndDate").toString());
            eventInputBudget.setText(bundle.get("dataBudget").toString());
            eventInputNotes.setText(bundle.get("dataNotes").toString());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }

    private void datePickerStartEnd(){
        final Calendar today = Calendar.getInstance();
        final int yearStart = today.get(Calendar.YEAR);
        final int monthStart = today.get(Calendar.MONTH);
        final int dateStart = today.get(Calendar.DATE);

        eventInputEndDate.setFocusable(false);
        eventInputEndDate.setClickable(true);

        eventInputStartDate.setFocusable(false);
        eventInputStartDate.setClickable(true);

        eventInputStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(context, R.style.DatePickerBirthdate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar tempSelected = Calendar.getInstance();
                        tempSelected.set(year,month,dayOfMonth);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String date = simpleDateFormat.format(tempSelected.getTime());
                        eventInputStartDate.setText(date.toString());

                    }
                },yearStart,monthStart,dateStart);
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);
                datePicker.show();
            }
        });

        eventInputEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePicker = new DatePickerDialog(context, R.style.DatePickerBirthdate, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar tempSelected = Calendar.getInstance();
                        tempSelected.set(year,month,dayOfMonth);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        String date = simpleDateFormat.format(tempSelected.getTime());
                        eventInputEndDate.setText(date+"");

                    }
                },yearStart,monthStart,dateStart);
                datePicker.getDatePicker().setSpinnersShown(true);
                datePicker.getDatePicker().setCalendarViewShown(false);
                datePicker.show();
            }
        });
    }

    private void buttonSelected(){
        eventButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(eventInputName.getText().toString()) ||
                        TextUtils.isEmpty(eventInputPlace.getText().toString()) ||
                        TextUtils.isEmpty(eventInputStartDate.getText().toString()) ||
                        TextUtils.isEmpty(eventInputEndDate.getText().toString()) ||
                        TextUtils.isEmpty(eventInputBudget.getText().toString()) ||
                        TextUtils.isEmpty(eventInputNotes.getText().toString())){

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Peringatan !!!")
                            .setMessage("Form tidak boleh ada yang kosong !!")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
                else{
                    if(isEdit){
                        editEventMethod();
                    }
                    else {
                        eventSaveMethod();
                    }

                    finish();
                }
            }
        });

        eventButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void eventSaveMethod(){
        isEdit=false;
        apiServices= APIUtilities.getAPIServices();

        final DataList tempData = new DataList();
        Random random = new Random();
        int tempRandomCode = random.nextInt(999)+6;
        int tempRandomID = random.nextInt(20)+6;

        tempData.setCode("EV"+tempRandomCode);
        tempData.setId(tempRandomID);
        tempData.setEventName(eventInputName.getText().toString());
        tempData.setPlace(eventInputPlace.getText().toString());
        tempData.setStartDate(eventInputStartDate.getText().toString());
        tempData.setEndDate(eventInputEndDate.getText().toString());
        tempData.setBudget(eventInputBudget.getText().toString());
        tempData.setNotes(eventInputNotes.getText().toString());

        String dataJson = APIUtilities.addEvents(tempData.getEventName(),tempData.getStartDate().toString(),
                tempData.getEndDate().toString(),tempData.getPlace().toString(),
                Integer.parseInt(tempData.getBudget().toString()),tempData.getNotes().toString());
        final String  tokenAuthorization = SessionManager.getToken(context);
        final RequestBody bodyRequest = RequestBody.create(APIUtilities.mediaType(), dataJson);

        apiServices.createEvent(APIUtilities.CONTENT_HEADER,tokenAuthorization,bodyRequest)
                .enqueue(new Callback<ModelEventRetrofit>() {
                    @Override
                    public void onResponse(Call<ModelEventRetrofit> call, Response<ModelEventRetrofit> response) {
                        if(response.code()==201){
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
//                            EventFragment.listEvent.add(tempData);
                        }
                        else{
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelEventRetrofit> call, Throwable t) {
                        Toast.makeText(context,"Request add Failure",Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void editEventMethod(){
        isEdit=true;
        apiServices= APIUtilities.getAPIServices();

        final DataList tempData = new DataList();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        tempData.setId(Integer.parseInt(bundle.get("dataID").toString()));
        tempData.setEventName(eventInputName.getText().toString());
        tempData.setPlace(eventInputPlace.getText().toString());
        tempData.setStartDate(eventInputStartDate.getText().toString());
        tempData.setEndDate(eventInputEndDate.getText().toString());
        tempData.setBudget(eventInputBudget.getText().toString());
        tempData.setNotes(eventInputNotes.getText().toString());

        String dataJson = APIUtilities.editEvents(tempData.getId(),tempData.getEventName(),tempData.getStartDate().toString(),
                tempData.getEndDate().toString(),tempData.getPlace().toString(),
                Integer.parseInt(tempData.getBudget().toString()),tempData.getNotes().toString());
        final String  tokenAuthorization = SessionManager.getToken(context);
        final RequestBody bodyRequest = RequestBody.create(APIUtilities.mediaType(), dataJson);

        apiServices.editEvent(APIUtilities.CONTENT_HEADER,tokenAuthorization,bodyRequest)
                .enqueue(new Callback<ModelEventRetrofit>() {
                    @Override
                    public void onResponse(Call<ModelEventRetrofit> call, Response<ModelEventRetrofit> response) {
                        if(response.code()==200){
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ModelEventRetrofit> call, Throwable t) {
                        Toast.makeText(context,"Request Edit Failure",Toast.LENGTH_LONG).show();
                    }
                });

    }
}

