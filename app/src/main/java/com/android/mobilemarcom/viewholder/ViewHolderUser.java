package com.android.mobilemarcom.viewholder;

import android.app.ProgressDialog;
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
import com.android.mobilemarcom.user.EditUserActivity;
import com.android.mobilemarcom.model.user.DataList;
import com.android.mobilemarcom.model.user.UserAdd;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHolderUser extends RecyclerView.ViewHolder {
    private TextView empName,username,role,status;
    private ImageView imageUser;
    private RequestAPIServices requestAPIServices;

    public ViewHolderUser(@NonNull View itemView) {
        super(itemView);
        empName = (TextView) itemView.findViewById(R.id.empName);
        username = (TextView) itemView.findViewById(R.id.username);
        role = (TextView) itemView.findViewById(R.id.role);
        status = (TextView) itemView.findViewById(R.id.status);
        imageUser = (ImageView) itemView.findViewById(R.id.imageUser);
    }

    public void setModel(final Context context, final DataList dataLists){
            if(dataLists!=null){
                empName.setText(dataLists.getEmployee().getFirstName().toString());
                username.setText(dataLists.getUsername().toString());
                role.setText(dataLists.getRole().getName().toString());;
                if(dataLists.getIsDelete()==true){
                    status.setText("Non Active");
                }else{
                    status.setText("Active");
                }
                imageUser.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu = new PopupMenu(context,imageUser);
                        popupMenu.getMenuInflater().inflate(R.menu.popup,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.edit:
                                        Intent in = new Intent(context, EditUserActivity.class);
                                        in.putExtra("role",dataLists.getRole().getName().toString());
                                        in.putExtra("employe",dataLists.getEmployee().getLastName().toString());
                                        in.putExtra("username",dataLists.getUsername().toString());
                                        in.putExtra("id",dataLists.getId());
                                        in.putExtra("idEmploye",dataLists.getEmployee().getId());
                                        context.startActivity(in);
                                        return true;
                                    case R.id.deactive:
                                        int id = dataLists.getId();
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

    }

    private void deactiveUser(final Context context, int id){
        if(id!=0){
            String contentType = "application/json";
            String token = SessionManager.getToken(context);
            final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,
                    "Waiting...");
            loading.show();
            requestAPIServices = APIUtilities.getAPIServices();
            requestAPIServices.deactiveUser(contentType,token,id).enqueue(new Callback<UserAdd>() {
                @Override
                public void onResponse(Call<UserAdd> call, Response<UserAdd> response) {
                    loading.dismiss();
                    if(response.code()==200){
                        if(response.body().getMessage()!=null){
                            Toast.makeText(context,response.body().getMessage(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context,"Data gagal ditambahkan",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserAdd> call, Throwable t) {
                    loading.dismiss();
                    System.out.println("Error "+t.getMessage());
                }
            });
        }
    }
}