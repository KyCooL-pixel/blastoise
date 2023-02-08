package com.blastoisefx.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.fxml.Initializable;

import com.blastoisefx.App;
import com.blastoisefx.model.Machine;
import com.blastoisefx.model.MachineType;

public class QueueComponentController implements Initializable {
  @FXML
  private Label addOnPriceLabel;

  @FXML
  private Label basePriceLabel;

  @FXML
  private FlowPane machinesFlowPane;

  // @FXML
  // private VBox machinesVBox;

  @FXML
  private Label titleLabel;

  private MachineType<? extends Machine> machineType;
  ArrayList<MachineComponentController> machineControllers = new ArrayList<>();

  public QueueComponentController(MachineType<? extends Machine> machineType) {
    this.machineType = machineType;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    titleLabel.setText(machineType.getName());
    var basePrice =  String.format("RM %.2f", machineType.getBasePrice());
    var addOnPrice =  String.format("RM %.2f", machineType.getAddOnPrice());
    basePriceLabel.setText(basePrice);
    addOnPriceLabel.setText(addOnPrice);

    for (Machine machine : machineType.getMachines()) {
      FXMLLoader loader = App.getFXMLLoader("machineComponent");
      var controller = new MachineComponentController(machine);
      loader.setController(controller);
      machineControllers.add(controller);
      try {
        machinesFlowPane.getChildren().add(loader.load());
      } catch (Exception e) {
        System.out.println("Error loading machine component");
      }
    }

  }

  public void tick() {
    LocalDateTime currentTime = LocalDateTime.now();
    for (MachineComponentController controller : machineControllers) {
      controller.tick(currentTime);
    }
  }
}