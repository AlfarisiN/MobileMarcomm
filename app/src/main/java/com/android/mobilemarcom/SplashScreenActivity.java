package com.android.mobilemarcom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mobilemarcom.utility.Constanta;
import com.android.mobilemarcom.utility.SessionManager;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreenActivity extends Activity {
    private Context context = this;
    ImageView companyLogo;
    TextView mainTitle, subTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        companyLogo=findViewById(R.id.companyLogo);
        mainTitle=findViewById(R.id.mainTitle);
        subTitle=findViewById(R.id.subTitle);

        Animation company_logo = AnimationUtils.loadAnimation(this, R.anim.top_to_bottom_animation);
        companyLogo.startAnimation(company_logo);

        Animation fadeIn  = AnimationUtils.loadAnimation(this, R.anim.fadein);
        mainTitle.startAnimation(fadeIn);
        subTitle.startAnimation(fadeIn);


        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(SessionManager.isLogin(context)){
                    //bypass ke mainmenu
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, Constanta.SPLASH_DELAY_TIME);
    }

//    public void getAppVersion(){
//        String version = Constanta.WELCOME_APP_NAME+" "+ getResources().getString(R.string.app_name);
//
//        Toast.makeText(context,version,Toast.LENGTH_LONG).show();
//    }
}
