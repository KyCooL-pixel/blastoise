package com.blastoisefx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.blastoisefx.model.Payment;
import com.blastoisefx.model.User;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ClientController implements Initializable {
  @FXML
  private BorderPane root;

  @FXML
  private Button addWasherQueueButton;

  @FXML
  private Label emailLabel;


  @FXML
  private Label title;

  private QueueController queueController;

  private Stage stage;

  private User user;

  public ClientController(QueueController queueController, User user) {
    this.queueController = queueController;
    this.user = user;
  }


  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    emailLabel.setText(user.getEmail());
  }

  @FXML
  void addWasherQueue(ActionEvent event) {
    stage.setScene(null);
    queueController.addWasherQueue(
      user,
      new Payment(50, Payment.Method.ONLINE_BANKING),
      50
    );
  }

  public void setStage(Stage thisStage){
    stage = thisStage;
}



}

