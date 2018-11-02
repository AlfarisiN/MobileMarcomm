package com.android.mobilemarcom.employee.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

public class EmployeeAPIUtilities {
    private static String BASE_URL_API = "http://139.162.5.173:8080/marcomm-ws/api/";
    private static String BASE_URL_API_2 = "https://documenter.getpostman.com/view/5586411/RWgqVynd";

    public static RequestAPI getAPI(){
        return RetrofitClient2.getClient(BASE_URL_API).create(RequestAPI.class);
    }
    public static String addCompany(String username, String password, Object mRoleId, Object mEmployeeId){
        Map<String,Object> map = new HashMap<>();
        if(username !=null ) map.put("username", username);
        if(password != null) map.put("password", password);
        if(mRoleId != null)  map.put("mRoleId", mRoleId);
        if(mEmployeeId != null) map.put("mEmployeeId", mEmployeeId);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);
        return json;

    }

    public static MediaType mediaType() {
        return MediaType.parse("application/json");
    }

}

