package com.blastoisefx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.blastoisefx.App;
import com.blastoisefx.model.Payment;
import com.blastoisefx.model.User;
import com.blastoisefx.model.Washer;
import com.blastoisefx.model.Payment.Method;
import com.blastoisefx.utils.Message;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
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

  public User getUser(){
    return user;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    emailLabel.setText(user.getEmail());
  }

  @FXML
  void addWasherQueue(ActionEvent event) throws IOException {
    // FXMLLoader loader = App.getFXMLLoader("payment");
    // Scene paymentScene = new Scene(loader.load(), 335, 600);
    // stage.setScene(paymentScene);

    var duration = getDuration();
    var price = duration * 123123;

    queueController.addWasherQueue(
        user,
        new Payment(price, getPaymentMethod()),
        50);
  }

  public void setStage(Stage thisStage) {
    stage = thisStage;
  }

  private Method getPaymentMethod() {
    ChoiceDialog<Method> dialog = new ChoiceDialog<Method>(Method.ONLINE_BANKING, Method.values());
    dialog.setTitle("Payment");
    dialog.setHeaderText("Choose your preferred payment method");
    var result = dialog.showAndWait();
    while (!result.isPresent()) {
      result = dialog.showAndWait();
    }
    return result.get();
  }

  private double getDuration() {
    TextInputDialog dialog = new TextInputDialog("0.0");
    dialog.setTitle("Extra duration for laundry");
    dialog.setHeaderText("Enter extra duration if you require extra time for your laundry, if not just press OK");
    dialog.setContentText("Please enter any extra duration for your laundry (in seconds):");
    var result = dialog.showAndWait();
    try {
      while (!result.isPresent() || Double.parseDouble(result.get()) < 0) {
        result = dialog.showAndWait();
      }
    } catch (NumberFormatException e) {
      result = dialog.showAndWait();
    }
    return Double.parseDouble(result.get());
  }

  public void showMessage(String head, String body, String text){
    Message.showMessage(head,body,text);
  }

}
