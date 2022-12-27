package com.blastoisefx;

import java.io.IOException;

import javafx.fxml.FXML;

public class MainController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}