package com.blastoisefx.controller;

import java.io.IOException;

import com.blastoisefx.App;
import com.blastoisefx.model.Dryer;
import com.blastoisefx.model.Machine;
import com.blastoisefx.model.Dryer.Temperature;
import com.blastoisefx.model.Machine.Load;
import com.blastoisefx.model.Machine.Status;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class MachinesController {
    @FXML
    private ListView<Machine> machinesView;
    @FXML
    private Label machineTypeLabel;
    @FXML
    private Button backButton;
    @FXML
    private Button payBtn;
    @FXML
    private ComboBox<String> loadBox;
    @FXML
    private ComboBox<String> tempBox;
    @FXML
    private Label time;
    @FXML
    private Button lockButton;
    @FXML
    private TextField priceField;
    @FXML
    private Button startBtn;
    @FXML
    private Button stopBtn;
    @FXML
    private Button abortBtn;

    private Machine currMachine;

    @FXML
    public void initialize() {
        machinesView.setCellFactory(new Callback<ListView<Machine>, ListCell<Machine>>() {
            public ListCell<Machine> call(ListView<Machine> param) {
                return new MachineCell();
            }
        });
        machinesView.setItems(App.getMachines());
        machinesView.getSelectionModel().select(0);
        currMachine = machinesView.getSelectionModel().getSelectedItem();
        loadBox.getItems().addAll("LOW", "MID", "HIGH");
        tempBox.getItems().addAll("LOW", "MID", "HIGH");

        // add listener for machineview
        loadCurrentMachine(currMachine);
        showTotalPrice();
    }

    @FXML
    private void backToMenu() throws IOException {
        App.setRoot("menu",340,400);
    }

    @FXML
    private void toPayment() throws IOException {
        App.setRoot("payment",300,300);
    }

    @FXML
    private void loadCurrentMachine(Machine machine) {
        machineTypeLabel.setText(machine.getClass().getSimpleName());
        loadBox.setValue(machine.getLoad().toString());
        if (machine instanceof Dryer) {
            Dryer d = (Dryer) machine;
            tempBox.setDisable(false);
            tempBox.setValue(d.getTemperature().toString());
        } else {
            tempBox.setDisable(true);
        }
    }

    // listener
    @FXML
    public void handleMouseClick(MouseEvent arg0) {
        currMachine = machinesView.getSelectionModel().getSelectedItem();
        loadCurrentMachine(currMachine);
    }

    @FXML
    public void startProcess() {
        // starting a process will lock a machine type and temp(dryer)
        String loadStatus = loadBox.getSelectionModel().getSelectedItem();
        switch (loadStatus) {
            case "LOW":
                currMachine.setLoad(Load.LOW);
                break;
            case "MID":
                currMachine.setLoad(Load.MID);
                break;
            case "HIGH":
                currMachine.setLoad(Load.HIGH);
                break;
        }
        if (currMachine instanceof Dryer) {
            String tempStatus = tempBox.getSelectionModel().getSelectedItem();
            Dryer d = (Dryer) currMachine;
            switch (tempStatus) {
                case "LOW":
                    d.setTemperature(Temperature.LOW);
                    break;
                case "MID":
                    d.setTemperature(Temperature.MID);
                    break;
                case "HIGH":
                    d.setTemperature(Temperature.HIGH);
                    break;
            }
        }
        currMachine.setStatus(Status.OPERATING);
    }

    @FXML
    private void showTotalPrice(){
        priceField.setText(computeTotalPrice()+"");
    }

    private double computeTotalPrice() {
        double price = 0;
        price = currMachine.getTotalPrice();
        price += loadBox.getSelectionModel().getSelectedIndex() + tempBox.getSelectionModel().getSelectedIndex();
        return price;
    }
}
