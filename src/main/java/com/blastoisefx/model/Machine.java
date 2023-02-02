package com.blastoisefx.model;

public abstract class Machine {
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

  public static enum Status {
    IDLE,
    OPERATING,
    LOCKED,
  }

  private Status status;
  private double price;

  public Machine() {
    this.status = Status.IDLE;
    this.load = Load.LOW;
    this.price = 3;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

public double getTotalPrice() {
    return price;
}
}
