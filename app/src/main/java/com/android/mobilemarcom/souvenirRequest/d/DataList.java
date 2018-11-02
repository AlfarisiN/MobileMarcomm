
package com.android.mobilemarcom.souvenirRequest.d;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("souvenirRequest")
    @Expose
    private Object souvenirRequest;
    @SerializedName("souvenir")
    @Expose
    private Souvenir souvenir;
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

    public Souvenir getSouvenir() {
        return souvenir;
    }

    public void setSouvenir(Souvenir souvenir) {
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
