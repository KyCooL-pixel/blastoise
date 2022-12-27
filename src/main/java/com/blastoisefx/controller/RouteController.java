package com.blastoisefx.controller;

import java.io.IOException;

import com.blastoisefx.App;

import javafx.fxml.FXML;

public class RouteController {
    @FXML
    private void toLogin() throws IOException {
        App.setRoot("auth");
    }
}