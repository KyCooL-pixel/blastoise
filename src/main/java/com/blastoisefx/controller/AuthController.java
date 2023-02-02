package com.blastoisefx.controller;

import java.io.IOException;

import com.blastoisefx.App;
import com.blastoisefx.model.Auth;
import com.blastoisefx.model.User;
import com.blastoisefx.utils.Message;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuthController {
    @FXML
    private TextField emailValue;
    @FXML
    private TextField passwordValue;
    @FXML
    private Label signInLabel;

    @FXML
    private void Login() throws IOException {
        String email = emailValue.getText();
        String password = passwordValue.getText();
        User currUser = Auth.validate(email, password);
        if (currUser != null) {
            App.setCurrUser(currUser);
            App.setRoot("menu",340,400);
        } else {
            Message.showMessage("ERROR", "WRONG CREDENTIALS", "Please enter correct info or sign up if new user");
        }
    }

    @FXML
    private void SignUp() throws IOException {
        String email = emailValue.getText();
        String password = passwordValue.getText();

        switch (Auth.addNewUser(email, password)) {
            case SUCCESS:
                Message.showMessage("SUCCESS", "NEW USER ADDED", "New user : " + email);
                break;
            case INVALID_EMAIL:
                clearResetEmail();
                break;
            case INVALID_PASSWORD:
                clearResetPassword();
                break;
            case INVALID_EMAIL_AND_PASSWORD:
                clearResetEmail();
                clearResetPassword();
        }
    }

    private void clearResetEmail(){
        emailValue.clear();
        emailValue.setPromptText("Please enter a valid email*");
        emailValue.setStyle("-fx-prompt-text-fill: #f50c0c");
    }

    private void clearResetPassword(){
        passwordValue.clear();
        passwordValue.setPromptText("Password must be at least 8 characters long*");
        passwordValue.setStyle("-fx-prompt-text-fill: #f50c0c");
    }

}