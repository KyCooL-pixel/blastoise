package com.blastoisefx.controller;

import java.io.IOException;

import com.blastoisefx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NotificationController {
    @FXML
    private Button backButton;
    @FXML
    private void backToMenu() throws IOException{
        App.setRoot("menu",340,400);
    }
}
