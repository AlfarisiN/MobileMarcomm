package com.android.mobilemarcom;

import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class Header {
    private int id;
    private String code;
    private String name;
    private Spinner type;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Spinner type() {
        return type;
    }

    public void setType(Spinner type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public SpinnerAdapter getType() {
        return (SpinnerAdapter) type;
    }

}
