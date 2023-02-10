package com.blastoisefx.model;

import java.io.Serializable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class User implements Serializable {
    private String email;
    private String password;
    private ObservableList<QueueItem> queue;

    // create user by giving name and contact number
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        queue = FXCollections.observableArrayList();
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public ObservableList<QueueItem> getQueue() {
        return queue;
    }
}
