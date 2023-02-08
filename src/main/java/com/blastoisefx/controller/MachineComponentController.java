package com.blastoisefx.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.blastoisefx.model.Machine;
import com.blastoisefx.model.QueueItem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MachineComponentController implements Initializable {
  @FXML
  private ListView<QueueItem> queueListView;

  @FXML
  private TitledPane titledPane;

  @FXML
  private VBox vbox;

  private Machine machine;


  public MachineComponentController(Machine machine) {
    this.machine = machine;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    titledPane.setText("Machine " + machine.getId());
    queueListView.setItems(machine.getQueue());
    queueListView.setCellFactory(param -> new ListCell<QueueItem>() {
      private final Label userEmailLabel = new Label();
      private final Label endTimeLabel = new Label();
      private final AnchorPane layout = new AnchorPane(userEmailLabel, endTimeLabel);

      {
        AnchorPane.setLeftAnchor(userEmailLabel, 2.0);
        AnchorPane.setRightAnchor(endTimeLabel, 2.0);
      }

      @Override
      protected void updateItem(QueueItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null || item.getUser() == null || item.getEndTime() == null) {
          userEmailLabel.setText(null);
          endTimeLabel.setText(null);
          setGraphic(null);
        } else {
          userEmailLabel.setText(item.getUser().getEmail());
          endTimeLabel.setText(String.valueOf(item.getWaitingTime()));
          setGraphic(layout);
        }
      }

    });

  }

  public void tick(LocalDateTime currentTime){
    machine.checkQueue(currentTime);
    queueListView.refresh();
  }
}