package com.android.mobilemarcom.t_souvenir;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.android.mobilemarcom.adapters.SouvenirStokAdapter;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestSouvenirStockAddItem;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseSouvenirStockAddItem;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SouvenirStokFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerTSouvenirStok;
    private SearchView searchTSouvenir;
    private Button btnTSouvenir;
    private List<RequestSouvenirStockAddItem> listSouvenirStok = new ArrayList<>();
    private RequestAPIServices apiServices;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_t_souvenir_stok
                ,container,false);
        recyclerTSouvenirStok = (RecyclerView) view.findViewById(R.id.recycler_t_souvenir_stok);
        searchTSouvenir = (SearchView) view.findViewById(R.id.search_t_souvenir);
        recyclerTSouvenirStok.setVisibility(View.INVISIBLE);
        searchTSouvenir.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                if (searchTSouvenir.getQuery().length() == 0 ){
                    recyclerTSouvenirStok.setVisibility(View.GONE);
                    listSouvenirStok.clear();
                } else {
                    listSouvenirStok.clear();
                    String contentType = "application/json";
                    String token = SessionManager.getToken(getContext());
                    apiServices = APIUtilities.getAPIServices();
                    apiServices.getSouvenirStock(contentType, token, newText).enqueue(new Callback<ResponseSouvenirStockAddItem>() {
                        @Override
                        public void onResponse(Call<ResponseSouvenirStockAddItem> call, Response<ResponseSouvenirStockAddItem> response) {
                            try {
                                if (response.code() == 200){
                                    final SouvenirStokAdapter adapter = new SouvenirStokAdapter(getContext(), response.body().getDataList());
                                    recyclerTSouvenirStok.setAdapter(adapter);
                                    RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(),
                                            LinearLayout.VERTICAL,false);

                                    recyclerTSouvenirStok.setLayoutManager(layoutManager1);
                                    adapter.getFilter().filter(newText);
                                    recyclerTSouvenirStok.setVisibility(View.VISIBLE);
                                } else {
                                    Toast.makeText(getContext(), "onResponse : "+response.code(), Toast.LENGTH_SHORT).show();
                                }
                            } catch (Exception e){
                                Toast.makeText(getContext(), "onResponse Error Exeception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseSouvenirStockAddItem> call, Throwable t) {
                            Toast.makeText(getContext(), "onFailure : "+t.getMessage() , Toast.LENGTH_SHORT).show();
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

        btnTSouvenir = (Button) view.findViewById(R.id.btn_t_souvenir);
        btnTSouvenir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SouvenirStokActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}