package com.blastoisefx.model;

public class MachineTypes {
    private int duration;
    private double price;
    public enum MachineState{
        IDLE,
        WASHING,
        LOCKED,
    }

    private MachineState machineState;

    public int getDuration() {
        return duration;
    }
    public void setDuration(int processTime) {
        this.duration = processTime;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public MachineState getMachineState(){
        return machineState;
    }
    public void setState(MachineState machineState){
        this.machineState = machineState;
    }

    // Constuctor here
    public MachineTypes(){

    }
}
