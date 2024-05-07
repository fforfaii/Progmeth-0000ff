package gui;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import logic.character.Minions;
import logic.character.Punk;
import logic.character.SlowGhost;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class CaveMapPane extends AnchorPane {
    private static CaveMapPane instance;
    private ImageView mainChar;
    private ImageView Boom;
    private ImageView Minion;
    private Animation mainAni;
    private Animation minionsAni;
    private Image Gun;
    private Image runLeft;
    private Image runRight;
    private Image Idle;
    private Image minion;
    private boolean canShoot;
    private int tmp = 1; // for play ghost slide
    ArrayList<Integer> xPos_Down = new ArrayList<Integer>(); // for collect rand x position for ghost to go down
    Punk punk;
    Minions minions;
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
        minion = new Image(ClassLoader.getSystemResource("ghost1.png").toString());

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
        canShoot = true;
        Boom = new ImageView(new Image(ClassLoader.getSystemResource("gun1.png").toString()));
        Boom.setFitWidth(60); //12
        Boom.setFitHeight(360); //72
        Boom.setLayoutY(punk.getyPos());
        Boom.setVisible(false);

        // Set Ghost1
        minions = Minions.getInstance();
        Minion = new ImageView(minion);
        minionsAni = new SpriteAnimation(Minion,Duration.millis(1000),6,6,0,0,48,48);
        minionsAni.setCycleCount(Animation.INDEFINITE);
        Minion.setFitHeight(80);
        Minion.setFitWidth(80);
        minionsAni.play();
        setTopAnchor(Minion, 10.0);
        RunGhostAnimation();

        getChildren().addAll(mainChar, Boom, Minion);

        // Keyboard Input
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        // go left
                        System.out.println("A");
                        if (mainChar.getLayoutX() >= 5.0) mainChar.setLayoutX(mainChar.getLayoutX()-punk.getSpeed());
                        setMainChar(runLeft,6,6,48,48);
                        break;
                    case D:
                        // go right
                        System.out.println("D");
                        if (mainChar.getLayoutX() <= 1080) {
                            mainChar.setLayoutX(mainChar.getLayoutX()+punk.getSpeed());
                        }
                        setMainChar(runRight,6,6,48,48);
                        break;
                    case SPACE:
                        // release power
                        if (!canShoot){
                            return;
                        }
                        canShoot = false;
                        Shoot(Gun);
                        // Duration = 5
                        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Punk.getInstance().getDelayShoot()), event -> canShoot = true));
                        cooldownTimer.play();
                        System.out.println("Boom!");
                        break;
                }
                punk.setxPos(mainChar.getLayoutX());
                System.out.println("Punk X : " + punk.getxPos());
                System.out.println("Punk Y : " + punk.getyPos());
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

    public void RunGhostAnimation() {
        AnimationTimer GhostAnimationTimer = new AnimationTimer() {
            private long startTime = System.nanoTime();
            private long lastUpdate = 0;
            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastUpdate >= 10_000_000_000L){
                    SlideXPos(Minion);
                    lastUpdate = currentTime;
                }
                // Get Position & Set to Minions class
                minions.setxPos(getXPos(Minion));
                minions.setyPos(getYPos(Minion));
                // get random XPos
                if (xPos_Down.size() < 5){
                    xPos_Down.add(xPos_Down.size(),randXPos());
                }
                // Check xPos to goDown
//                if ((int) Minion.getTranslateX() == xPos_Down.get(0)){
//                    if (currentTime - lastUpdate >= 4_000_000_000L){
//                        goDown(Minion); // ยังติดว่าจะทำไงให้ AnimationTimer ชะลอช่วงที่ขึ้นลง
//                        lastUpdate = currentTime;
//                    }
//                    tmp = 1;
//                    if (currentTime - lastUpdate >= 10_000_000_000L){
//                        SlideXPos(Minion);
//                        lastUpdate = currentTime;
//                    }
//                }
//                System.out.println("ตำแหน่งที่ลง " + xPos_Down);
            }
        };
        GhostAnimationTimer.start();
    }
    public double getXPos(ImageView imageView) {
        return imageView.getTranslateX();
    }
    public double getYPos(ImageView imageView) {
        return imageView.getTranslateY();
    }
    public void SlideXPos(ImageView imageView) {
        // Create TranslateTransition for left-right movement
        TranslateTransition translateXTransition = new TranslateTransition(Duration.seconds(5), imageView);
        translateXTransition.setFromX(10);
        translateXTransition.setToX(1142 - imageView.getFitWidth());
        translateXTransition.setCycleCount(1);
        translateXTransition.setAutoReverse(true);
        translateXTransition.setOnFinished(event -> {
            // Create another TranslateTransition to move back from right-left
            TranslateTransition reverseTransition = new TranslateTransition(Duration.seconds(5), imageView);
            reverseTransition.setFromX(1142 - imageView.getFitWidth());
            reverseTransition.setToX(10);
            reverseTransition.setCycleCount(1);
            reverseTransition.setAutoReverse(false);
            reverseTransition.play();
        });

        if (tmp == 1 ) {
            translateXTransition.play();
        } else if (tmp == 0) {
            translateXTransition.stop();
        }
    }
    public int randXPos() {
        int randXPos = ThreadLocalRandom.current().nextInt(10, 1062);
        return randXPos;
    }
    public void goDown(ImageView imageView) {
        // remove useed xPos
        xPos_Down.remove(0);

        // Stop slide X axis
        this.tmp = 0;
        SlideXPos(imageView);

        // Move down
        TranslateTransition translateYTransitionDown = new TranslateTransition(Duration.seconds(2), imageView);
        translateYTransitionDown.setFromY(0);
        translateYTransitionDown.setToY(500);
        translateYTransitionDown.setCycleCount(1);
        translateYTransitionDown.setAutoReverse(true);

        // Move up
        TranslateTransition translateYTransitionUp = new TranslateTransition(Duration.seconds(2), imageView);
        translateYTransitionUp.setFromY(500);
        translateYTransitionUp.setToY(0);
        translateYTransitionUp.setCycleCount(1);
        translateYTransitionUp.setAutoReverse(true);
        translateYTransitionUp.setDelay(Duration.seconds(0)); // No Delay before moving up

        SequentialTransition sequentialTransition = new SequentialTransition(translateYTransitionDown, translateYTransitionUp);
        sequentialTransition.play();
    }

    // ไม่ใช้ละ แต่เก็บไว้เผื่อเอา code มาใช้
