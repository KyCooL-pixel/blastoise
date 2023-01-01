package com.blastoisefx.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.Queue;

import com.blastoisefx.App;
import com.blastoisefx.model.QueueItem;
import com.blastoisefx.model.WashMachine;
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
    @FXML
    private Label countDownLabel;
    @FXML
    private Label localTimeLabel;
    @FXML
    private Button addTimeButton;
    @FXML
    private Button createQueueItemBtn;
    @FXML
    private Label ETALabel;
    @FXML
    private Label qLength;
    @FXML
    private Button mockQueueBtn;

    // Shares timeline
    Timeline time = new Timeline();

    @FXML
    public void initialize() {
        // mock data

        localTimeShow();
        qLengthShow();
        seconds += computeTime();
        doTime();
    }

    @FXML
    private void localTimeShow() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                localTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
                if (localTimeLabel.getText().equals(ETALabel.getText())) {
                    ETALabel.setText("NOW");
                    Message.showMessage("Queue Completed", "It's your turn now.",
                            "Go to next page for payment confirmation");
                    washQueue.poll();
                    isQueued = false;
                }
            }

        };
        timer.start();
    }

    @FXML
    private void ETATimeShow() {
        ETALabel.setText(
                LocalDateTime.now().plusSeconds(computeTime()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @FXML
    private void qLengthShow() {
        if (!isQueued)
            qLength.setText(washQueue.size() + "");
        else
            qLength.setText(Integer.toString(queueLengthOnceQueued));
    }

    // This method does the count down stuff
    @FXML
    private void doTime() {
        // Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (seconds <= 1) {
                    time.stop();
                }
                elapsedSeconds++;
                seconds--;
                countDownLabel.setText(formatTime(seconds));
                if (washQueue.peek() != null && elapsedSeconds == washQueue.peek().getDuration()) {
                    washQueue.poll();
                    elapsedSeconds = 0;
                    if(queueLengthOnceQueued>0)
                        queueLengthOnceQueued--;
                    qLengthShow();
                }
            }
        });
        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if (time != null) {
            time.stop();
        }
        time.play();
    }

    @FXML
    private void addTime() {
        time.stop();
        seconds += 5;
        time.play();
    }

    @FXML
    private int computeTime() {
        int temp = 0;
        for (QueueItem qItem : washQueue) {
            temp += qItem.getDuration();
        }
        return temp;
    }

    private String formatTime(int i) {
        String time = String.format("%02d:%02d", i / 60, i % 60);
        return time;
    }

    @FXML
    private void createQueueItem() {
        if (isQueued) {
            Message.showMessage("Error", "Already Queued", "You are currently in queue already");
        }
        else if (washQueue.size()==0){
            ETALabel.setText("NOW");
            Message.showMessage("Queue Completed", "It's your turn now.",
                    "Go to next page for payment confirmation");
        }
         else {
            // TODO change to dynamic input here : line 147
            queueLengthOnceQueued = washQueue.size();
            QueueItem newItem = new QueueItem(App.getCurrUser(), LocalDateTime.now(), new WashMachine());
            washQueue.add(newItem);
            ETATimeShow();
            isQueued = true;
        }
    }

    @FXML
    private void mockQueue() {
        QueueItem newItem = new QueueItem(App.getCurrUser(), LocalDateTime.now(), new WashMachine());
        washQueue.add(newItem);
        if (!isQueued) {
            // add 1 to make up for the keyframe lag
            seconds += newItem.getDuration() + 1;
            if (time.getStatus() == Status.STOPPED)
                time.play();
            qLengthShow();
        }

    }
}
