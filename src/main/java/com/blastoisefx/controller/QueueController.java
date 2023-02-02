package com.blastoisefx.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

import com.blastoisefx.App;
import com.blastoisefx.model.Dryer;
import com.blastoisefx.model.Payment;
import com.blastoisefx.model.QueueItem;
import com.blastoisefx.model.Washer;
import com.blastoisefx.utils.Message;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class QueueController {
    // Initiate a queue here
    private Queue<QueueItem> washQueue = new LinkedList<QueueItem>();
    private Queue<QueueItem> dryQueue = new LinkedList<QueueItem>();

    // declare an elapsed variable here that will let the queuItem know it should be
    // ended
    // once it reaches the duration
    private int elapsedSecondsWash = 0;
    private int elapsedSecondsDry = 0;

    // set a queue length variable once user pressed queue
    private int washQueueLengthOnceQueued;
    private int dryQueueLengthOnceQueued;
    // these two need to take from model
    private Integer washSeconds = 1;
    private Integer drySeconds = 1;

    // this keep track if user is already queueing or not
    private boolean isQueuedWash = false;
    private boolean isQueuedDry = false;
    @FXML
    private Label LocalTimeLabel;

    @FXML
    private Button washQueueBtn;
    @FXML
    private Label washAvailable;
    @FXML
    private Label washLength;
    @FXML
    private Button rWashQueueBtn;

    @FXML
    private Button dryQueueBtn;
    @FXML
    private Label dryAvailable;
    @FXML
    private Label dryLength;
    @FXML
    private Button rDryQueueBtn;

    // Shares timeline
    Timeline washTime = new Timeline();
    Timeline dryTime = new Timeline();

    @FXML
    public void initialize() {
        // mock data
        LocalTimeShow();
        washQLengthShow();
        dryQLengthShow();
        washSeconds += washComputeTime();
        drySeconds += dryComputeTime();
        doTimeWash();
        doTimeDry();
    }

    @FXML
    private void LocalTimeShow() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                LocalTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                if (LocalTimeLabel.getText().equals(washAvailable.getText())) {
                    washAvailable.setText("NOW");
                    Message.showMessage("Queue for Wash Completed", "It's your turn now.",
                            "Go to My Machines for next step");
                    washAvailable.setText("--------------------");
                    addMachines(washQueue.poll());
                    washQueue.clear();
                    isQueuedWash = false;
                    washQLengthShow();
                    // washLength.setText(0 + "");
                }
                if (LocalTimeLabel.getText().equals(dryAvailable.getText())) {
                    dryAvailable.setText("NOW");
                    Message.showMessage("Queue for Dry Completed", "It's your turn now.",
                            "Go to My Machines for next step");
                    dryAvailable.setText("--------------------");
                    addMachines(dryQueue.poll());
                    dryQueue.clear();
                    isQueuedDry = false;
                    dryQLengthShow();
                    // dryLength.setText(0 + "");
                }
            }

        };
        timer.start();
    }

    @FXML
    private void washETATimeShow() {
        washAvailable.setText(
                LocalDateTime.now().plusSeconds(washComputeTime()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @FXML
    private void dryETATimeShow() {
        dryAvailable.setText(
                LocalDateTime.now().plusSeconds(dryComputeTime()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @FXML
    private void washQLengthShow() {
        if (!isQueuedWash)
            washLength.setText(washQueue.size() + "");
        else
            washLength.setText(Integer.toString(dryQueueLengthOnceQueued));
    }

    @FXML
    private void dryQLengthShow() {
        if (!isQueuedDry)
            dryLength.setText(dryQueue.size() + "");
        else
            dryLength.setText(Integer.toString(washQueueLengthOnceQueued));
    }

    // This method does the count down stuff
    @FXML
    private void doTimeWash() {
        // Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (washSeconds <= 1) {
                    washTime.stop();
                }
                elapsedSecondsWash++;
                washSeconds--;
                if (washQueue.peek() != null && elapsedSecondsWash == washQueue.peek().getDuration()) {
                    elapsedSecondsWash = 0;
                    washQueue.poll();
                    if (washQueueLengthOnceQueued > 0)
                        washQueueLengthOnceQueued--;
                    washQLengthShow();
                }
            }
        });
        washTime.setCycleCount(Timeline.INDEFINITE);
        washTime.getKeyFrames().add(frame);
        if (washTime != null) {
            washTime.stop();
        }
        washTime.play();
    }

    // This method does the count down stuff
    @FXML
    private void doTimeDry() {
        // Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (drySeconds <= 1) {
                    dryTime.stop();
                }
                elapsedSecondsDry++;
                drySeconds--;
                if (dryQueue.peek() != null && elapsedSecondsDry == dryQueue.peek().getDuration()) {
                    elapsedSecondsDry = 0;
                    dryQueue.poll();
                    if (dryQueueLengthOnceQueued > 0)
                        dryQueueLengthOnceQueued--;
                    dryQLengthShow();
                }
            }
        });
        dryTime.setCycleCount(Timeline.INDEFINITE);
        dryTime.getKeyFrames().add(frame);
        if (dryTime != null) {
            dryTime.stop();
        }
        dryTime.play();
    }

    @FXML
    private int washComputeTime() {
        int temp = 0;
        for (QueueItem qItem : washQueue) {
            temp += qItem.getDuration();
        }
        temp -= 10;
        return temp;
    }

    @FXML
    private int dryComputeTime() {
        int temp = 0;
        for (QueueItem qItem : dryQueue) {
            temp += qItem.getDuration();
        }
        temp -= 10;
        return temp;
    }

    private String formatTime(int i) {
        String time = String.format("%02d:%02d", i / 60, i % 60);
        return time;
    }

    @FXML
    private void washCreateQueueItem() {
        if (isQueuedWash) {
            Message.showMessage("Error", "Already Queued", "You are currently in queue already");
        } else if (washQueue.size() == 0) {
            washAvailable.setText("NOW");
            Message.showMessage("Queue Completed", "It's your turn now.",
                    "Go to next page for payment confirmation");
            addMachines(new QueueItem(App.getCurrUser(), new Payment(0, null), new Washer()));
            washAvailable.setText("----------------");
        } else {
            washQueueLengthOnceQueued = washQueue.size();
            QueueItem newItem = new QueueItem(App.getCurrUser(), new Payment(0, null), new Washer());
            washQueue.add(newItem);
            washETATimeShow();
            isQueuedWash = true;
        }
    }

    @FXML
    private void washMockQueue() {
        QueueItem newItem = new QueueItem(App.getCurrUser(), new Payment(0, null), new Washer());
        washQueue.add(newItem);
        if (!isQueuedWash) {
            // add 1 to make up for the keyframe lag
            washSeconds += newItem.getDuration() + 1;
            if (washTime.getStatus() == Status.STOPPED)
                washTime.play();
            washQLengthShow();
        }
    }

    @FXML
    private void dryCreateQueueItem() {
        if (isQueuedDry) {
            Message.showMessage("Error", "Already Queued", "You are currently in queue already");
        } else if (dryQueue.size() == 0) {
            dryAvailable.setText("NOW");
            Message.showMessage("Queue Completed", "It's your turn now.",
                    "Go to next page for payment confirmation");
            addMachines(new QueueItem(App.getCurrUser(), new Payment(0, null), new Dryer()));
            dryAvailable.setText("----------------");
        } else {
            dryQueueLengthOnceQueued = dryQueue.size();
            QueueItem newItem = new QueueItem(App.getCurrUser(), new Payment(0, null), new Dryer());
            dryQueue.add(newItem);
            dryETATimeShow();
            isQueuedDry = true;
        }
    }

    @FXML
    private void dryMockQueue() {
        QueueItem newItem = new QueueItem(App.getCurrUser(), new Payment(0, null), new Dryer());
        dryQueue.add(newItem);
        if (!isQueuedDry) {
            // add 1 to make up for the keyframe lag
            drySeconds += newItem.getDuration() + 1;
            if (dryTime.getStatus() == Status.STOPPED)
                dryTime.play();
            dryQLengthShow();
        }
    }

    @FXML
    private void backToMainMenu() throws IOException {
        App.setRoot("menu",340,400);
    }

    @FXML
    private void toMyMachines() throws IOException {
        App.setRoot("myMachines",565,420);
    }

    private void addMachines(QueueItem qItem) {
        App.getMachines().add(qItem.getMachine());
    }
}
