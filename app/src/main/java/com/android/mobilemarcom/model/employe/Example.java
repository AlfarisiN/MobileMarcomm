
package com.android.mobilemarcom.model.employe;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("dataList")
    @Expose
    private List<DataListEmploye> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataListEmploye> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListEmploye> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
