package com.blastoisefx.model;

import java.util.ArrayList;

public class MachineType<T extends Machine> {
    private String name;

    private double BASE_PRICE;
    private double ADD_ON_PRICE;

    private ArrayList<QueueItem> queue;
    private ArrayList<T> machines;

    private final int MINIMUM_DURATION;
    private final int ADD_ON_DURATION_DIVISION;

    public MachineType(ArrayList<T> machines, double basePrice, double addOnPrice, int minimumDuration,
            int addonDurationDivision) {
        if (machines.size() == 0)
            throw new IllegalArgumentException("MachineType must have at least one machine");

        this.machines = machines;
        this.name = machines.get(0).getClass().getSimpleName();
        this.queue = new ArrayList<>();
        this.BASE_PRICE = basePrice;
        this.ADD_ON_PRICE = addOnPrice;
        this.MINIMUM_DURATION = minimumDuration;
        this.ADD_ON_DURATION_DIVISION = addonDurationDivision;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        int totalDuration = 0;
        for (QueueItem queueItem : queue) {
            if (queueItem.getState() != QueueItem.State.FINISHED) {
                totalDuration += queueItem.getDuration();
            }
        }
        return totalDuration;
    }

    public int getWaitingTime() {
        int totalWaitingTime = 0;
        for (QueueItem queueItem : queue) {
            if (queueItem.getState() != QueueItem.State.FINISHED) {
                totalWaitingTime += queueItem.getWaitingTime();
            } else {
                break;
            }
        }
        return totalWaitingTime;
    }

    public double getPrice(int Duration) {
        return BASE_PRICE
                + (ADD_ON_PRICE * Math.ceil((Duration - MINIMUM_DURATION) / (double) ADD_ON_DURATION_DIVISION));
    }

    public ArrayList<QueueItem> getQueue() {
        return queue;
    }

    public ArrayList<T> getMachines() {
        return machines;
    }

    public void addQueueItem(QueueItem queueItem) {
        if(queueItem.getDuration() < MINIMUM_DURATION) {
            queueItem.setDuration(MINIMUM_DURATION);
        }

        if(queue.size() > 0) {
            queueItem.setStartTime(queue.get(queue.size() - 1).getEndTime());
        }
        queue.add(queueItem);
    }

    public QueueItem removeQueueItem() {
        return queue.remove(queue.size() - 1);
    }
}
