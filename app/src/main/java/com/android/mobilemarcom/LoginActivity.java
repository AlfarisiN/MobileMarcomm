package com.android.mobilemarcom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.mobilemarcom.model.Login;
import com.android.mobilemarcom.retrofit.APIUtilities;
import com.android.mobilemarcom.retrofit.RequestAPIServices;
import com.android.mobilemarcom.utility.LoadingClass;
import com.android.mobilemarcom.utility.SessionManager;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Context context = this;
    private Button btn_login;
    private RequestAPIServices requestAPIServices;
    private EditText input_username,input_password;
    private ProgressDialog loading;
    private String username,password;
    private TextInputLayout input_username_textlayout,input_password_textlayout;
    private ImageView gambarLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (Button) findViewById(R.id.btn_login);
        input_username = (EditText) findViewById(R.id.input_username);
        input_password = (EditText) findViewById(R.id.input_password);
        input_username_textlayout = (TextInputLayout) findViewById(R.id.input_username_textlayout);
        input_password_textlayout = (TextInputLayout) findViewById(R.id.input_password_textlayout);
        gambarLogin = (ImageView) findViewById(R.id.gambarLogin);

        Animation company_logo = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom_animation);
        gambarLogin.startAnimation(company_logo);

        Animation fadeIn  = AnimationUtils.loadAnimation(this, R.anim.fadein);
        input_username.startAnimation(fadeIn);
        input_password.startAnimation(fadeIn);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validasi();
            }
        });

    }

    private void validasi(){
        if(!validateUsername() | !validatePassword()){
            return;
        } else{
            username = input_username.getText().toString().trim();
            password = input_password.getText().toString().trim();
            requestLogin(username,password);
        }
    }

    private void requestLogin(final String username, String password){
        String json = APIUtilities.generateLoginMap(username,password);
        RequestBody requestBody = RequestBody.create(APIUtilities.mediaType(), json);

        final ProgressDialog loading = LoadingClass.loadingAnimationAndText(context,"Tunggu sebentar...");
        loading.show();

        String contentType = "application/json";
        requestAPIServices = APIUtilities.getAPIServices();
        requestAPIServices.loginUser(contentType,requestBody).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                loading.dismiss();
                if(response.code()==200){
                    if(response.body().getGeneratedToken()!=null){
                        SessionManager.saveRegistrationData(context,username,response.body().getGeneratedToken(),1,true);
                        Toast.makeText(context,"Berhasil login",Toast.LENGTH_SHORT).show();
                        Intent in = new Intent(context,MainActivity.class);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(in);
                    }
                }else if(response.code()==401){
                    Toast.makeText(context,"Invalid username/password",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(context,"Gagal login",Toast.LENGTH_SHORT).show();
            }
        });

    }


    public boolean validateUsername(){
        String check = input_username.getText().toString().trim();
        if(check.isEmpty()){
            input_username_textlayout.setError("Username tidak boleh kosong");
            return false;
        }else{
            input_username_textlayout.setError(null);
            return true;
        }
    }

    public boolean validatePassword() {
        String check = input_password.getText().toString().trim();
        if (check.isEmpty()) {
            input_password_textlayout.setError("Password tidak boleh kosong");
            return false;
        } else {
            input_password_textlayout.setError(null);
            return true;
        }
    }
}
