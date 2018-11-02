
package com.android.mobilemarcom.model.ModelSouvenir;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSouvenir {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("unit")
    @Expose
    private RequestSouvenirUnit unit;
    @SerializedName("notes")
    @Expose
    private String notes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestSouvenirUnit getUnit() {
        return unit;
    }

    public void setUnit(RequestSouvenirUnit unit) {
        this.unit = unit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
