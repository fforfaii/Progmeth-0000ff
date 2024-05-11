package gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class HpBoard extends HBox {
    private static HpBoard instance;
    public HpBoard(){
        setSpacing(8);
        setPrefWidth(91);
        setPrefHeight(20);
        for (int i = 0; i < 3; i++) {
            ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
            hp.setFitHeight(32);
            hp.setFitWidth(40);
            getChildren().add(hp);
        }
    }
    public static HpBoard getInstance() {
        if (instance == null){
            instance = new HpBoard();
        }
        return instance;
    }
}
