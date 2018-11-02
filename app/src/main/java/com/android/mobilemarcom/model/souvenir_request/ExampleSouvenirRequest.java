package com.android.mobilemarcom.model.souvenir_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleSouvenirRequest {
    @SerializedName("dataList")
    @Expose
    private List<DataListSouvenirRequest> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataListSouvenirRequest> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListSouvenirRequest> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
