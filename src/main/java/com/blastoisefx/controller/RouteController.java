package com.blastoisefx.controller;

import com.blastoisefx.App;

import java.io.IOException;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RouteController {

    @FXML
    private void toLogin(MouseEvent event) throws IOException {
        Object source = event.getSource();
        if (source instanceof Node) {
            Node control = (Node) source;
            FXMLLoader loader = App.getFXMLLoader("auth");
            loader.setController(new AuthController());
            control.getScene().setRoot(loader.load());
        }
    }
}