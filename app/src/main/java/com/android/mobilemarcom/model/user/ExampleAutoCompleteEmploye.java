package com.android.mobilemarcom.model.user;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleAutoCompleteEmploye {
    @SerializedName("dataList")
    @Expose
    private List<DataListAutoCompleteEmploye> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataListAutoCompleteEmploye> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListAutoCompleteEmploye> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
