package com.blastoisefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.blastoisefx.model.MachineType;
import com.blastoisefx.model.QueueItem;
import com.blastoisefx.model.Machine;
import com.blastoisefx.model.Washer;
import com.blastoisefx.controller.ClientController;
import com.blastoisefx.model.Dryer;
import com.blastoisefx.model.Iron;
import com.blastoisefx.model.User;

/**
 * JavaFX App
 */
public class App extends Application {
    // Logic part
    // will leave the users list here first
    private static ArrayList<User> users = new ArrayList<User>();
    private static User currUser;

    private static ArrayList<MachineType<? extends Machine>> machineTypes;
    private static ArrayList<ClientController> clientControllers;
    private static Scene scene;
    private static Stage _stage;

    public static final double MILLISECONDS_PER_FRAME_IN_30_FPS = Math.ceil(1000 / (double) 33);

    @Override
    public void init() {
        loadUsers();

        final int NUMBER_OF_WASHERS = 2;
        final int NUMBER_OF_DRYERS = 2;
        final int NUMBER_OF_IRONS = 2;

        // Initiate machines
        ArrayList<Washer> washers = new ArrayList<>();
        ArrayList<Dryer> dryers = new ArrayList<>();
        ArrayList<Iron> irons = new ArrayList<>();

        for (int i = 1; i <= NUMBER_OF_WASHERS; i++) {
            washers.add(new Washer(String.valueOf(i)));
        }
        for (int i = 1; i <= NUMBER_OF_DRYERS; i++) {
            dryers.add(new Dryer(String.valueOf(i)));
        }
        for (int i = 1; i <= NUMBER_OF_IRONS; i++) {
            irons.add(new Iron(String.valueOf(i)));
        }

        // Initiate machine types
        machineTypes = new ArrayList<>();
        machineTypes.add(new MachineType<Washer>(washers, 3, 1, 30, 10));
        machineTypes.add(new MachineType<Dryer>(dryers, 5, 2, 30, 12));
        machineTypes.add(new MachineType<Iron>(irons, 1, 1, 10, 10));

        var user = new User("ngiubing02@gmail.com", null);
        machineTypes.get(0).addQueueItem(new QueueItem(user, null, 10));
        machineTypes.get(0).addQueueItem(new QueueItem(user, null, 10));
        machineTypes.get(0).addQueueItem(new QueueItem(user, null, 10));

        // store clientcontrollers
        clientControllers = new ArrayList<>();
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        stage.getIcons().add(new Image(App.class.getResourceAsStream("pokemon-blastoise-nicknames.jpg")));
        scene = new Scene(loadFXML("main"), 370, 407);
        stage.setScene(scene);
        stage.show();
        _stage = stage;
    }

    @Override
    public void stop() {
        saveUsers();
    }

    private void saveUsers() {
        try {
            // Creating stream and writing the object
            FileOutputStream fout = new FileOutputStream("users.txt");
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(users);
            out.flush();
            // closing the stream
            out.close();
            System.out.println("Users' data saved successfully");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void loadUsers() {
        try {
            // Creating stream to read the object
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("users.txt"));
            @SuppressWarnings("unchecked")
            ArrayList<User> usersFromDataBase = (ArrayList<User>) in.readObject();
            users = usersFromDataBase;
            // closing the stream
            in.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Creating new database file for storing users");
        }
    }

    public static ArrayList<MachineType<? extends Machine>> getMachineTypes() {
        return machineTypes;
    }

    public static void setRoot(String fxml, double width, double height) throws IOException {
        scene.setRoot(loadFXML(fxml));
        Window window = scene.getWindow();
        window.setWidth(width);
        window.setHeight(height);
    }

    public static FXMLLoader getFXMLLoader(String fxml) {
        return new FXMLLoader(App.class.getResource(fxml + ".fxml"));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = getFXMLLoader(fxml);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getCurrUser() {
        return currUser;
    }

    public static void setCurrUser(User cu) {
        currUser = cu;
    }

    public static ArrayList<ClientController> getClientControllers() {
        return clientControllers;
    }

}