
package com.android.mobilemarcom.souvenirRequest.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleAddItem {

    @SerializedName("dataListAddItem")
    @Expose
    private List<DataListAddItem> dataListAddItem = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataListAddItem> getDataListAddItem() {
        return dataListAddItem;
    }

    public void setDataListAddItem(List<DataListAddItem> dataListAddItem) {
        this.dataListAddItem = dataListAddItem;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
