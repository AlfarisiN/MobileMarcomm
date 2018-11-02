package com.android.mobilemarcom.viewholder;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestGetItemSouvenirStok;
import com.android.mobilemarcom.souvenirRequest.d.Example;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHolderSouvenirReqAdd2 extends RecyclerView.ViewHolder {
    private TextView t_additem_souvenir, t_additem_quantity, t_additem_del;
    private com.android.mobilemarcom.souvenirRequest.API.RequestAPIServices apiServices;
    private int id1;

    public ViewHolderSouvenirReqAdd2(View itemView, int id1) {
        super(itemView);
        this.id1 = id1;

        t_additem_souvenir = (TextView) itemView.findViewById(R.id.t_additem_souvenir);
        t_additem_quantity = (TextView) itemView.findViewById(R.id.t_additem_quantity);
        t_additem_del = (TextView) itemView.findViewById(R.id.t_additem_del);
    }

    public void setModelAddItemSouvenir(final Context context, final RequestGetItemSouvenirStok souvenirAddItem){
        String souvenir = souvenirAddItem.getSouvenir().getName();
        t_additem_souvenir.setText(souvenir);

        int quantity = souvenirAddItem.getQuantity();
        t_additem_quantity.setText(""+quantity);

        t_additem_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            int id = souvenirAddItem.getId();
            deleteSouvenirStok(context, id);
            }
        });
    }

    public void deleteSouvenirStok(final Context context, int id){
        if (id != 0) {
            String contentType = "application/json";
            String token = SessionManager.getToken(context);
            final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,
                    "Waiting...");
            loading.show();
            apiServices = com.android.mobilemarcom.souvenirRequest.API.APIUtilities.getAPI();
            apiServices.deleteItem(contentType, token, id).enqueue(new Callback<Example>() {
                @Override
                public void onResponse(Call<Example> call, Response<Example> response) {
                    loading.dismiss();
                    if (response.code() == 201) {
                        if (response.body().getMessage() != null) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Example> call, Throwable t) {
                    loading.dismiss();
                    System.out.println("Error " + t.getMessage());
                }
            });
        }
    }
}
