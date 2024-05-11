package main;

import gui.*;
import gui.GameOverPane;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Main instance;
    Parent root;
    Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        instance = this;
        this.root = MapPane.getInstance();
        Scene scene = new Scene(root,1152,648);
        stage.setScene(scene);
        stage.setTitle("Ready Set RUN!");
        stage.setResizable(false);
        stage.show();
    }

    public void changeSceneJava(Parent parent) throws IOException {
        stage.getScene().setRoot(parent);
        parent.requestFocus(); // very important if not include KeyEvent will not be listened !!!!
    }

    public static Main getInstance() {
        if (instance == null) {
            instance = new Main();
        }
        return instance;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
