
package com.android.mobilemarcom.unit.modelunit;

import java.util.List;

import com.android.mobilemarcom.event.modelevent.EventModul;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelUnitRetrofit {

    @SerializedName("dataList")
    @Expose
    private List<DataList> dataList = null; //unit
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataList> getDataList() {
        return dataList;
    } //untuk ambil data unit

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
