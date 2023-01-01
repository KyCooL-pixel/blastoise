package com.blastoisefx.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Message {
    public static void showMessage(String title,String header, String Content){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(Content);
        alert.show();
    }
}
