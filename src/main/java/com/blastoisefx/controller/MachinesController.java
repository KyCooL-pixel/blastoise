package com.blastoisefx.controller;

import java.io.IOException;

import com.blastoisefx.App;
import com.blastoisefx.model.Machine;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

public class MachinesController {
    @FXML
    private ListView<Machine> machinesView;
    @FXML
    private Button backButton;
    @FXML
    private Button payBtn;

    @FXML
    public void initialize(){
        machinesView.setCellFactory(new Callback<ListView<Machine>,ListCell<Machine>>() {
            public ListCell<Machine> call(ListView<Machine> param){
                return new MachineCell();

                }
        });
        machinesView.setItems(App.getMachines());
    }
    @FXML
    private void backToMenu() throws IOException{
        App.setRoot("menu");
    }
    @FXML
    private void toPayment() throws IOException{
        App.setRoot("payment");
    }
}
