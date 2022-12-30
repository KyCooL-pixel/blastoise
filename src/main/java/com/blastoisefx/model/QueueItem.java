package com.blastoisefx.model;

import java.time.LocalDateTime;

public class QueueItem {
    private User user;
    private LocalDateTime startTime;
    private MachineTypes machineTypes;

    private int duration;

    public enum State {
        WAITING,
        WASHING,
        FINISHED
    }

    private State state;

    // GETTERS AND SETTERS FOR EVERYTHING STARTS HERE
    public MachineTypes getMachineTypes() {
        return machineTypes;
    }

    public void setMachineTypes(MachineTypes machineTypes) {
        this.machineTypes = machineTypes;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public Integer getDuration() {
        return duration;
    }

    // ENDS HERE

    // CONSTRUCTOR
    public QueueItem(User user, LocalDateTime startTime, MachineTypes machineTypes) {
        this.user = user;
        this.startTime = startTime;
        this.machineTypes = machineTypes;
        this.state = State.WAITING;
        this.duration = machineTypes.getDuration();
    }

}
