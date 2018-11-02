
package com.android.mobilemarcom.model.ModelSouvenirStok;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseSouvenirStockAddItem {

    @SerializedName("dataList")
    @Expose
    private List<RequestSouvenirStockAddItem> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<RequestSouvenirStockAddItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<RequestSouvenirStockAddItem> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
