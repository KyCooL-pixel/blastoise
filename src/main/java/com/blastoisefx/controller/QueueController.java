package com.blastoisefx.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;

import com.blastoisefx.App;
import com.blastoisefx.model.Payment;
import com.blastoisefx.model.QueueItem;
import com.blastoisefx.model.User;
import com.blastoisefx.utils.Message;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    // Initiate a queue here
    private Queue<QueueItem> washQueue = new LinkedList<QueueItem>();

    // declare an elapsed variable here that will let the queuItem know it should be
    // ended
    // once it reaches the duration
    private int elapsedSeconds = 0;

    // set a queue length variable once user pressed queue
    private int queueLengthOnceQueued;
    // these two need to take from model
    private Integer seconds = 1;

    // this keep track if user is already queueing or not
    private boolean isQueued = false;

    // normal fxml stuff
    // @FXML
    // private Label countDownLabel;
    @FXML
    private Label localTimeLabel;

    @FXML
    private Button addClientButton;

    @FXML
    private GridPane queueComponentPane;
    // private Button addTimeButton;
    // @FXML
    // private Button createQueueItemBtn;
    // @FXML
    // private Label ETALabel;
    // @FXML
    // private Label qLength;
    // @FXML
    // private Button mockQueueBtn;

    // Shares timeline
    // Timeline time = new Timeline();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var machineTypes = App.getMachineTypes();

        QueueComponentController[] queueComponentControllers = new QueueComponentController[machineTypes.size()];

        // convert the following to for loop
        // machineTypes.forEach(machineType -> {
        // FXMLLoader loader = App.getFXMLLoader("queueComponent");
        // QueueComponentController controller = new QueueComponentController(machineType);
        // loader.setController(controller);
        // try {
        // queueComponentPane.add(loader.load(), 0, machineTypes.indexOf(machineType));
        // } catch (IOException e) {
        // e.printStackTrace();
        // }
        // });
        for (int i = 0; i < machineTypes.size(); i++) {
            FXMLLoader loader = App.getFXMLLoader("queueComponent");
            QueueComponentController controller = new QueueComponentController(machineTypes.get(i));
            loader.setController(controller);
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

    // @FXML
    // private void localTimeShow() {
    // AnimationTimer timer = new AnimationTimer() {
    // @Override
    // public void handle(long now) {
    // localTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    // if (localTimeLabel.getText().equals(ETALabel.getText())) {
    // ETALabel.setText("NOW");
    // Message.showMessage("Queue Completed", "It's your turn now.",
    // "Go to next page for payment confirmation");
    // washQueue.poll();
    // isQueued = false;
    // }
    // }

    // };
    // timer.start();
    // }

    // @FXML
    // private void ETATimeShow() {
    // ETALabel.setText(
    // LocalDateTime.now().plusSeconds(computeTime()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    // }

    // @FXML
    // private void qLengthShow() {
    // if (!isQueued)
    // qLength.setText(washQueue.size() + "");
    // else
    // qLength.setText(Integer.toString(queueLengthOnceQueued));
    // }

    // // This method does the count down stuff
    // @FXML
    // private void doTime() {
    // // Timeline time = new Timeline();
    // KeyFrame frame = new KeyFrame(Duration.seconds(1), new
    // EventHandler<ActionEvent>() {
    // @Override
    // public void handle(ActionEvent event) {
    // if (seconds <= 1) {
    // time.stop();
    // }
    // elapsedSeconds++;
    // seconds--;
    // countDownLabel.setText(formatTime(seconds));
    // if (washQueue.peek() != null && elapsedSeconds ==
    // washQueue.peek().getDuration()) {
    // washQueue.poll();
    // elapsedSeconds = 0;
    // if(queueLengthOnceQueued>0)
    // queueLengthOnceQueued--;
    // qLengthShow();
    // }
    // }
    // });
    // time.setCycleCount(Timeline.INDEFINITE);
    // time.getKeyFrames().add(frame);
    // if (time != null) {
    // time.stop();
    // }
    // time.play();
    // }

    // @FXML
    // private void addTime() {
    // time.stop();
    // seconds += 5;
    // time.play();
    // }

    // @FXML
    // private int computeTime() {
    // int temp = 0;
    // for (QueueItem qItem : washQueue) {
    // temp += qItem.getDuration();
    // }
    // return temp;
    // }

    // private String formatTime(int i) {
    // String time = String.format("%02d:%02d", i / 60, i % 60);
    // return time;
    // }

    // @FXML
    // private void createQueueItem() {
    // if (isQueued) {
    // Message.showMessage("Error", "Already Queued", "You are currently in queue
    // already");
    // }
    // else if (washQueue.size()==0){
    // ETALabel.setText("NOW");
    // Message.showMessage("Queue Completed", "It's your turn now.",
    // "Go to next page for payment confirmation");
    // }
    // else {
    // // TODO change to dynamic input here : line 147
    // queueLengthOnceQueued = washQueue.size();
    // QueueItem newItem = new QueueItem(App.getCurrUser(), LocalDateTime.now(), new
    // WashMachine());
    // washQueue.add(newItem);
    // ETATimeShow();
    // isQueued = true;
    // }
    // }

    // @FXML
    // private void mockQueue() {
    // QueueItem newItem = new QueueItem(App.getCurrUser(), LocalDateTime.now(), new
    // WashMachine());
    // washQueue.add(newItem);
    // if (!isQueued) {
    // // add 1 to make up for the keyframe lag
    // seconds += newItem.getDuration() + 1;
    // if (time.getStatus() == Status.STOPPED)
    // time.play();
    // qLengthShow();
    // }

    // }
}
