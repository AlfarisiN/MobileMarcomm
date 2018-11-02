package com.android.mobilemarcom.event;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.EventAdapter;
import com.android.mobilemarcom.adapters.UnitAdapter;
import com.android.mobilemarcom.event.modelevent.EventModul;
import com.android.mobilemarcom.model.modelevent.ModelEventRetrofit;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.unit.UnitFragment;
import com.android.mobilemarcom.model.modelevent.DataList;
import com.android.mobilemarcom.unit.modelunit.ModelUnitRetrofit;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Irfan Naufal Ridi on 24/10/2018.
 */

public class EventFragment extends Fragment {

    private RecyclerView recyclerListEvent;
    private EventAdapter adapterEvent;
    public static List<DataList> listEvent = new ArrayList<>();

    private EditText searchEvent;
    private Button buttonEventAdd;
    Context context;

    RequestAPIServices apiServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event, container, false);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        context = view.getContext();
        searchEvent = (EditText)view.findViewById(R.id.eventSearch);
        buttonEventAdd = (Button)view.findViewById(R.id.buttonAddEvent);
        recyclerListEvent = (RecyclerView)view.findViewById(R.id.recyclerEvent);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerListEvent.setLayoutManager(layoutManager);

        buttonEventAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventRequestActivity.class);
                context.startActivity(intent);
            }
        });

//        recyclerListEvent.setVisibility(View.INVISIBLE);

        searchEvent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(searchEvent.getText().toString().isEmpty()){
                    recyclerListEvent.setVisibility(View.INVISIBLE);
                }
                else{
                    recyclerListEvent.setVisibility(View.VISIBLE);
//                    if(listEvent.size()==0){
                        searchForEvent();
//                    }
//                    else{
//                        filterEvent(searchEvent.getText().toString());
//                    }
                }
            }
        });

        //untuk ngeklik drawable right nya
        searchEvent.setOnTouchListener(new View.OnTouchListener() {
            private float touchX = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int drawableLeft = searchEvent.getRight() - searchEvent
                        .getCompoundDrawables()[2].getBounds().width();
                if(event.getAction() == MotionEvent.ACTION_DOWN && event.getRawX() >= drawableLeft){
                    touchX = event.getRawX();
                    return true;
                }
                else if(event.getAction() == MotionEvent.ACTION_UP && touchX >= drawableLeft){

//                    if(searchEvent.getText().toString().trim().isEmpty()){
//                        Toast.makeText(context,"Isikan Form isian terlebih dahulu",Toast.LENGTH_LONG).show();
//                    }
//                    else{
//                        recyclerListEvent.setVisibility(View.VISIBLE);
//                        if(listEvent.size()==0){
//                            searchForEvent();
//                        }
//                        else{
//                            filterEvent(searchEvent.getText().toString());
//                        }
//                    }

                    touchX = 0;
                    return true;
                }
                else {
                    return searchEvent.onTouchEvent(event);
                }
            }
        });

        return view;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            getFragmentManager().beginTransaction().remove(EventFragment.this).commitAllowingStateLoss();
        }
    }

    @Override
    public void onResume() {
        recyclerListEvent.setVisibility(View.INVISIBLE);
        searchEvent.setText("");
        super.onResume();
    }

    public void filterEvent(String text){
        ArrayList<DataList> filterList = new ArrayList<>();

        for(DataList temp : listEvent){
            if(temp.getCode().toString().toLowerCase().contains(text.toLowerCase())){
                filterList.add(temp);
            }
        }

        adapterEvent.filterList(filterList);

    }

    private void searchForEvent(){
        final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,"");
        loading.show();

        apiServices = APIUtilities.getAPIServices();
        final String  tokenAuthorization = SessionManager.getToken(context);

        apiServices.searchEvent(/*APIUtilities.CONTENT_HEADER,*/tokenAuthorization,searchEvent.getText().toString())
                .enqueue(new Callback<ModelEventRetrofit>() {

                    @Override
                    public void onResponse(Call<ModelEventRetrofit> call, Response<ModelEventRetrofit> response) {
                        loading.dismiss();

                        if(response.code()==200){
                            listEvent = response.body().getDataList();
                            adapterEvent = new EventAdapter(context,listEvent);
                            filterEvent(searchEvent.getText().toString());
                            recyclerListEvent.setAdapter(adapterEvent);
                        }
                        else {
                            Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                        }
                        adapterEvent.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ModelEventRetrofit> call, Throwable t) {
                        loading.dismiss();
                        Toast.makeText(context,"Search Event Failure",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
