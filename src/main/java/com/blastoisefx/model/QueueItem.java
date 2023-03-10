
package com.blastoisefx.model;

import java.time.LocalDateTime;

public class QueueItem {
    private User user;
    private LocalDateTime startTime;
    private State state = State.WAITING;
    private Payment payment;
    private int duration;

    public enum State {
        WAITING,
        OPERATING,
        FINISHED
    }

    // GETTERS AND SETTERS FOR EVERYTHING STARTS HERE
    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public Payment getPayment() {
        return payment;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getOperationEndTime() {
        return startTime.plusSeconds(duration);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // ENDS HERE

    // CONSTRUCTOR
    public QueueItem(User user, Payment payment, int duration) {
        this.user = user;
        this.payment = payment;
        this.startTime = LocalDateTime.now();
        this.duration = duration;
    }
}
