
package com.android.mobilemarcom.employee.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ExampleAuto {

    @SerializedName("dataList")
    @Expose
    private List<DataListAuto> dataListAuto = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<DataListAuto> getDataListAuto() {
        return dataListAuto;
    }

    public void setDataList(List<DataListAuto> dataListAuto) {
        this.dataListAuto = dataListAuto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
