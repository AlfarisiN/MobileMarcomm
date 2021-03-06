package com.android.mobilemarcom.model.souvenir_request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataListSouvenirRequest {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("souvenirRequest")
    @Expose
    private Object souvenirRequest;
    @SerializedName("souvenir")
    @Expose
    private SouvenirRequest souvenir;
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

    public SouvenirRequest getSouvenir() {
        return souvenir;
    }

    public void setSouvenir(SouvenirRequest souvenir) {
        this.souvenir = souvenir;
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
