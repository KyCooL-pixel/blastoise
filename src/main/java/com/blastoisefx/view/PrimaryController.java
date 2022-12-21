package com.blastoisefx.view;

import java.io.IOException;

import com.blastoisefx.App;

import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
