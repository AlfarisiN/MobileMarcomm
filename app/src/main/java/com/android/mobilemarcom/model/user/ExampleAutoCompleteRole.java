package com.android.mobilemarcom.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleAutoCompleteRole {
    @SerializedName("dataList")
    @Expose
    private List<DataListAutoCompleteRole> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataListAutoCompleteRole> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListAutoCompleteRole> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
