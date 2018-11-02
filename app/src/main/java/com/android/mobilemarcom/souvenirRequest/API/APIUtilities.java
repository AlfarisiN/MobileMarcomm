package com.android.mobilemarcom.souvenirRequest.API;

import com.android.mobilemarcom.employee.API.RetrofitClient2;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

public class APIUtilities {
    private static String BASE_URL_API = "http://139.162.5.173:8080/marcomm-ws/api/";
    private static String BASE_URL_API_2 = "https://documenter.getpostman.com/view/5586411/RWgqVynd";

    public static RequestAPIServices getAPI(){
        return RetrofitClient2.getClient(BASE_URL_API).create(RequestAPIServices.class);
    }
    public static MediaType mediaType() {
        return MediaType.parse("application/json");
    }
    public static String generateSouvenirReqAdd(int event, String dueDate, String notes){
        Map<String, Object> map = new HashMap<>();
        if(dueDate != null) map.put("dueDate", dueDate);
        if(notes != null) map.put("notes", notes);

        if(event != 0){
            Map<String, Object> unitObj = new HashMap<>();
            unitObj.put("id", event);

            map.put("event", unitObj);
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);

        return json;
    }
    public static String generateSouvenirAddItem(String souvenir, String quantity, String notes){
        Map<String, Object> map = new HashMap<>();
        if (quantity != null) map.put("quantity", quantity);
        if (notes != null) map.put("notes", notes);
        if (souvenir != null){
            Map<String, String> unitObj = new HashMap<>();
            unitObj.put("id", souvenir);
            map.put("souvenir", unitObj);
        }

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);

        return json;

    }
}
