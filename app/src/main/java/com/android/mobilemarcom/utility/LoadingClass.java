package com.android.mobilemarcom.utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.android.mobilemarcom.R;

public class LoadingClass {
    public static ProgressDialog loadingAnimationAndText(Context context, String text){
        ProgressDialog loading = new ProgressDialog(context, R.style.SimpleLoadingStyle);
        loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        //digunakan untuk pada saat loading tidak bisa dihilangkan login nya
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage(text);
        return loading;
    }

    public static ProgressDialog loadingAnimation(Context context){
        ProgressDialog loading = new ProgressDialog(context, R.style.SimpleLoadingStyle);
        loading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        //digunakan untuk pada saat loading tidak bisa dihilangkan login nya
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        return loading;
    }
}
