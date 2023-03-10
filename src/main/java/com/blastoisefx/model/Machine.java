package com.blastoisefx.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import com.blastoisefx.model.QueueItem.State;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class Machine {
  private ObservableList<QueueItem> queue;

  public static enum Status {
    IDLE,
    OPERATING,
    LOCKED,
    WAITING,
  }

  private Status status;
  private String id;
  private final int lockDuration;

  public Machine(String id, int lockDuration) {
    this.status = Status.IDLE;
    this.queue = FXCollections.observableArrayList();
    this.id = id;
    this.lockDuration = lockDuration;
  }

  public Status getStatus() {
    return status;
  }

  public QueueItem checkQueue(LocalDateTime currentTime) {
    if (queue.size() < 1) {
      setStatus(Status.IDLE);
      return null;
    }

    QueueItem item = queue.get(0);
    if (status == Status.OPERATING && currentTime.isAfter(item.getOperationEndTime())) {
      setStatus(Status.LOCKED);
      item.setState(State.FINISHED);
      return item;
    }

    if(currentTime.isAfter(item.getOperationEndTime().plusSeconds(lockDuration))) {
      setStatus(Status.IDLE);
      item.setState(State.FINISHED);
      item.getUser().getQueue().remove(item);
      return queue.remove(item) ? item : null;
    }

    if (item.getState() == State.WAITING) {
      item.setState(State.OPERATING);
      setStatus(Status.OPERATING);
      return null;
    }

    return null;
  }

  public void addToQueue(QueueItem item) {
    item.setStartTime(getEndTime());
    queue.add(item);
    item.getUser().getQueue().add(item);
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

    return queue.get(queue.size() - 1).getOperationEndTime();
  }

  public long getQueueItemWaitingTime(QueueItem item) {
    var index = queue.indexOf(item);
    return getQueueItemWaitingTime(index);
  }

  public long getQueueItemWaitingTime(int index) {
    if (index < 0 || index >= queue.size()) {
      throw new IndexOutOfBoundsException();
    }

    LocalDateTime currentTime = LocalDateTime.now();

    return getQueueItemWaitingTimeRecursive(index, currentTime);
  }

  private long getQueueItemWaitingTimeRecursive(int index, LocalDateTime currentTime) {
    if (index < 0 || index > queue.size()) {
      // return -1 to fix time calcuation overlapping
      return -1;
    }

    return ChronoUnit.SECONDS.between(currentTime, queue.get(index).getOperationEndTime().plusSeconds(lockDuration))
        + getQueueItemWaitingTimeRecursive(index - 1, currentTime);
  }

  public String getId() {
    return id;
  }
}
