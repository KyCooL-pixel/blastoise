package com.blastoisefx.model;

public class WashMachine extends MachineTypes {
    public enum Temperature{
        LOW,
        MID,
        HIGH,
    }

    public WashMachine() {
        super();
        this.setDuration(40);
    }

}
