
package com.android.mobilemarcom.model.ModelSouvenirStok;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetItemSouvenirStok {

    @SerializedName("dataList")
    @Expose
    private List<RequestGetItemSouvenirStok> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<RequestGetItemSouvenirStok> getDataList() {
        return dataList;
    }

    public void setDataList(List<RequestGetItemSouvenirStok> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
