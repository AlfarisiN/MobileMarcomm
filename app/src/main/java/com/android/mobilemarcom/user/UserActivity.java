package com.android.mobilemarcom.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableRow;


import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.UsersAdapter;
import com.android.mobilemarcom.model.user.DataList;
import com.android.mobilemarcom.model.user.Example;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends Fragment {
    private RecyclerView recyclerUser;
    private SearchView nameSearchUser;
    private TableRow rowlay;
    private Button buttonAdd;
    private RequestAPIServices requestAPIServices;
    private List<DataList> dataLists = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_user,container,false);
        recyclerUser = (RecyclerView) view.findViewById(R.id.recyclerUser);
        nameSearchUser = (SearchView) view.findViewById(R.id.nameSearchUser);
        rowlay = (TableRow) view.findViewById(R.id.rowlay);
        buttonAdd = (Button) view.findViewById(R.id.buttonAdd);


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getContext(),TambahUserActivity.class);
                getContext().startActivity(in);
            }
        });


        nameSearchUser.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if(nameSearchUser.getQuery().length()==0){
                    recyclerUser.setVisibility(View.GONE);
                    dataLists.clear();
                }else{
                    dataLists.clear();
                    String contentType = "application/json";
                    String token = SessionManager.getToken(getContext());
                    final ProgressDialog loading = LoadingClass.loadingAnimationAndText(getContext(),"");
                    loading.show();
                    requestAPIServices = APIUtilities.getAPIServices();
                    requestAPIServices.getDataListUser(contentType,token,newText).enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(retrofit2.Call<Example> call, Response<Example> response) {
                            loading.dismiss();
                            if(response.code()==200){
                                List<DataList> tmp = response.body().getDataList();
                                for(int i=0;i<tmp.size();i++){
                                    DataList dataList = tmp.get(i);
                                    dataLists.add(dataList);
                                }

                                final UsersAdapter adapter=new UsersAdapter(getContext(),dataLists);
                                recyclerUser.setAdapter(adapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                                        LinearLayout.VERTICAL, false);
                                recyclerUser.setLayoutManager(layoutManager);
                                adapter.getFilter().filter(newText);
                                rowlay.setVisibility(View.VISIBLE);
                                recyclerUser.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(retrofit2.Call<Example> call, Throwable t) {
                            loading.dismiss();
                            System.out.println("Gagal mengambil");
                        }
                    });
                }
                return false;
            }
            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }
        });
        return view;
    }

}
