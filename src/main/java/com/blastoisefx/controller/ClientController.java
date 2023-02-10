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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

  @FXML
  private Label totalPrice;

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

    try {
      var duration = getDuration();
      var price = duration * 123123;
      var paymentMethod = getPaymentMethod();

    queueController.addWasherQueue(
        user,
        new Payment(price, paymentPart(getPaymentMethod())),
        50);
    } catch (NoSuchElementException e) {
      // Ignore as user clicks cancel
    } catch (NullPointerException | NumberFormatException e) {
      Message.showMessage("Error", e.getClass().getSimpleName(), "Please enter a valid number.");
    }
  }


  public void setStage(Stage thisStage) {
    stage = thisStage;
  }

  private Method getPaymentMethod() throws NoSuchElementException {
    ChoiceDialog<Method> dialog = new ChoiceDialog<Method>(Method.ONLINE_BANKING, Method.values());
    dialog.setTitle("Payment");
    dialog.setHeaderText("Choose your preferred payment method");
    //dialog.getDialogPane().getChildren().add(new TextField("Hello world"))
    var result = dialog.showAndWait();
    return result.get();
  }

  private Method paymentPart(Method method){
    Alert alert = new Alert(AlertType.CONFIRMATION);
    totalPrice = new Label("Mock Price here");
    GridPane gridPane = new GridPane();
    gridPane.setVgap(5);
    gridPane.add(totalPrice,0,0);
    
    if(method == Method.ONLINE_BANKING){
      gridPane.add(new Label("User Name"),0,1); 
      gridPane.add(new TextField(),1,1); 
      gridPane.add(new Label("User Password"),0,2); 
      gridPane.add(new PasswordField(),1,2); 

    }
    else{
      Image image = new Image("/qrCode.png");
      ImageView qrImageView = new ImageView(image);
      gridPane.getChildren().add(qrImageView);
    }
    alert.getDialogPane().setContent(gridPane);
    alert.showAndWait();
    return method;
  }

  private double getDuration() {
    TextInputDialog dialog = new TextInputDialog("0.0");
    dialog.setTitle("Extra duration for laundry");
    dialog.setHeaderText("Enter extra duration if you require extra time for your laundry, if not just press OK");
    dialog.setContentText("Please enter any extra duration for your laundry (in seconds):");
    var result = dialog.showAndWait();
    return Double.parseDouble(result.get());
  }

  public void showMessage(String head, String body, String text){
    Message.showMessage(head,body,text);
  }

}
