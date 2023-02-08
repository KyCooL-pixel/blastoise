package com.blastoisefx.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.blastoisefx.App;
import com.blastoisefx.model.Payment;
import com.blastoisefx.model.QueueItem;
import com.blastoisefx.model.User;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QueueController implements Initializable {
    final double MILLISECONDS_PER_FRAME_IN_30_FPS = Math.ceil(1000 / 30);

    // normal fxml stuff
    // @FXML
    // private Label countDownLabel;
    @FXML
    private Label localTimeLabel;

    @FXML
    private Button addClientButton;

    @FXML
    private GridPane queueComponentPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var machineTypes = App.getMachineTypes();

        ArrayList<QueueComponentController> queueComponentControllers = new ArrayList<>();

        for (int i = 0; i < machineTypes.size(); i++) {
            FXMLLoader loader = App.getFXMLLoader("queueComponent");
            QueueComponentController controller = new QueueComponentController(machineTypes.get(i));
            loader.setController(controller);
            queueComponentControllers.add(controller);
            try {
                queueComponentPane.add(loader.load(), i, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final Timeline time = new Timeline(
                new KeyFrame(
                        Duration.millis(MILLISECONDS_PER_FRAME_IN_30_FPS),
                        event -> {
                            setCurrentTime();
                            queueComponentControllers.forEach(QueueComponentController::tick);
                        }));
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }

    private void setCurrentTime() {
        localTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @FXML
    private void addClient() throws IOException {
        Stage clientStage = new Stage();
        FXMLLoader loader = App.getFXMLLoader("auth");
        ClientController clientController = new ClientController(this, null);

        loader.setController(new AuthController(clientController));
        Scene main = new Scene(loader.load(), 335, 600);
        clientStage.setScene(main);
        clientStage.show();
    }

    public void addWasherQueue(User user, Payment payment, int duration) {
        App.getMachineTypes().get(0).addQueueItem(new QueueItem(user, payment, duration));;
    }


}
