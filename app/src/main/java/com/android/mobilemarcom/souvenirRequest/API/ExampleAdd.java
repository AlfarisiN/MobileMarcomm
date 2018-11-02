
package com.android.mobilemarcom.souvenirRequest.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExampleAdd {

    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("dueDate")
    @Expose
    private String dueDate;
    @SerializedName("notes")
    @Expose
    private String notes;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
