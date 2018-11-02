package com.android.mobilemarcom.souvenir;

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
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.adapters.SouvenirAdapter;
import com.android.mobilemarcom.model.ModelSouvenir.DataListModel;
import com.android.mobilemarcom.model.ModelSouvenir.DataListSouvenir;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SouvenirFragment extends Fragment {
    private RecyclerView recyclerList;
    private List<DataListModel> dataListModels = new ArrayList<>();
    private RequestAPIServices apiServices;
    private Button btnAddSouvenir;
    private SearchView searchSouvenir;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_souvenir,container,false);
        recyclerList = (RecyclerView) view.findViewById(R.id.recycler_souvenir);
        searchSouvenir = (SearchView) view.findViewById(R.id.search_souvenir);
        recyclerList.setVisibility(View.VISIBLE);

        searchSouvenir.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (searchSouvenir.getQuery().length() == 0){
                    recyclerList.setVisibility(View.GONE);
                    dataListModels.clear();
                } else {
                    dataListModels.clear();
                    String contentType = "application/json";
                    final String  tokenAuthorization = SessionManager.getToken(getContext());
                    apiServices = APIUtilities.getAPIServices();
                    apiServices.searchSouvenirName(contentType, tokenAuthorization, newText).enqueue(new Callback<DataListSouvenir>() {
                        @Override
                        public void onResponse(Call<DataListSouvenir> call, Response<DataListSouvenir> response) {
                            if (response.isSuccessful() && response.code() == 200){
                                final SouvenirAdapter adapter = new SouvenirAdapter(getContext(), response.body().getDataList());
                                recyclerList.setAdapter(adapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                                        LinearLayout.VERTICAL, false);
                                recyclerList.setLayoutManager(layoutManager);
                                adapter.getFilter().filter(newText);
                                recyclerList.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(getContext(), "onResponse Error : "+response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DataListSouvenir> call, Throwable t) {
                            Toast.makeText(getContext(), "onFailure Response Error : "+t.getMessage() , Toast.LENGTH_SHORT).show();
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


        btnAddSouvenir = (Button)view.findViewById(R.id.btn_add_souvenir);
        btnAddSouvenir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SouvenirDaftarActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
}