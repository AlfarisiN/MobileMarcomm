
package com.android.mobilemarcom.model.ModelSouvenirStok;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSouvenirStockItemId {

    @SerializedName("id")
    @Expose
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}