package com.blastoisefx.controller;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

import com.blastoisefx.App;
import com.blastoisefx.model.Machine;
import com.blastoisefx.model.QueueItem;
import com.blastoisefx.model.User;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ClientViewMachinesController implements Initializable {

  @FXML
  private Button backButton;

  @FXML
  private Button lockButton;

  @FXML
  private ListView<QueueItem> queuesListView;

  private Scene clientMenuScene;
  private User user;
  private HashMap<QueueItem, Machine> queueItemToMachine = new HashMap<>();

  public ClientViewMachinesController(Scene clientMenuScene, User user) {
    this.clientMenuScene = clientMenuScene;
    this.user = user;
    var queue = user.getQueue();

    queue.forEach(item -> {
      var machine = getMachineFromQueueItem(item);
      if (machine != null) {
        queueItemToMachine.put(item, machine);
      }
    });
    queue.addListener(new ListChangeListener<QueueItem>() {
      @Override
      public void onChanged(Change<? extends QueueItem> c) {
        while (c.next()) {

          for (var item : c.getAddedSubList()) {
            var machine = getMachineFromQueueItem(item);
            if (machine != null) {
              queueItemToMachine.put(item, machine);
            }
          }
          for (var item : c.getRemoved()) {
            queueItemToMachine.remove(item);
          }
        }
      }
    });
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    backButton.onActionProperty().set(e -> {
      Stage stage = (Stage) backButton.getScene().getWindow();
      stage.setScene(clientMenuScene);
    });

    lockButton.setOnAction(e -> {
      var item = queuesListView.selectionModelProperty().get().getSelectedItem();
      var machine = queueItemToMachine.get(item);
      if (machine.getStatus() == Machine.Status.WAITING) {
        machine.setStatus(Machine.Status.LOCKED);
      } else if (machine.getStatus() == Machine.Status.LOCKED) {
        machine.setStatus(Machine.Status.WAITING);
      }
    });

    queuesListView.onMouseClickedProperty().set(e -> {
      var item = queuesListView.selectionModelProperty().get().getSelectedItem();

      if (item == null) {
        lockButton.setDisable(true);
        return;
      }

      var machine = queueItemToMachine.get(item);

      if (machine == null) {
        lockButton.setDisable(true);
        return;
      }

      lockButton.setDisable(false);
      if (machine.getStatus() == Machine.Status.WAITING) {
        lockButton.setText("Lock");
      } else if (machine.getStatus() == Machine.Status.LOCKED) {
        lockButton.setText("Unlock");
      } else {
        lockButton.setDisable(true);
      }
    });
    queuesListView.setItems(user.getQueue());
    queuesListView.setCellFactory(param -> new ListCell<QueueItem>() {
      private final Label machineLabel = new Label();
      private final Label endTimeLabel = new Label();
      private final AnchorPane layout = new AnchorPane(machineLabel, endTimeLabel);

      {
        AnchorPane.setLeftAnchor(machineLabel, 2.0);
        AnchorPane.setRightAnchor(endTimeLabel, 2.0);
      }

      @Override
      protected void updateItem(QueueItem item, boolean empty) {
        super.updateItem(item, empty);
        var machine = queueItemToMachine.get(item);

        if (empty || item == null || item.getUser() == null || item.getOperationEndTime() == null) {
          machineLabel.setText(null);
          endTimeLabel.setText(null);
          setGraphic(null);
        } else {
          if (item.getState() == QueueItem.State.FINISHED) {
            if (machine != null && machine.getStatus() == Machine.Status.LOCKED) {
              layout.setStyle("-fx-background-color: #ffcbd1");
            } else if (machine != null && machine.getStatus() == Machine.Status.WAITING) {
              layout.setStyle("-fx-background-color: #BBE2FF");
            } else {
              layout.setStyle(null);
            }
          }

          machineLabel.setText(machine.getClass().getSimpleName() + " " + machine.getId());
          endTimeLabel.setText(String.valueOf(machine.getQueueItemWaitingTime(item)));
          setGraphic(layout);
        }
      }
    });

    final Timeline time = new Timeline(
        new KeyFrame(
            Duration.seconds(1),
            event -> {
              tick();
            }));
    time.setCycleCount(Animation.INDEFINITE);
    time.play();
  }

  public void tick() {
    queuesListView.refresh();
  }

  private Machine getMachineFromQueueItem(QueueItem item) {
    var machineTypes = App.getMachineTypes();
    for (var machineType : machineTypes) {
      var machines = machineType.getMachines();
      for (Machine machine : machines) {
        if (machine.getQueue().contains(item)) {
          return machine;
        }
      }
    }
    return null;
  }

}
