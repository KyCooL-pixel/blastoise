package com.blastoisefx;

import java.io.IOException;

import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void toLogin() throws IOException {
        App.setRoot("login");
    }
    @FXML
    private void toSignUp() throws IOException {
        App.setRoot("signup");
    }
}