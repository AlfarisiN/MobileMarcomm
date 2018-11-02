package com.android.mobilemarcom.viewholder;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.souvenirRequest.API.DataList;
import com.android.mobilemarcom.souvenirRequest.RequestApprovalActivity;
import com.android.mobilemarcom.souvenirRequest.RequestCloseActivity;
import com.android.mobilemarcom.souvenirRequest.SouvenirReqAddItemActivity;


public class ViewHolderSouvenirReq extends RecyclerView.ViewHolder {
    private TextView req_code,req_date,req_by,req_status;
    private ImageView imageUser;
    private Context context;
    private RequestAPIServices requestAPI;

    public ViewHolderSouvenirReq(@NonNull View itemView) {
        super(itemView);
        req_code = (TextView) itemView.findViewById(R.id.souvenir_req_code);
        req_date = (TextView) itemView.findViewById(R.id.souvenir_req_date);
        req_by = (TextView) itemView.findViewById(R.id.souvenir_req_by);
        req_status = (TextView) itemView.findViewById(R.id.souvenir_req_stat);
        imageUser = (ImageView) itemView.findViewById(R.id.image_employee_option);
    }

    public void setModelSouvenirReq(final Context context, final DataList dataList) {
        String code = dataList.getCode().toString();
        req_code.setText(code);
        req_date.setText(dataList.getRequestedDate().toString());
        req_by.setText(dataList.getRequestedBy().getFirstName().toString() +" "+dataList.getRequestedBy().getLastName().toString());

        String stat = String.valueOf(dataList.getStatus());
        req_status.setText(stat);
        if(req_status == null){
            stat = "Deactive";
        }
        else{
            stat = "Done";
        }
        req_status.setText(stat);
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, imageUser);
                popupMenu.getMenuInflater().inflate(R.menu.popup_req, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.addItem:
                                Intent i = new Intent(context, SouvenirReqAddItemActivity.class);
//                                i.putExtra("id", employee.getId().toString());
//                                i.putExtra("lastName", employee.getLastName().toString());
                                context.startActivity(i);
                                return true;
                            case R.id.approval:
                                Intent in = new Intent(context, RequestApprovalActivity.class);
                                in.putExtra("id",dataList.getId());
                                in.putExtra("request_data",dataList.getRequestedDate());
                                in.putExtra("code",dataList.getCode());
                                in.putExtra("event","TRW0EVDDMMYY0000"+dataList.getId());
                                context.startActivity(in);
                                return true;
                            case R.id.close:
                                Intent intent = new Intent(context, RequestCloseActivity.class);
                                intent.putExtra("id",dataList.getId());
                                intent.putExtra("request_data",dataList.getRequestedDate());
                                intent.putExtra("code",dataList.getCode());
                                intent.putExtra("event","TRW0EVDDMMYY0000"+dataList.getId());
                                context.startActivity(intent);
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }
//    private void deactiveUser(final Context context, int id) {
//        if (id != 0) {
//            String contentType = "application/json";
//            String token = SessionManager.getToken(context);
//            final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,
//                    "Waiting...");
//            loading.show();
//            requestAPI = EmployeeAPIUtilities.getAPI();
//            requestAPI.deactivateEmployee(contentType, token, "7").enqueue(new Callback<ExampleGet>() {
//                @Override
//                public void onResponse(Call<ExampleGet> call, Response<ExampleGet> response) {
//                    loading.dismiss();
//                    if (response.code() == 200) {
//                        if (response.body().getMessage() != null) {
//                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(context, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ExampleGet> call, Throwable t) {
//                    loading.dismiss();
//                    System.out.println("Error " + t.getMessage());
//                }
//            });
//        }
//    }

}