
package com.android.mobilemarcom.employee.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleGet {

    @SerializedName("dataList")
    @Expose
    private List<DataList> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataList> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataList> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
