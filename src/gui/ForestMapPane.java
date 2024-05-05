package gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import logic.character.Punk;

public class ForestMapPane extends AnchorPane {
    private static ForestMapPane instance;
    private ImageView mainChar;
    private ImageView Boom;
    private Animation mainAni;
    private Image Gun;
    private Image runLeft;
    private Image runRight;
    private Image Idle;
    private boolean canShoot;
    Punk punk;

    public ForestMapPane(){
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
        mainAni = new SpriteAnimation(mainChar, Duration.millis(1000),4,4,0,0,48,48);
        mainAni.setCycleCount(Animation.INDEFINITE);
        mainChar.setFitWidth(100);
        mainChar.setFitHeight(100);
        mainAni.play();
        setTopAnchor(mainChar,453.0);

        // Set GunBoom
        canShoot = true;
        Boom = new ImageView(new Image(ClassLoader.getSystemResource("gun1.png").toString()));
        Boom.setFitWidth(12);
        Boom.setFitHeight(72);
        Boom.setVisible(false);

        getChildren().addAll(mainChar, Boom);

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        // go left
                        System.out.println("A");
                        System.out.println(mainChar.getLayoutX());
                        if (mainChar.getLayoutX() >= 5.0) mainChar.setLayoutX(mainChar.getLayoutX()-punk.getSpeed());
                        setMainChar(runLeft,6,6,48,48);
                        break;
                    case D:
                        // go right
                        System.out.println("D");
                        System.out.println(mainChar.getLayoutX());
                        if (mainChar.getLayoutX() <= 1080) mainChar.setLayoutX(mainChar.getLayoutX()+punk.getSpeed());
                        setMainChar(runRight,6,6,48,48);
                        break;
                    case SPACE:
                        // release power
                        if (!canShoot){
                            return;
                        }
                        System.out.println("Boom!");
                        Shoot(Gun);
                        canShoot = false;
                        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> canShoot = true));
                        cooldownTimer.play();
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
        String img_path = ClassLoader.getSystemResource("BG_forest1.jpg").toString();
        Image img = new Image(img_path);
        BackgroundImage bg_img = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
        setBackground(new Background(bg_img));
    }

    public static ForestMapPane getInstance() {
        if (instance == null) {
            instance = new ForestMapPane();
        }
        return instance;
    }
}
