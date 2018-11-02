package com.android.mobilemarcom.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;

public class APIUtilities {
    public static String BASE_URL = "http://139.162.5.173:8080/marcomm-ws/api/";
    public static final String CONTENT_HEADER = "application/json";
    public static final String AUTHORIZATION_UNIT_DEACTIVE = "ZQNOFY4FVX77MELCTMDNZIHHLSCDPYX5IIRIY5LCHT18BIDD94HU49H6STZD2ACTR8QUD4OGJ75SWKWJYKP2NCZ2O1C3QNMX76GLAZNBPXOKCAAVI1T2XENTVW4XPIHI";
    public static final String AUTHORIZATION_UNIT_SEARCH = "JCZXSHTUOIW5PAAGXIYZFTTX43KGRGJGFKL8DLMPJUMNFRIYOSTZUSL2157WV2MKY8CNNJDP8SAYN1KHHGBHV0B2W1UFPCR4APQKYEW6HJVFM98F4KY5T0QVWRGZXRTP";
    public static final String AUTHORIZATION_UNIT_EDIT = "ZQNOFY4FVX77MELCTMDNZIHHLSCDPYX5IIRIY5LCHT18BIDD94HU49H6STZD2ACTR8QUD4OGJ75SWKWJYKP2NCZ2O1C3QNMX76GLAZNBPXOKCAAVI1T2XENTVW4XPIHI";

    public static RequestAPIServices getAPIServices(){
        return RetrofitClient.getClient(BASE_URL).create(RequestAPIServices.class);
    }

    public static MediaType mediaType() {
        return okhttp3.MediaType.parse("application/json; charset=utf-8");
    }

    //generate login body request
    public static String generateLoginMap(String username, String password){
        Map<String, String> map = new HashMap<>();
        if(username != null) map.put("username", username);
        if(password != null) map.put("password", password);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);

        return json;
    }
    public static String addUnit(String name,String notes){
        Map<String,Object> map = new HashMap<>();
        map.put("notes",notes);
        map.put("name",name);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);
        return json;
    }

    public static String editUnit(int id,String name,String notes){
        Map<String,Object> map = new HashMap<>();
        map.put("notes",notes);
        map.put("name",name);
        map.put("id",id);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);
        return json;
    }

    public static String editUser(Object id,String username, String password, Object mRoleId, Object mEmployeeId){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        if(username !=null ) map.put("username", username);
        if(password != null) map.put("password", password);
        if(mRoleId != null)  map.put("mRoleId", mRoleId);
        if(mEmployeeId != null) map.put("mEmployeeId", mEmployeeId);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);
        return json;

    }

    public static String addUser(String username, String password, Object mRoleId, Object mEmployeeId){
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

    /* Souvenir by Denis */

    //generateCreateSouvenir
    public static String generateSouvenirMap(String name, Object unit, String notes){
        Map<String, Object> map = new HashMap<>();
        if (name != null) map.put("name", name);
        if (notes != null) map.put("notes", notes);
        Map<String, Object> unitObj = new HashMap<>();
        unitObj.put("id", unit);
        map.put("unit", unitObj);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);

        return json;
    }

    public static String generateSouvenirItem(Object souvenir, String quantity, String notes){
        Map<String, Object> map = new HashMap<>();
        if (quantity != null) map.put("quantity", quantity);
        if (notes != null) map.put("notes", notes);
        Map<String, Object> unitObj = new HashMap<>();
        unitObj.put("id", souvenir);
        map.put("souvenir", unitObj);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);

        return json;

    }

    public static String editSouvenir(Object id,String name, Object unit, String notes){
        Map<String,Object> map = new HashMap<>();
        Map<String, Object> unitObj = new HashMap<>();
        unitObj.put("id", unit);
        map.put("unit", unitObj);
        map.put("id",id);
        if(name !=null ) map.put("name", name);
        if(notes != null) map.put("notes", notes);

        
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);
        return json;
    }

    public static String createSouvenirStok(Object receivedBy, String receivedDate, String notes){
        Map<String,Object> map = new HashMap<>();
        Map<String, Object> unitObj = new HashMap<>();
        unitObj.put("id", receivedBy);
        map.put("receivedBy", unitObj);
        if(receivedDate !=null ) map.put("receivedDate", receivedDate);
        if(notes != null) map.put("notes", notes);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);
        return json;
    }
    public static String addEvents(String eventName,String sDate,String eDate,String place,int budget,String notes){
        Map<String,Object> map = new HashMap<>();
        map.put("notes",notes);
        map.put("eventName",eventName);
        map.put("startDate",sDate);
        map.put("endDate",eDate);
        map.put("place",place);
        map.put("budget",budget);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);
        return json;
    }
    public static String editEvents(int id,String eventName,String sDate,String eDate,String place,int budget,String notes){
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("notes",notes);
        map.put("eventName",eventName);
        map.put("startDate",sDate);
        map.put("endDate",eDate);
        map.put("place",place);
        map.put("budget",budget);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.serializeNulls().create();
        String json = gson.toJson(map);
        return json;
    }
}