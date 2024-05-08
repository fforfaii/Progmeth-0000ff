package gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.character.Punk;

import java.util.ServiceConfigurationError;

public class ScoreBoard extends HBox {
    private static ScoreBoard instance;
    public ScoreBoard(){
        setPadding(new Insets(8));
        setPrefWidth(50);
        setPrefHeight(30);
        setBackground(new Background(new BackgroundFill(Color.rgb(247, 243, 229), new CornerRadii(10.0), null)));
        setAlignment(Pos.CENTER);
        Text text = new Text("Your Score :  ");
        Text Score = new Text("" + Punk.getInstance().getScore());
        setTextScoreBoard(text);
        setTextScoreBoard(Score);
        getChildren().addAll(text,Score);
    }
    public void setScoreboard() {
        getChildren().remove(1);
        Text Score = new Text("" + Punk.getInstance().getScore());
        setTextScoreBoard(Score);
        getChildren().add(Score);
    }
    public void setTextScoreBoard(Text text) {
        text.setFont(Font.font("Monospace", FontWeight.EXTRA_BOLD,18));
        text.setFill(Color.rgb(48, 34, 3));
    }
    public static ScoreBoard getInstance(){
        if (instance == null){
            instance = new ScoreBoard();
        }
        return instance;
    }
}
