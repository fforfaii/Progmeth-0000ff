package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import main.Main;
import sound.PlaySound;

import java.io.IOException;

public class LoginPane extends GridPane {
    private static LoginPane instance;
    public static String playerName = "";

    private LoginPane() {
        PlaySound.defaultBG.play();

        setBGImage();
        setAlignment(Pos.CENTER);
        setVgap(10);

        // Set Game's Logo
        Image logoImage = new Image(ClassLoader.getSystemResource("logo.png").toString());
        ImageView logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(300);
        logoImageView.setFitWidth(400);
        GridPane.setConstraints(logoImageView, 0, 0);

        // Set Text Field
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setFont(Font.font("Monospace",FontWeight.BOLD,16));
        nameField.setFocusTraversable(false);
        GridPane.setConstraints(nameField, 0, 1);

        // Set Join Button
        Button joinBtn = new Button("JOIN GAME");
        GridPane.setConstraints(joinBtn,0,2);
        joinBtn.setFont(Font.font("Monospace",FontWeight.BOLD,20));
        joinBtn.setTextFill(Color.rgb(204,102,0));
        joinBtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #CCAA00; -fx-border-width: 3px;");
        GridPane.setHalignment(joinBtn, HPos.CENTER);
        joinBtn.setVisible(false);

        // Start Button will appear when User input player's name
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            joinBtn.setVisible(!newValue.isEmpty());
        });

        // Change Scene to MapPane
        joinBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    setPlayerName(nameField.getText());
                    Main.getInstance().changeSceneJava(MapPane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Set playerName variable
        setPlayerName(nameField.getText());
        getChildren().addAll(logoImageView, nameField, joinBtn);
    }

    public void setBGImage() {
        String img_path = ClassLoader.getSystemResource("BG_forest.gif").toString();
        Image img = new Image(img_path);
        BackgroundImage bg_img = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
        setBackground(new Background(bg_img));
    }

    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String playerName) {
        LoginPane.playerName = playerName;
    }

    public static LoginPane getInstance() {
        if (instance == null) {
            instance = new LoginPane();
        }
        return instance;
    }
}