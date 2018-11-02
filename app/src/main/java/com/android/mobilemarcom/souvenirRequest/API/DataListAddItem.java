
package com.android.mobilemarcom.souvenirRequest.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataListAddItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("souvenirRequest")
    @Expose
    private Object souvenirRequest;
    @SerializedName("souvenirAddItem")
    @Expose
    private SouvenirAddItem souvenirAddItem;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("notes")
    @Expose
    private Object notes;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getSouvenirRequest() {
        return souvenirRequest;
    }

    public void setSouvenirRequest(Object souvenirRequest) {
        this.souvenirRequest = souvenirRequest;
    }

    public SouvenirAddItem getSouvenirAddItem() {
        return souvenirAddItem;
    }

    public void setSouvenirAddItem(SouvenirAddItem souvenirAddItem) {
        this.souvenirAddItem = souvenirAddItem;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

}
