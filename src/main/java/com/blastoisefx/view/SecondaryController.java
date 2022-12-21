package com.blastoisefx.view;

import java.io.IOException;

import com.blastoisefx.App;

import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}