package com.blastoisefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.blastoisefx.model.User;

/**
 * JavaFX App
 */
public class App extends Application {
    // Logic part
    // will leave the users list here first
    private static ArrayList<User> users = new ArrayList<User>();

    // UI
    private static Scene scene;
    @Override
    public void init(){
        loadUsers();
    }

    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image(App.class.getResourceAsStream("pokemon-blastoise-nicknames.jpg")));
        scene = new Scene(loadFXML("main"), 640, 480);
        stage.setScene(scene);
        stage.show();
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
            users = (ArrayList<User>) in.readObject();
            // closing the stream
            in.close();
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Creating new database file for storing users");
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

}