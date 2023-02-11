package com.blastoisefx.model;

public class Dryer extends Machine {
  private Temperature temperature;

  public enum Temperature {
    LOW,
    MID,
    HIGH,
  }

  public Dryer(String id) {
    super(id,10);
    this.temperature = Temperature.LOW;
  }

  public Temperature getTemperature() {
    return temperature;
  }

  public void setTemperature(Temperature temperature) throws IllegalStateException {
    if (this.getStatus() == Status.IDLE) {
      this.temperature = temperature;
      return;
    }

    throw new IllegalStateException("Cannot change temperature while machine is not idle");
  }
}
