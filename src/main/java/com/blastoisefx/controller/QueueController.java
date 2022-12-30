package com.blastoisefx.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class QueueController {
    // Initiate a queue here or should I?
    private Queue<QueueItem> washQueue = new LinkedList<QueueItem>();

    // these two need to take from model
    private Integer seconds = starttime;

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
        washQueue.add(new QueueItem(App.getCurrUser(), LocalDateTime.now(), new WashMachine()));
        washQueue.add(new QueueItem(App.getCurrUser(), LocalDateTime.now(), new WashMachine()));
        washQueue.add(new QueueItem(App.getCurrUser(), LocalDateTime.now(), new WashMachine()));
        washQueue.add(new QueueItem(App.getCurrUser(), LocalDateTime.now(), new WashMachine()));
        localTimeShow();
        seconds=computeTime();
        doTime();
    }
    @FXML
    private void localTimeShow(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                localTimeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }
    @FXML
    private void ETATimeShow(){
        ETALabel.setText(LocalDateTime.now().plusSeconds(computeTime()).format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }

    @FXML
    private void qLengthShow(){
        qLength.setText("Queue Length:" + washQueue.size());
    }
    // This method does the count down stuff
    @FXML
    private void doTime() {
        // Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                seconds--;
                countDownLabel.setText(formatTime(seconds));
                if (seconds <= 0) {
                    time.stop();
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
    private int computeTime(){
        int temp =0;
        for(QueueItem qItem: washQueue){
            temp+=qItem.getDuration();
        }
        return temp;
    }
    private String formatTime(int i){
        String time = String.format("%02d:%02d", i / 60, i % 60);
        return time;
    }


    @FXML
    private void createQueueItem(){

    }

}
