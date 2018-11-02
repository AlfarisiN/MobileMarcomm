
package com.android.mobilemarcom.model.ModelSouvenir;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseAutoCompleteSouvenir {

    @SerializedName("dataList")
    @Expose
    private List<RequestAutoCompleteSouvenir> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<RequestAutoCompleteSouvenir> getDataList() {
        return dataList;
    }

    public void setDataList(List<RequestAutoCompleteSouvenir> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
