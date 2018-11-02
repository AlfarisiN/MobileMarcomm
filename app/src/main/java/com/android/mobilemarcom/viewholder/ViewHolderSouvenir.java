package com.android.mobilemarcom.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.model.ModelSouvenir.DataListModel;
import com.android.mobilemarcom.model.ModelSouvenir.DataListSouvenir;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.souvenir.EditSouvenirActivity;
import com.android.mobilemarcom.utility.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHolderSouvenir extends RecyclerView.ViewHolder {
    private TextView souvenir_list_code, souvenir_list_name, souvenir_list_quantity, souvenir_list_status;
    private ImageView image_souvenir_option;
    private RequestAPIServices apiServices;

    public ViewHolderSouvenir(@NonNull View itemView) {
        super(itemView);

        souvenir_list_code = (TextView) itemView.findViewById(R.id.souvenir_list_code);
        souvenir_list_name = (TextView) itemView.findViewById(R.id.souvenir_list_name);
        souvenir_list_quantity = (TextView) itemView.findViewById(R.id.souvenir_list_quantity);
        souvenir_list_status = (TextView) itemView.findViewById(R.id.souvenir_list_status);
        image_souvenir_option = (ImageView) itemView.findViewById(R.id.image_souvenir_option);

    }

    public void setModelSouvenir(final Context context, final DataListModel souvenir) {
        //set Code
        if (souvenir.getCode() != null) souvenir_list_code.setText(souvenir.getCode());
        if (souvenir.getName() != null) souvenir_list_name.setText(souvenir.getName());
        if (souvenir.getQuantity() != null) souvenir_list_quantity.setText(""+souvenir.getQuantity());
        if (souvenir.getIsDelete().equals(false)) {
            souvenir_list_status.setText("Aktif");
        } else {
            souvenir_list_status.setText("Deactived");
        }
        image_souvenir_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, image_souvenir_option);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.edit:
                                Intent intent = new Intent(context, EditSouvenirActivity.class);
                                intent.putExtra("name",souvenir.getName().toString() );
                                intent.putExtra("unit",souvenir.getName().toString());
                                intent.putExtra("id", souvenir.getId());
                                context.startActivity(intent);
                                return true;
                            case R.id.deactive :
                                int id = souvenir.getId();
                                deactiveSouvenir(context, id);
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

    private void deactiveSouvenir(final Context context, int id){
        if (id != 0){
            String contentType = "application/json";
            String token = SessionManager.getToken(context);

            apiServices = APIUtilities.getAPIServices();
            apiServices.deactivateSouvenir(contentType, token, id).enqueue(new Callback<DataListSouvenir>() {
                @Override
                public void onResponse(Call<DataListSouvenir> call, Response<DataListSouvenir> response) {
                    if (response.code() == 200){
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Data Not Change!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<DataListSouvenir> call, Throwable t) {
                    Toast.makeText(context, "Error onFailure : "+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}