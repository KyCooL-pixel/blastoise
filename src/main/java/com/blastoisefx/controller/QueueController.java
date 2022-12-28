package com.blastoisefx.controller;

import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class QueueController {
    private final Integer starttime=15;
    private Integer seconds= starttime;
    @FXML private Label countDownLabel;
    @FXML
    public void initialize() {
        doTime();
    }
    @FXML
    private void doTime() {
        Timeline time= new Timeline();


        KeyFrame frame= new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

         @Override
         public void handle(ActionEvent event) {


          seconds--;
          countDownLabel.setText("Countdown: "+seconds.toString());
          if(seconds<=0){
           time.stop();
          }


         }


        });

        time.setCycleCount(Timeline.INDEFINITE);
        time.getKeyFrames().add(frame);
        if(time!=null){
         time.stop();
        }
        time.play();


       }

}
