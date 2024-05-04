package main;

import gui.JoinPane;
import gui.LoginPane;
import gui.WelcomePane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(WelcomePane.getInstance(),1152,648);
        stage.setScene(scene);
        stage.setTitle("Ready Set RUN!");
        stage.setResizable(false);
        stage.show();
    }

    public void changeScene(String root) throws IOException {

    }

    public static void main(String[] args) {
        launch(args);
    }
}
