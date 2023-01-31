package com.blastoisefx.model;

public abstract class Machine {
    public static enum Status {
      IDLE,
      OPERATING,
      LOCKED,
    }

    private Status status;

    public Machine() {
      this.status = Status.IDLE;
    }

    public Status getStatus() {
      return status;
    }

    public void setStatus(Status status) {
      this.status = status;
    }
}

