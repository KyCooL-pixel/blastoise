package com.blastoisefx.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
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
      private final HBox layout = new HBox(userEmailLabel, endTimeLabel);

      {
        layout.setSpacing(10);
      }

      @Override
      protected void updateItem(QueueItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null || item.getUser() == null || item.getEndTime() == null) {
          userEmailLabel.setText(null);
          endTimeLabel.setText(null);
          setGraphic(null);
        } else {
          System.out.println(item.getUser());
          System.out.println(item.getEndTime());
          userEmailLabel.setText(item.getUser().getEmail());
          endTimeLabel.setText(item.getEndTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)));
          setGraphic(layout);
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