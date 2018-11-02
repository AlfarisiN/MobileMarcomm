package com.android.mobilemarcom.souvenirRequest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.souvenirRequest.API.APIUtilities;
import com.android.mobilemarcom.souvenirRequest.API.DataList;
import com.android.mobilemarcom.souvenirRequest.API.Example;
import com.android.mobilemarcom.souvenirRequest.API.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SouvenirRequestActivity extends AppCompatActivity {
    private Context context = this;
    private RecyclerView recycler_souvenirRequest;
    private SearchView search_souvenirRequest;
    private Button btn_add_souvenirRequest;

    private RequestAPIServices requestAPI;
    private SouvenirReqAdapter souvenirReqAdapter;

    List<String> request = new ArrayList<String>();
    List<DataList> dataLists = new ArrayList<DataList>();
    List<Example> stringList = new ArrayList<>();

//    List<RequestedBy> dataLists = new ArrayList<RequestedBy>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_souvenir_request);

        recycler_souvenirRequest = (RecyclerView) findViewById(R.id.recycler_souvenirRequest);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler_souvenirRequest.setLayoutManager(layoutManager);
        search_souvenirRequest = (SearchView) findViewById(R.id.search_souvenirRequest);
        btn_add_souvenirRequest = (Button) findViewById(R.id.btn_add_souvenirRequest);
        btn_add_souvenirRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddSouvenirReq.class);
                startActivity(intent);
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
                    recycler_souvenirRequest.setVisibility(View.GONE);
                    dataLists.clear();
                } else {
                    dataLists.clear();
                    String contentType = "application/json";
                    final String tokenAuthorization = SessionManager.getToken(context);
                    requestAPI = APIUtilities.getAPI();
                    requestAPI.souvenirReqGet(contentType, tokenAuthorization, newText).enqueue(new Callback<Example>() {
                        @Override
                        public void onResponse(Call<Example> call, Response<Example> response) {
                            if (response.code() == 200) {
                                List<DataList> tmp = response.body().getDataList();
                                for (int i = 0; i < tmp.size(); i++) {
                                    DataList dataListModel = tmp.get(i);
                                    dataLists.add(dataListModel);
                                }
                                final SouvenirReqAdapter adapter = new SouvenirReqAdapter(context, dataLists);
                                recycler_souvenirRequest.setAdapter(adapter);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,
                                        LinearLayout.VERTICAL, false);
                                recycler_souvenirRequest.setLayoutManager(layoutManager);
//                                adapter.getFilter().filter(newText);
                                recycler_souvenirRequest.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(context, "Failure Search Item RequestGetItemSouvenirStokSouvenir : " + response.code(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Example> call, Throwable t) {
                            Toast.makeText(context, "onFailure RequestGetItemSouvenirStokSouvenir : " + t.getMessage(), Toast.LENGTH_SHORT).show();
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
    }
}