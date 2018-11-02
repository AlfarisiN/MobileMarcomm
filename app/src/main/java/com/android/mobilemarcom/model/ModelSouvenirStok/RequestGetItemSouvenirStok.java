
package com.android.mobilemarcom.model.ModelSouvenirStok;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestGetItemSouvenirStok {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("request")
    @Expose
    private Object request;
    @SerializedName("souvenir")
    @Expose
    private RequestGetItemSouvenirStokSouvenir souvenir;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("notes")
    @Expose
    private Object notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public RequestGetItemSouvenirStokSouvenir getSouvenir() {
        return souvenir;
    }

    public void setSouvenir(RequestGetItemSouvenirStokSouvenir souvenir) {
        this.souvenir = souvenir;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

}
