package com.android.mobilemarcom.employee.API;

public class ModelListEmployee {
    private String id;
    private String code;
    private String firstName;
    private String lastName;
    private String company;
    private String email;

    public ModelListEmployee(String id, String code, String firstName, String lastName, String company, String email) {
        this.id = id;
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }
}
