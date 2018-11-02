package com.android.mobilemarcom.viewholder;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.R;
import com.android.mobilemarcom.employee.API.Employee;
import com.android.mobilemarcom.employee.API.EmployeeAPIUtilities;
import com.android.mobilemarcom.employee.API.ExampleGet;
import com.android.mobilemarcom.employee.API.RequestAPI;
import com.android.mobilemarcom.employee.EditEmployeeActivity;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewHolderEmployee extends RecyclerView.ViewHolder {
    private TextView employee_id,employee_fullname,employee_company,employee_status;
    private ImageView imageUser;
    private Context context;
    private RequestAPI requestAPI;

    public ViewHolderEmployee(@NonNull View itemView) {
        super(itemView);
        employee_id = (TextView) itemView.findViewById(R.id.employee_id);
        employee_fullname = (TextView) itemView.findViewById(R.id.employee_fullname);
        employee_company = (TextView) itemView.findViewById(R.id.employee_company);
        employee_status = (TextView) itemView.findViewById(R.id.employee_status);
        imageUser = (ImageView) itemView.findViewById(R.id.image_employee_option);
    }

    public void setModelEmployee(final Context context, final Employee employee) {
        String emp_id = String.valueOf(employee.getCode());
        employee_id.setText(emp_id);
        if(employee_id == null){
            emp_id = "10.10.10.10";
        }else{
            emp_id = "11.11.11.11";
        }
        employee_id.setText(emp_id);

        String name = employee.getFirstName() +" "+ employee.getLastName();
        employee_fullname.setText(name);

        String comp = (String) employee.getCompany();
        employee_company.setText(comp);

        String stat = (String) employee.getIsDelete();
        employee_status.setText(stat);
        if(employee_status == null){
            stat = "Deactive";
        }
        else{
            stat = "Active";
        }
        employee_status.setText(stat);
        imageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, imageUser);
                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.edit:
                                Intent i = new Intent(context, EditEmployeeActivity.class);
//                                i.putExtra("code", employee.getCode().toString());
                                i.putExtra("lastName", employee.getLastName().toString());
                                i.putExtra("firstName", employee.getFirstName().toString());
                                context.startActivity(i);

                                return true;
                            case R.id.deactive:
                                int id = employee.getId();
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
            requestAPI = EmployeeAPIUtilities.getAPI();
            requestAPI.deactivateEmployee(contentType, token, id ).enqueue(new Callback<ExampleGet>() {
                @Override
                public void onResponse(Call<ExampleGet> call, Response<ExampleGet> response) {
                    loading.dismiss();
                    if (response.code() == 200) {
                        if (response.body().getMessage() != null) {
                            Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ExampleGet> call, Throwable t) {
                    loading.dismiss();
                    System.out.println("Error " + t.getMessage());
                }
            });
        }
    }

}
