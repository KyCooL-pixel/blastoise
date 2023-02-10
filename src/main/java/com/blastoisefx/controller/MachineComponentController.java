package com.blastoisefx.controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import com.blastoisefx.App;
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
        if (empty || item == null || item.getUser() == null || item.getOperationEndTime() == null) {
          userEmailLabel.setText(null);
          endTimeLabel.setText(null);
          setGraphic(null);
        } else {
          layout.setStyle(null);
          if(getIndex() == 0 && machine.getStatus() == Machine.Status.LOCKED) {
            layout.setStyle("-fx-background-color: #ffcbd1");
          }
          userEmailLabel.setText(item.getUser().getEmail());
          var endTime = machine.getQueueItemWaitingTime(getIndex());
          endTimeLabel.setText(String.valueOf(endTime < 0 ? 0 : endTime));
          setGraphic(layout);
        }
      }

    });

  }

  public void tick(LocalDateTime currentTime) {
    QueueItem q = machine.checkQueue(currentTime);
    if (q != null) {
      for (ClientController c : App.getClientControllers()) {
        if (c.getUser() != q.getUser()) {
          continue;
        }

        if (machine.getStatus() == Machine.Status.LOCKED) {
          // Finished Washing
          c.showMessage("Notification", "Machine Operation Finished", "Please remove your clothes from the machine before the lock is released");
        } else {
          // Unlocked
          c.showMessage("Notificatrion", "Machine Unlocked", "The machine is now unlocked");
        }
      }
    }
    queueListView.refresh();
  }
}
