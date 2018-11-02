package com.android.mobilemarcom.viewholder;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.souvenirRequest.API.APIUtilities;
import com.android.mobilemarcom.souvenirRequest.API.RequestAPIServices;
import com.android.mobilemarcom.souvenirRequest.d.DataList;
import com.android.mobilemarcom.souvenirRequest.d.Example;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHolderSouvenirReqItem extends RecyclerView.ViewHolder {
    private TextView souvenir_item, souvenir_quantity_item;
    private ImageView imageUser;
    private Context context;
    private RequestAPIServices requestAPI;

    public ViewHolderSouvenirReqItem(@NonNull View itemView) {
        super(itemView);
        souvenir_item = (TextView) itemView.findViewById(R.id.souvenir_item);
        souvenir_quantity_item = (TextView) itemView.findViewById(R.id.souvenir_quantity_item);
        imageUser = (ImageView) itemView.findViewById(R.id.image_employee_option);
    }

    @SuppressLint("ResourceType")
    public void setModelSouvenirReqItem(final Context context, final DataList dataList) {
        String code = String.valueOf(dataList.getId());
        souvenir_item.setText(code);
        if(souvenir_item.getId() == 1){
            code = "Notes";
        }else{
            code = "notes";
        }
        souvenir_item.setText(code);
        souvenir_quantity_item.setText(""+dataList.getQty());

        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, imageUser);
                popupMenu.getMenuInflater().inflate(R.menu.popup_req_2, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                int id = dataList.getId();
                                deactiveUser(context,id);
                                return true;

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void deactiveUser(final Context context, int id) {
        if (id != 0) {
            String contentType = "application/json";
            String token = SessionManager.getToken(context);
            final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,
                    "Waiting...");
            loading.show();
            requestAPI = APIUtilities.getAPI();
            requestAPI.deleteItem(contentType, token, id).enqueue(new Callback<Example>() {
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
