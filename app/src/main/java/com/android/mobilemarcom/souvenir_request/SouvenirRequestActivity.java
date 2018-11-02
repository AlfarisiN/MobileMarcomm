package com.android.mobilemarcom.souvenir_request;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.SouvenirReqAdapter;
import com.android.mobilemarcom.model.souvenir_request.DataList;
import com.android.mobilemarcom.model.souvenir_request.Example;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SouvenirRequestActivity extends Fragment {
    private RecyclerView recycler_souvenirRequest;
    private SearchView search_souvenirRequest;
    private Button btn_add_souvenirRequest;

    private RequestAPIServices requestAPI;

    List<DataList> dataLists = new ArrayList<DataList>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_souvenir_request,container,false);
        recycler_souvenirRequest = (RecyclerView) view.findViewById(R.id.recycler_souvenirRequest);
        search_souvenirRequest = (SearchView) view.findViewById(R.id.search_souvenirRequest);
        btn_add_souvenirRequest = (Button) view.findViewById(R.id.btn_add_souvenirRequest);

        btn_add_souvenirRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), AddSouvenirReq.class);
//                startActivity(intent);
            }
        });


        search_souvenirRequest.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (search_souvenirRequest.getQuery().length() == 0) {
//                    rowlay.setVisibility(View.GONE);
                    recycler_souvenirRequest.setVisibility(View.GONE);
                } else {
                    String contentType = "application/json";
                    String tokenAuthorization = SessionManager.getToken(getContext());

                    requestAPI = APIUtilities.getAPIServices();
                    requestAPI.souvenirReqGet(contentType,tokenAuthorization,newText).enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {
                            if (response.code() == 200) {
                                if (response.body().getDataList().size() > 0) {
                                    final SouvenirReqAdapter adapter=new SouvenirReqAdapter(getContext(),response.body().getDataList());
                                    recycler_souvenirRequest.setAdapter(adapter);
                                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                                            LinearLayout.VERTICAL, false);
                                    recycler_souvenirRequest.setLayoutManager(layoutManager);
                                    adapter.getFilter().filter(newText);
                                    recycler_souvenirRequest.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(getContext(), "Hasil pencarian kosong : " + response.code(), Toast.LENGTH_SHORT).show();
                                    recycler_souvenirRequest.setVisibility(View.GONE);
                                }
                            } else {
                                Toast.makeText(getContext(), "Search Souvenir Request Gagal : " + response.code(), Toast.LENGTH_SHORT).show();
                                recycler_souvenirRequest.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                            System.out.println("Error "+t.getMessage());
//                            Toast.makeText(getContext(), "Error : " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
