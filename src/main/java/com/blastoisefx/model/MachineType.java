package com.blastoisefx.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

public class MachineType<T extends Machine> {
    private String name;

    private double BASE_PRICE;
    private double ADD_ON_PRICE;

    private ObservableList<T> machines;

    private final int MINIMUM_DURATION;
    private final int ADD_ON_DURATION_DIVISION;

    public MachineType(ArrayList<T> machines, double basePrice, double addOnPrice, int minimumDuration,
            int addonDurationDivision) {
        if (machines.size() == 0)
            throw new IllegalArgumentException("MachineType must have at least one machine");

        this.machines = FXCollections.observableList(machines);
        this.name = machines.get(0).getClass().getSimpleName();
        this.BASE_PRICE = basePrice;
        this.ADD_ON_PRICE = addOnPrice;
        this.MINIMUM_DURATION = minimumDuration;
        this.ADD_ON_DURATION_DIVISION = addonDurationDivision;
    }

    public String getName() {
        return name;
    }

    public double getPrice(int Duration) {
        return BASE_PRICE
                + (ADD_ON_PRICE * Math.ceil((Duration - MINIMUM_DURATION) / (double) ADD_ON_DURATION_DIVISION));
    }

    public ObservableList<T> getMachines() {
        return machines;
    }

    public Machine getFastestAvailableMachine() {
        Machine fastestMachine = null;
        LocalDateTime fastestTime = LocalDateTime.MAX;
        for (T machine : machines) {
            if(machine.getEndTime().isBefore(fastestTime)){
                fastestMachine = machine;
                fastestTime = machine.getEndTime();
            }
        }
        return fastestMachine;
    }

    public void addQueueItem(QueueItem queueItem) {
        getFastestAvailableMachine().addToQueue(queueItem);
    }
}
