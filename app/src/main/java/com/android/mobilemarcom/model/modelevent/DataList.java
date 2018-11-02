
package com.android.mobilemarcom.model.modelevent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("eventName")
    @Expose
    private String eventName;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private Object endDate;
    @SerializedName("place")
    @Expose
    private Object place;
    @SerializedName("budget")
    @Expose
    private Object budget;
    @SerializedName("requestedBy")
    @Expose
    private Object requestedBy;
    @SerializedName("requestedDate")
    @Expose
    private Object requestedDate;
    @SerializedName("approvedBy")
    @Expose
    private Object approvedBy;
    @SerializedName("approvedDate")
    @Expose
    private Object approvedDate;
    @SerializedName("assignTo")
    @Expose
    private Object assignTo;
    @SerializedName("closedDate")
    @Expose
    private Object closedDate;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("rejectReason")
    @Expose
    private Object rejectReason;
    @SerializedName("isDelete")
    @Expose
    private Object isDelete;

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

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public Object getPlace() {
        return place;
    }

    public void setPlace(Object place) {
        this.place = place;
    }

    public Object getBudget() {
        return budget;
    }

    public void setBudget(Object budget) {
        this.budget = budget;
    }

    public Object getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(Object requestedBy) {
        this.requestedBy = requestedBy;
    }

    public Object getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Object requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Object getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Object approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Object getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Object approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Object getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(Object assignTo) {
        this.assignTo = assignTo;
    }

    public Object getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Object closedDate) {
        this.closedDate = closedDate;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(Object rejectReason) {
        this.rejectReason = rejectReason;
    }

    public Object getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Object isDelete) {
        this.isDelete = isDelete;
    }

}
