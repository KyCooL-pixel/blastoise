package com.blastoisefx.model;

public class Washer extends Machine {
    private Load load;

    public enum Load {
      LOW,
      MID,
      HIGH,
    }

    public Load getLoad() {
        return load;
    }

    public void setLoad(Load load) {
        this.load = load;
    }
}
