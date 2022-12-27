package com.blastoisefx.model;

public class User {
    private String email;
    private String password;

    // create user by giving name and contact number
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
