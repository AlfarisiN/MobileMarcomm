
package com.android.mobilemarcom.model.ModelSouvenirStok;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSouvenirStockItem {

    @SerializedName("souvenir")
    @Expose
    private RequestSouvenirStockItemId souvenir;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("notes")
    @Expose
    private String notes;

    public RequestSouvenirStockItemId getSouvenir() {
        return souvenir;
    }

    public void setSouvenir(RequestSouvenirStockItemId souvenir) {
        this.souvenir = souvenir;
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
