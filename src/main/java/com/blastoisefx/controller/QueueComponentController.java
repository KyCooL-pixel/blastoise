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
import com.blastoisefx.model.MachineType;
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

  private MachineType<? extends Machine> machineType;

  ObservableList<QueueItem> items;

  public QueueComponentController(MachineType<? extends Machine> machineType) {
    this.machineType = machineType;
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    items = FXCollections.observableList(machineType.getQueue());
    listView.setItems(items);
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

    title.setText(machineType.getName());
    tick();
  }

  public void tick() {
    queueItemCountField.setText(String.valueOf(machineType.getQueue().size()));
    waitingTimeField.setText(String.valueOf(machineType.getDuration()));

    int duration = 0;
    var queue = machineType.getQueue();
    if (queue.size() > 0) {
      duration = queue.get(0).getDuration();
    }
    currentQueueItemDurationField.setText(String.valueOf(duration));
  }

}