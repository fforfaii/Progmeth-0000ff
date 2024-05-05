package gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import logic.Punk;

public class CaveMapPane extends AnchorPane {
    private static CaveMapPane instance;
    private ImageView mainChar;
    private ImageView Boom;
    private Animation mainAni;
    private Image Gun;
    private Image runLeft;
    private Image runRight;
    private Image Idle;
    Punk punk;
    public CaveMapPane() {
        setBGImage();

        // Set Ground
        Image groundImage = new Image(ClassLoader.getSystemResource("rock_ground_long.png").toString());
        ImageView groundImageView = new ImageView(groundImage);
        setTopAnchor(groundImageView,530.0);

        this.getChildren().add(groundImageView);

        //Preload Run Animation
        Gun = new Image(ClassLoader.getSystemResource("Punk_Gun_Resize.png").toString());
        runLeft = new Image(ClassLoader.getSystemResource("Punk_runleft.png").toString());
        runRight = new Image(ClassLoader.getSystemResource("Punk_runright.png").toString());
        Idle = new Image(ClassLoader.getSystemResource("Punk_idle.png").toString());

        // Set Main Character
        punk = Punk.getInstance();
        mainChar = new ImageView(new Image(ClassLoader.getSystemResource("Punk_idle.png").toString()));
        mainAni = new SpriteAnimation(mainChar,Duration.millis(1000),4,4,0,0,48,48);
        mainAni.setCycleCount(Animation.INDEFINITE);
        mainChar.setFitWidth(100);
        mainChar.setFitHeight(100);
        mainAni.play();
        setTopAnchor(mainChar,453.0);

        // Set GunBoom
        Boom = new ImageView(new Image(ClassLoader.getSystemResource("gun1.png").toString()));
        Boom.setFitWidth(12);
        Boom.setFitHeight(72);
        Boom.setVisible(false);

        getChildren().addAll(mainChar, Boom);

        // Keyboard Input
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        // go left
                        System.out.println("A");
                        if (mainChar.getLayoutX() >= 5.0) mainChar.setLayoutX(mainChar.getLayoutX()-5);
                        setMainChar(runLeft,6,6,48,48);
                        break;
                    case D:
                        // go right
                        System.out.println("D");
                        if (mainChar.getLayoutX() <= 1100) mainChar.setLayoutX(mainChar.getLayoutX()+5);
                        setMainChar(runRight,6,6,48,48);
                        break;
                    case SPACE:
                        // release power
                        System.out.println("Boom!");
                        Shoot(Gun);
                        break;
                }
                punk.setxPos(mainChar.getLayoutX());
                System.out.println(punk.getxPos());
            }
        });
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                // idle
                setMainChar(Idle,4,4,48,48);
            }
        });

    }

    public void Shoot(Image image) {
        mainChar.setImage(image);
        mainChar.setViewport(new javafx.geometry.Rectangle2D(96, 0, 48, 48));
//        Boom.setVisible(true);
//        Boom.setLayoutY(0);
//        TranslateTransition boomTransition = new TranslateTransition(Duration.seconds(1), Boom);
//        boomTransition.setByY(-300); // Move the Boom image upward by 100 pixels
//        boomTransition.play(); // Start the animation
//        Boom.setLayoutY(0);
    }

    public void setMainChar(Image Image, int count, int column, int width, int height) {
        mainChar.setImage(Image);
        SpriteAnimation.getInstance().setCount(count);
        SpriteAnimation.getInstance().setColumns(column);
        SpriteAnimation.getInstance().setWidth(width);
        SpriteAnimation.getInstance().setHeight(height);
        mainAni.setCycleCount(Animation.INDEFINITE);
        mainChar.setFitWidth(100);
        mainChar.setFitHeight(100);
        mainAni.play();
        setTopAnchor(mainChar,453.0);
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
