package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.character.Punk;
import main.Main;

import java.io.IOException;

public class GameOverPane extends StackPane {
    private static GameOverPane instance;
    Punk player;
    VBox allElements;
    public GameOverPane() {
        player = Punk.getInstance();

        String img_path = ClassLoader.getSystemResource("BG_end.gif").toString();
        Image img = new Image(img_path);
        BackgroundImage bg_img = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
        setBackground(new Background(bg_img));

        allElements = new VBox();
        allElements.setAlignment(Pos.TOP_CENTER);
        allElements.setSpacing(12.0);

        ImageView gameover = new ImageView(ClassLoader.getSystemResource("gameover.gif").toString());
        gameover.setFitWidth(400);
        gameover.setFitHeight(400);
        gameover.setViewport(new Rectangle2D(0, 50, gameover.getFitWidth(), gameover.getFitHeight()-100));
        VBox.setMargin(gameover, new Insets(0, 70, 0, 0));

        Text scoreText = new Text("Your Score is " + player.getScore() + " !");
        scoreText.setFont(Font.font("Monospace", FontWeight.EXTRA_BOLD, 40));
        scoreText.setFill(Color.rgb(255, 252, 245));

        Button menu = new Button("menu");
        menu.setFont(Font.font("Monospace", FontWeight.BOLD,20));
        menu.setTextFill(Color.rgb(29, 9, 75));
        menu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.getInstance().changeSceneJava(MapPane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        allElements.getChildren().addAll(gameover,scoreText,menu);
        getChildren().add(allElements);
    }

    public static GameOverPane getInstance() {
        if (instance == null) {
            instance = new GameOverPane();
        }
        return instance;
    }
}