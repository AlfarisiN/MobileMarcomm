
package com.android.mobilemarcom.model.ModelSouvenirStok;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSouvenirStockAddItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("receivedBy")
    @Expose
    private RequestSouvenirStockAddItemReceivedBy receivedBy;
    @SerializedName("receivedDate")
    @Expose
    private String receivedDate;
    @SerializedName("notes")
    @Expose
    private Object notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RequestSouvenirStockAddItemReceivedBy getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(RequestSouvenirStockAddItemReceivedBy receivedBy) {
        this.receivedBy = receivedBy;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

}
