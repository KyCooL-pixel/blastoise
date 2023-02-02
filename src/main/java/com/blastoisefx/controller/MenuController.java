package com.blastoisefx.controller;

import java.io.IOException;

import com.blastoisefx.App;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {
    @FXML
    private Button toQueueBtn;

    @FXML
    private void toQueue() throws IOException{
        App.setRoot("queue");
    }

    @FXML
    private void toMachines() throws IOException{
        App.setRoot("myMachines");
    }

    @FXML
    private void toNotifications() throws IOException{
        App.setRoot("notification");
    }
}
