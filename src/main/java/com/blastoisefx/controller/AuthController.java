package com.blastoisefx.controller;

import java.io.IOException;

import com.blastoisefx.App;
import com.blastoisefx.model.Auth;
import com.blastoisefx.utils.Message;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;

public class AuthController {
    @FXML
    private TextField emailValue;
    @FXML
    private TextField passwordValue;

    @FXML
    private void Login() throws IOException {
        String email = emailValue.getText();
        String password = passwordValue.getText();
        if (Auth.validate(email, password))
            App.setRoot("example");
        else {
            Message.showMessage("ERROR", "WRONG CREDENTIALS", "Please enter correct info or sign up if new user");
        }
    }

    @FXML
    private void SignUp() throws IOException {
        String email = emailValue.getText();
        String password = passwordValue.getText();
        switch(Auth.addNewUser(email, password)){
            case 0:
            Message.showMessage("SUCCESS","NEW USER ADDED", "New user : "+ email);break;
            case 1:
            emailValue.clear();
            emailValue.setPromptText("Please enter a valid email");break;
            case 2:
            passwordValue.clear();
            passwordValue.setPromptText("Password must be at least 8 characters long");break;
            case 3:
            emailValue.clear();
            emailValue.setPromptText("Please enter a valid email");
            passwordValue.clear();
            passwordValue.setPromptText("Password must be at least 8 characters long");
        }
    }

}