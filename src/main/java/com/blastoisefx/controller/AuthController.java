package com.blastoisefx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.blastoisefx.App;
import com.blastoisefx.model.Auth;
import com.blastoisefx.model.User;
import com.blastoisefx.utils.Message;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AuthController implements Initializable{
    @FXML
    private TextField emailValue;
    @FXML
    private TextField passwordValue;
    @FXML
    private Label signInLabel;

    private ClientController clientController;

    public AuthController() {
    }

    public AuthController(ClientController clientController) {
        this.clientController = clientController;
    }

    public void initialize(URL location, ResourceBundle resources) {
        emailValue.setText("me@me.com");
        passwordValue.setText("me@me.com");
    }

    @FXML
    private void Login(MouseEvent event) throws IOException {
        String email = emailValue.getText();
        String password = passwordValue.getText();
        User currUser = Auth.validate(email, password);

        if (currUser == null) {
            Message.showMessage("ERROR", "WRONG CREDENTIALS", "Please enter correct info or sign up if new user");
            return;
        }

        Object source = event.getSource();
        if (!(source instanceof Control)) {
            Message.showMessage("ERROR", "ERROR", "Something went wrong");
            return;
        }

        if (clientController != null) {
            clientController.setUser(currUser);

            Control control = (Control) source;
            FXMLLoader loader = App.getFXMLLoader("client");
            loader.setController(clientController);
            control.getScene().setRoot(loader.load());
            return;
        }

        App.setCurrUser(currUser);
        App.setRoot("queue",710, 530);
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

    private void clearResetEmail() {
        emailValue.clear();
        emailValue.setPromptText("Please enter a valid email*");
        emailValue.setStyle("-fx-prompt-text-fill: #f50c0c");
    }

    private void clearResetPassword() {
        passwordValue.clear();
        passwordValue.setPromptText("Password must be at least 8 characters long*");
        passwordValue.setStyle("-fx-prompt-text-fill: #f50c0c");
    }

}