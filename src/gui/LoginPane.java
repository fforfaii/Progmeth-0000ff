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

import java.io.IOException;

public class LoginPane extends GridPane {
    private static LoginPane instance;
    public static String playername = "";

    private LoginPane() {
        setBGImage();
        setAlignment(Pos.CENTER);
        setVgap(10);

        // Set Game's Logo
        Image image = new Image(ClassLoader.getSystemResource("logo.png").toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(300);
        imageView.setFitWidth(400);
        GridPane.setConstraints(imageView, 0, 0);

        // Set Text Field
        TextField playername = new TextField();
        playername.setPromptText("Enter your name");
        playername.setFont(Font.font("Monospace",FontWeight.BOLD,16));
        GridPane.setConstraints(playername, 0, 1);

        // Set Join Button
        Button joinBtn = new Button("JOIN GAME");
        GridPane.setConstraints(joinBtn,0,2);
        joinBtn.setFont(Font.font("Monospace",FontWeight.BOLD,20));
        joinBtn.setTextFill(Color.rgb(204,102,0));
        joinBtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #CCAA00; -fx-border-width: 3px;");
        GridPane.setHalignment(joinBtn, HPos.CENTER);
        joinBtn.setVisible(false);

        // Start Button will appear when User input player's name
        playername.textProperty().addListener((observable, oldValue, newValue) -> {
            joinBtn.setVisible(!newValue.isEmpty());
        });

        // Change Scene to MapPane
        joinBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    setPlayername(playername.getText());
                    Main.getInstance().changeSceneJava(MapPane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Set playername variable
        setPlayername(playername.getText());

        getChildren().addAll(imageView, playername, joinBtn);
    }

    public void setBGImage() {
        String img_path = ClassLoader.getSystemResource("BG_forest.gif").toString();
        Image img = new Image(img_path);
        BackgroundImage bg_img = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
        setBackground(new Background(bg_img));
    }

    public static String getPlayername() {
        return playername;
    }

    public static void setPlayername(String playername) {
        LoginPane.playername = playername;
    }

    public static LoginPane getInstance() {
        if (instance == null) {
            instance = new LoginPane();
        }
        return instance;
    }
}