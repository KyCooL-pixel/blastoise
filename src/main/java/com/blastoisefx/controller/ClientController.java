package com.blastoisefx.controller;

import java.net.URL;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import com.blastoisefx.App;
import com.blastoisefx.model.Machine;
import com.blastoisefx.model.MachineType;
import com.blastoisefx.model.Payment;
import com.blastoisefx.model.QueueItem;
import com.blastoisefx.model.User;
import com.blastoisefx.model.Payment.Method;
import com.blastoisefx.utils.Message;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientController implements Initializable {
  @FXML
  private BorderPane root;

  @FXML
  private Label emailLabel;

  @FXML
  private Label title;

  @FXML
  private Label totalPrice;

  @FXML
  private FlowPane buttonsFlowPane;
  
  public Image image;

  private QueueController queueController;

  private Stage stage;

  private User user;

  public ClientController(QueueController queueController, User user) {
    this.user = user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    emailLabel.setText(user.getEmail());
    App.getMachineTypes().forEach(machineType -> {
      var button = new Button(machineType.getName());
      button.setOnAction(event -> {
        try {
          var duration = getDuration(machineType);
          var price = machineType.getPrice(duration);
          var paymentMethod = getPaymentMethod();

          machineType.addQueueItem(new QueueItem(
              user,
              new Payment(price, paymentPart(paymentMethod)),
              duration));
        } catch (NoSuchElementException e) {
          // Ignore as user clicks cancel
        } catch (NullPointerException | NumberFormatException e) {
          Message.showMessage("Error", e.getClass().getSimpleName(), "Please enter a valid number.");
        }
      });
      button.setPrefHeight(25);
      button.setPrefWidth(155);
      buttonsFlowPane.getChildren().add(0, button);
    });
  }

  public void setStage(Stage thisStage) {
    stage = thisStage;
  }

  private Method getPaymentMethod() throws NoSuchElementException {
    ChoiceDialog<Method> dialog = new ChoiceDialog<Method>(Method.ONLINE_BANKING, Method.values());
    dialog.setTitle("Payment");
    dialog.setHeaderText("Choose your preferred payment method");
    // dialog.getDialogPane().getChildren().add(new TextField("Hello world"))
    var result = dialog.showAndWait();
    return result.get();
  }

  private Method paymentPart(Method method) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Payment");
    alert.setHeaderText("Confirm your payment");
    totalPrice = new Label("Mock Price here");
    GridPane gridPane = new GridPane();
    gridPane.setVgap(5);
    gridPane.add(totalPrice, 0, 0);

    }
    else{
      gridPane.add(new Label("Phone Number"),0,1); 
      gridPane.add(new TextField(),1,1); 
      gridPane.add(new Label("PIN number"),0,2); 
      gridPane.add(new PasswordField(),1,2); 
    }
    alert.getDialogPane().setContent(gridPane);
    alert.showAndWait();
    return method;
  }

  private int getDuration(MachineType<? extends Machine> machineType) {
    TextInputDialog dialog = new TextInputDialog("0");
    dialog.setTitle("Add on duration for laundry");
    dialog.setHeaderText(String.format("The minimum duration required is %d seconds Enter extra duration if you require extra time for your laundry, if not just press OK", machineType.getDefaultDuration()));
    dialog.setContentText("Please enter any add on duration for your laundry (in seconds):");
    var result = dialog.showAndWait();
    return machineType.getDefaultDuration() +  Integer.parseInt(result.get());
  }

  public void showMessage(String head, String body, String text) {
    Message.showMessage(head, body, text);
  }

}
