package com.blastoisefx.model;

import java.time.LocalDateTime;

import com.blastoisefx.model.QueueItem.State;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Machine {
  private ObservableList<QueueItem> queue;

  public static enum Status {
    IDLE,
    OPERATING,
    LOCKED,
  }

  private Status status;
  private String id;

  public Machine(String id) {
    this.status = Status.IDLE;
    this.queue = FXCollections.observableArrayList();
    this.id = id;
  }

  public Status getStatus() {
    return status;
  }

  public void checkQueue(LocalDateTime currentTime) {
    if (queue.size() < 1) {
      setStatus(Status.IDLE);
      return;
    }

    QueueItem item = queue.get(0);
    if (status == Status.OPERATING) {
      if (currentTime.isAfter(item.getEndTime())) {
        setStatus(Status.IDLE);
        item.setState(State.FINISHED);
        queue.remove(0);
        return;
      }
    }

    if (item.getState() == State.WAITING) {
      item.setState(State.OPERATING);
      setStatus(Status.OPERATING);
    }
  }

  public void addToQueue(QueueItem item) {
    queue.add(item);
  }

  public ObservableList<QueueItem> getQueue() {
    return queue;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public LocalDateTime getEndTime() {
    if (queue.size() == 0) {
      return LocalDateTime.now();
    }

    return queue.get(queue.size() - 1).getEndTime();
  }

  public String getId() {
    return id;
  }
}
