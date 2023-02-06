package com.blastoisefx.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.Initializable;

import com.blastoisefx.model.Machine;
import com.blastoisefx.model.QueueItem;

public class QueueComponentController implements Initializable {
  @FXML
  private Label currentQueueItemDurationField;

  @FXML
  private Label queueItemCountField;

  @FXML
  private Label title;

  @FXML
  private Label waitingTimeField;

  @FXML
  private ListView<QueueItem> listView;

  private Machine machine;

  public QueueComponentController(Machine machine) {
    this.machine = machine;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    listView.setItems(machine.getQueue());
    listView.setCellFactory(param -> new ListCell<QueueItem>() {
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
          setStyle("-fx-background-color: blue");
        }
      }
    });

    tick();
  }

  public void tick() {
    queueItemCountField.setText(String.valueOf(machine.getQueue().size()));

    int duration = 0;
    var queue = machine.getQueue();
    if (queue.size() > 0) {
      duration = queue.get(0).getDuration();
    }
    currentQueueItemDurationField.setText(String.valueOf(duration));

    machine.checkQueue(LocalDateTime.now());
  }

}