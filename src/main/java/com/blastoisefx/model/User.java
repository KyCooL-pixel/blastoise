package com.blastoisefx.model;

public class User {
    private String name;
    private String contactNumber;

    // create user by giving name and contact number
    public User(String name, String contactNumber) {
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public String getcontactNumber() {
        return contactNumber;
    }

}
