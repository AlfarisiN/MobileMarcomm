
package com.android.mobilemarcom.souvenirRequest.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleCreateAdd {

    @SerializedName("souvenirCreateAdd")
    @Expose
    private SouvenirCreateAdd souvenirCreateAdd;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("notes")
    @Expose
    private String notes;

    public SouvenirCreateAdd getSouvenirCreateAdd() {
        return souvenirCreateAdd;
    }

    public void setSouvenirCreateAdd(SouvenirCreateAdd souvenirCreateAdd) {
        this.souvenirCreateAdd = souvenirCreateAdd;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
