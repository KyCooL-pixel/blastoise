package com.blastoisefx.controller;

import com.blastoisefx.model.Machine;

import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;

public class MachineCell extends ListCell<Machine> {
    @Override
    // TODO refactor states to another class
    public void updateItem(Machine machine, boolean yes){
        super.updateItem(machine, yes);
        if(machine != null){
            String name = machine.getClass().getSimpleName();
            Label machinetype = new Label(name);
            machinetype.setTextFill( name.equals("Washer")? Color.web("Blue"):Color.web("Green"));
            setGraphic(machinetype);
        }
    }
}
