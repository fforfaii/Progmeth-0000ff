package gui;

import javafx.animation.Animation;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import logic.Punk;

public class CaveMapPane extends AnchorPane {
    private static CaveMapPane instance;
    Punk punk;
    public CaveMapPane() {
        setBGImage();

        // Set Ground
        Image groundImage = new Image(ClassLoader.getSystemResource("rock_ground_long.png").toString());
        ImageView groundImageView = new ImageView(groundImage);
        setTopAnchor(groundImageView,530.0);

        this.getChildren().add(groundImageView);

        // Set Main Character
        punk = Punk.getInstance();
        setMainChar("Punk_idle.png",4,4,48,48);

        // Keyboard Input
        setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        // go left
                        System.out.println("A");
                        setMainChar("Punk_runleft.png",6,6,48,48);
                        punk.setxPos(punk.getxPos()-1);
                        break;
                    case D:
                        // go right
                        System.out.println("D");
                        setMainChar("Punk_runright.png",6,6,48,48);
                        punk.setxPos(punk.getxPos()+1);
                        break;
                    case TAB:
                        // release power
                        break;
                }
            }
        });
        setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                // idle
                setMainChar("Punk_idle.png",4,4,48,48);
            }
        });
    }

    public void setMainChar(String sprite, int count, int column, int width, int height) {
        ImageView mainChar = new ImageView(new Image(ClassLoader.getSystemResource(sprite).toString()));
        Animation mainAni = new SpriteAnimation(mainChar,Duration.millis(1000),count,column,0,0,width,height);
        mainAni.setCycleCount(Animation.INDEFINITE);
        mainChar.setFitWidth(100);
        mainChar.setFitHeight(100);
        mainAni.play();
        setTopAnchor(mainChar,453.0);
        getChildren().add(mainChar);

        punk.setxPos(mainChar.getX());
    }

    public void setBGImage() {
        String img_path = ClassLoader.getSystemResource("BG_Cave.jpg").toString();
        Image img = new Image(img_path);
        BackgroundImage bg_img = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
        setBackground(new Background(bg_img));
    }

    public static CaveMapPane getInstance() {
        if (instance == null) {
            instance = new CaveMapPane();
        }
        return instance;
    }
}
