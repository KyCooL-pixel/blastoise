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
    // these two need to take from model
    private final Integer starttime = 15;
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

    // Shares timeline
    Timeline time = new Timeline();

    @FXML
    public void initialize() {
        localTimeShow();
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
    // This method does the count down stuff
    @FXML
    private void doTime() {
        // Timeline time = new Timeline();
        KeyFrame frame = new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                seconds--;
                countDownLabel.setText(seconds.toString());
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

    private String formatTime(int i){
        String time = String.format("%02d:%02d", i / 60, i % 60);
        return time;
    }


    @FXML
    private void createQueueItem(){

    }

}
