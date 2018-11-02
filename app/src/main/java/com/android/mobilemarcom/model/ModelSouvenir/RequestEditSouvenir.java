
package com.android.mobilemarcom.model.ModelSouvenir;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestEditSouvenir {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("unit")
    @Expose
    private RequestEditSouvenirUnit unit;
    @SerializedName("notes")
    @Expose
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RequestEditSouvenirUnit getUnit() {
        return unit;
    }

    public void setUnit(RequestEditSouvenirUnit unit) {
        this.unit = unit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
