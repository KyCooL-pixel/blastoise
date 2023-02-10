package com.blastoisefx.controller;

import java.io.IOException;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class PaymentController {
    @FXML
    private ComboBox<String> paymentBox;
    @FXML
    private Pane creditCardPane;
    @FXML
    private ImageView eWalletQR;
    @FXML
    private Button doneButton;

    @FXML
    private void initialize() {
        paymentBox.getItems().addAll("Credit Card",
                "E-Wallet");
    }
    @FXML
    private void itemSelectedChanged() throws IOException {
        if (paymentBox.getSelectionModel().getSelectedIndex() == 0) {
            eWalletQR.setVisible(false);
            creditCardPane.setVisible(true);
        } else {
            eWalletQR.setVisible(true);
            creditCardPane.setVisible(false);
        }

    }
}
