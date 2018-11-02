package com.android.mobilemarcom.utility;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    //deklarasi shared preference
    protected static SharedPreferences retrieveSharedPreferences(Context context){
        return context.getSharedPreferences(Constanta.SHARED_PREFERENCE_NAME,
                context.MODE_PRIVATE);
    }

    //mode edit (CRUD)
    protected static SharedPreferences.Editor retrieveSharedPreferencesEditor(Context context){
        return retrieveSharedPreferences(context).edit();
    }


    /*deklarasikan getter/setter sesuai kebutuhan*/

    //simpan data register
    public static void saveRegistrationData(Context context,
                                            String username,
                                            String token,
                                            int id,
                                            boolean login
    ){
        SharedPreferences.Editor editor = retrieveSharedPreferencesEditor(context);
        editor.putString("username", username);
        editor.putString("token", token);
        editor.putBoolean("login", login);
        editor.putInt("id",id);
        editor.commit();
    }

    public static void logoutUser(Context context){
        SharedPreferences.Editor editor = retrieveSharedPreferencesEditor(context);
        editor.clear();
        editor.commit();
    }

    //ambil data register
    public static String getUsername(Context context){
        return retrieveSharedPreferences(context).getString("username", "");
    }

    public static String getToken(Context context){
        return retrieveSharedPreferences(context).getString("token", "");
    }

    public static Integer getId(Context context){
        return retrieveSharedPreferences(context).getInt("id", 0);
    }

    public static boolean isLogin(Context context){
        return retrieveSharedPreferences(context).getBoolean("login", false);
    }

}