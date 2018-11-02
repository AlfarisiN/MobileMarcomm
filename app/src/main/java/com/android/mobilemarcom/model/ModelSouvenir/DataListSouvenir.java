
package com.android.mobilemarcom.model.ModelSouvenir;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataListSouvenir {

    @SerializedName("dataList")
    @Expose
    private List<DataListModel> dataList = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataListModel> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataListModel> dataList) {
        this.dataList = dataList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
