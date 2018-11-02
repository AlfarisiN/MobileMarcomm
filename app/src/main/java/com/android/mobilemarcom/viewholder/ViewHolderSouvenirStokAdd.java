package com.android.mobilemarcom.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.ModelSouvenirStok.RequestGetItemSouvenirStok;
import com.android.mobilemarcom.model.ModelSouvenirStok.ResponseGetItemSouvenirStok;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHolderSouvenirStokAdd extends RecyclerView.ViewHolder {
    private TextView t_additem_souvenir, t_additem_quantity, t_additem_del;
    private RequestAPIServices apiServices;
    private int id1;

    public ViewHolderSouvenirStokAdd(View itemView, int id1) {
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
            deleteSouvenirStok(context, id,id1);
            }
        });
    }

    public void deleteSouvenirStok(final Context context, int id, int id1){
        if (id != 0){
            String a = String.valueOf(id);
            String b = String.valueOf(id1);
            String c = b+a;
            String contentType = "application/json";
            final String  tokenAuthorization = SessionManager.getToken(context);
            apiServices = APIUtilities.getAPIServices();
            apiServices.delSouvenirStockAddItem(contentType, tokenAuthorization, c).enqueue(new Callback<ResponseGetItemSouvenirStok>() {
                @Override
                public void onResponse(Call<ResponseGetItemSouvenirStok> call, Response<ResponseGetItemSouvenirStok> response) {
                    if (response.code() == 201){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseGetItemSouvenirStok> call, Throwable t) {
                    Toast.makeText(context, "onFailure : ", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
