package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import logic.character.Punk;

public class HpBoard extends HBox {
    private static HpBoard instance;
    public HpBoard(){
        setSpacing(8);
        setPrefWidth(91);
        setPrefHeight(20);
        for (int i = 0; i < Punk.getInstance().getHp(); i++) {
            ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
            hp.setFitHeight(32);
            hp.setFitWidth(40);
            getChildren().add(hp);
        }
    }
    public static void updateHpBoard(){
        getInstance().getChildren().clear();
        for (int i = 0; i < Punk.getInstance().getHp(); i++) {
            ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
            hp.setFitHeight(32);
            hp.setFitWidth(40);
            getInstance().getChildren().add(hp);
        }
    }

    public static HpBoard getInstance() {
        if (instance == null){
            instance = new HpBoard();
        }
        return instance;
    }
}