//    public void randomPos(ImageView imageView) {
//        // Create TranslateTransition for left-right movement
//        TranslateTransition translateXTransition = new TranslateTransition(Duration.seconds(5), imageView);
//        translateXTransition.setFromX(10); // Starting X position
//        translateXTransition.setToX(1142 - imageView.getFitWidth());
//        translateXTransition.setCycleCount(TranslateTransition.INDEFINITE);
//        translateXTransition.setAutoReverse(true);
//        translateXTransition.play();
//        System.out.println(imageView.getLayoutX());
//
//        Random random = new Random();
//        double randomXPos = random.nextInt(10) + 1;
//
//        // Get the coordinate of imageView in every 0.1 sec
//        Timeline monitorPosition = new Timeline(
//                new KeyFrame(Duration.ZERO, event -> {
//                    // Log current position every 0.1 seconds
//                    System.out.println("Current X: " + imageView.getTranslateX());
//                    System.out.println("Current Y: " + imageView.getTranslateY());
//                }),
//                new KeyFrame(Duration.seconds(0.1))
//        );
//        monitorPosition.setCycleCount(Timeline.INDEFINITE);
//        monitorPosition.play();
//
//        Timeline goDown = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
//            translateXTransition.stop();
//            // Move down
//            TranslateTransition translateYTransitionDown = new TranslateTransition(Duration.seconds(2), imageView);
//            translateYTransitionDown.setFromY(0);
//            translateYTransitionDown.setToY(500);
//            translateYTransitionDown.setCycleCount(1);
//            translateYTransitionDown.setAutoReverse(true);
//
//            // Move up
//            TranslateTransition translateYTransitionUp = new TranslateTransition(Duration.seconds(2), imageView);
//            translateYTransitionUp.setFromY(500);
//            translateYTransitionUp.setToY(0);
//            translateYTransitionUp.setCycleCount(1);
//            translateYTransitionUp.setAutoReverse(true);
//            translateYTransitionUp.setDelay(Duration.seconds(0)); // Delay before moving up
//
//            SequentialTransition sequentialTransition = new SequentialTransition(translateYTransitionDown, translateYTransitionUp);
//            sequentialTransition.play();
//        }));
//
//        goDown.play();
//    }

    public void Shoot(Image image) {
        // Set mainChar when Shoot
        mainChar.setImage(image);
        mainChar.setViewport(new javafx.geometry.Rectangle2D(96, 0, 48, 48));

        // Set Boom when Shoot
        Boom.setVisible(true);
        Boom.setLayoutX(mainChar.getLayoutX() + 22);
        System.out.println("start: " + Boom.getLayoutY());

        // Animate Boom moving upwards
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), Boom);
        transition.setByY(-453); // Move the Boom from its initial position (453) to 0
        transition.setOnFinished(event -> {
            // Hide Boom and reset its position after the animation finishes
            Boom.setVisible(false);
            Boom.setLayoutY(mainChar.getLayoutY());

            TranslateTransition reverseTransition = new TranslateTransition(Duration.seconds(0), Boom); // No actual animation, just resetting the position
//            reverseTransition.setToY(0); // Set Y position to 0 (initial position)
//            reverseTransition.play();
//            if (Boom.getLayoutY() - Minion.getLayoutY() <= 100 && Boom.getLayoutX() - Minion.getLayoutX() <= 100){
//                Minion.setVisible(false);
//            }
            System.out.println(Boom.getLayoutX()+", "+ Boom.getLayoutY());
            Boom.setTranslateY(0);
        });
        transition.play();
        System.out.println("finish: " + Boom.getLayoutY());
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
