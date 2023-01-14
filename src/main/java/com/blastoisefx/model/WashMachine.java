package com.blastoisefx.model;

public class WashMachine extends MachineType {
    public enum Temperature{
        LOW,
        MID,
        HIGH,
    }

    public WashMachine() {
        super();
        this.setDuration(5);
    }

}
