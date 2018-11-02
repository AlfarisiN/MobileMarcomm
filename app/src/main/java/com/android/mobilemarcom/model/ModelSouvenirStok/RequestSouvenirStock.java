
package com.android.mobilemarcom.model.ModelSouvenirStok;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSouvenirStock {

    @SerializedName("receivedBy")
    @Expose
    private RequestSouvenirStockReceivedBy receivedBy;
    @SerializedName("receivedDate")
    @Expose
    private String receivedDate;
    @SerializedName("notes")
    @Expose
    private String notes;

    public RequestSouvenirStockReceivedBy getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(RequestSouvenirStockReceivedBy receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
