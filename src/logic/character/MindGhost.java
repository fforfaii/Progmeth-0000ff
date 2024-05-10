package logic.character;


import gui.ForestMapPane;
import gui.SpriteAnimation;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import logic.GameLogic;
import logic.ability.GoDownable;
import logic.ability.Hitable;
import javafx.geometry.Rectangle2D;
import logic.ability.Imperishable;

import java.awt.color.ICC_ColorSpace;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MindGhost extends Enemy implements Hitable, GoDownable, Imperishable { //if hit: inverted control
    private static MindGhost instance;
    private int hp;
    private double xPos;
    private double yPos;
    private ImageView mindGhostImageView;
    private Animation mindGhostAnimation;
    private AnchorPane currentPane;
    public MindGhost(double x, double y){
        setHp(1);
        setXPos(x);
        setYPos(y);
        mindGhostImageView = new ImageView(new Image(ClassLoader.getSystemResource("mindghost.png").toString()));
        mindGhostAnimation = new SpriteAnimation(mindGhostImageView,Duration.millis(1000),6,6,0,0,48,48);
        mindGhostAnimation.setCycleCount(Animation.INDEFINITE);
        mindGhostImageView.setFitWidth(80);
        mindGhostImageView.setFitHeight(80);
        mindGhostAnimation.play();
    }
    public void runAnimation(AnchorPane currentPane){
        ArrayList<Integer> xPosDown = new ArrayList<>();
        AnimationTimer GhostAnimationTimer = new AnimationTimer() {
            private long startTime = System.nanoTime();
            private long lastUpdate = 0;
            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastUpdate >= 6_000_000_000L) {
                    GameLogic.slideXPos(mindGhostImageView.getTranslateX(), mindGhostImageView, 5,getImageView().getFitWidth() + 10.0);
                    lastUpdate = currentTime;
                }
                // Get Position & Set to Minions class
                setXPos(mindGhostImageView.getTranslateX());
                setYPos(mindGhostImageView.getTranslateY());

                // get random XPos
                if (xPosDown.size() < 20) {
                    xPosDown.add(xPosDown.size(), (int) GameLogic.randXPos());
                }
                // Check xPos to goDown
                int stay = (int) mindGhostImageView.getTranslateX();
                if (xPosDown.contains(stay)) {
                    // remove used xPos
                    xPosDown.remove(xPosDown.indexOf(stay));
                    System.out.println("stay = " + stay + " go down !!!!!!!!");

                    if (currentTime - lastUpdate >= 4_000_000_000L) {
                        goDown(mindGhostImageView);
                        lastUpdate = currentTime;
                    }
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check ghost hit
                    GameLogic.checkGhostHit(currentPane, getInstance(),mindGhostImageView);
                }
            }
        };
        GhostAnimationTimer.start();
    }

    @Override
    public ImageView getImageView() {
        return mindGhostImageView;
    }

    public Animation getAnimation() {
        return mindGhostAnimation;
    }

    public static MindGhost getInstance() {
        if (instance == null) {
            instance = new MindGhost(10.0,0.0);
        }
        return instance;
    }

    @Override
    public void goDown(ImageView imageView) {
        // Move down
        TranslateTransition translateYTransitionDown = new TranslateTransition(Duration.seconds(2), imageView);
        translateYTransitionDown.setFromY(0);
        translateYTransitionDown.setToY(460);
        translateYTransitionDown.setCycleCount(1);
        translateYTransitionDown.setAutoReverse(true);

        // Move up
        TranslateTransition translateYTransitionUp = new TranslateTransition(Duration.seconds(2), imageView);
        translateYTransitionUp.setFromY(460);
        translateYTransitionUp.setToY(0);
        translateYTransitionUp.setCycleCount(1);
        translateYTransitionUp.setAutoReverse(true);
        translateYTransitionUp.setDelay(Duration.seconds(0)); // No Delay before moving up

        SequentialTransition sequentialTransition = new SequentialTransition(translateYTransitionDown, translateYTransitionUp);
        sequentialTransition.play();
    }
//    //need to check if hit or not in the GameLogic.update()
//    @Override
//<<<<<<< HEAD
//    public void hitDamage() {
//        Node currentmap = Constant.getinstanceMap(GameLogic.getCurrentMap());
//        Punk punk = Punk.getInstance();
//
//        Set<KeyCode> pressedKeys = new HashSet<>();
//        currentmap.setOnKeyPressed(event -> {
//            pressedKeys.add(event.getCode());
//            Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(punk.getDelayShoot()), e -> punk.setCanShoot(true)));
//
//            if (pressedKeys.contains(KeyCode.A) && pressedKeys.contains(KeyCode.SPACE)) {
//                // Move right and shoot
//                if (punk.isCanShoot()){
//                    punk.setXPos(punk.getPunkImageView().getLayoutX());
//                    punk.shoot();
//                    punk.setCanShoot(false);
//                    delayShoot.play();
//                }
//                punk.runRight();
//            } else if (pressedKeys.contains(KeyCode.D) && pressedKeys.contains(KeyCode.SPACE)){
//                // Move left and shoot
//                if (punk.isCanShoot()){
//                    punk.setXPos(punk.getPunkImageView().getLayoutX());
//                    punk.shoot();
//                    punk.setCanShoot(false);
//                    delayShoot.play();
//                }
//                punk.runLeft();
//            } else if (pressedKeys.contains(KeyCode.A)) {
//                // Move right
//                punk.setXPos(punk.getPunkImageView().getLayoutX());
//                System.out.println("XPos : punk.getXPos()");
//                punk.runRight();
//            } else if (pressedKeys.contains(KeyCode.D)){
//                //Move Left
//                punk.setXPos(punk.getPunkImageView().getLayoutX());
//                System.out.println("XPos : punk.getXPos()");
//                punk.runLeft();
//            } else if (pressedKeys.contains(KeyCode.SPACE)) {
//                // Shoot
//                if (punk.isCanShoot()){
//                    System.out.println("Boom!");
//                    punk.setXPos(punk.getPunkImageView().getLayoutX());
//                    punk.shoot();
//                    punk.setCanShoot(false);
//                    delayShoot.play();
//                }
//            }
//        });
//        currentmap.setOnKeyReleased(event -> {
//            pressedKeys.remove(event.getCode());
//            punk.setPunkAnimation(punk.getPunkIdle(), 4, 4, 48, 48);
//        });
//    }
    @Override
    public void hitDamage(AnchorPane currentPane) {
        if (Punk.getInstance().isImmortalDelay() || Punk.getInstance().isMindGhostDelay()) {
            return;
        }
        GameLogic.getContinuousMovement().stop();
        GameLogic.reversePlayerInput(currentPane);
        Timeline effectDuration = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            GameLogic.getReverseContinuousMovement().stop();
            GameLogic.getContinuousMovement().play();
        }));
        effectDuration.play();
    }
//||||||| 2d12347
//    public void hitDamage() {
//        Node currentmap = Constant.getinstanceMap(GameLogic.getCurrentMap());
//        Punk punk = Punk.getInstance();
//
//        Set<KeyCode> pressedKeys = new HashSet<>();
//        currentmap.setOnKeyPressed(event -> {
//            pressedKeys.add(event.getCode());
//            Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(punk.getDelayShoot()), e -> punk.setCanShoot(true)));
//
//            if (pressedKeys.contains(KeyCode.A) && pressedKeys.contains(KeyCode.SPACE)) {
//                // Move right and shoot
//                if (punk.isCanShoot()){
//                    punk.setXPos(punk.getPunkImageView().getLayoutX());
//                    punk.shoot();
//                    punk.setCanShoot(false);
//                    delayShoot.play();
//                }
//                punk.runRight();
//            } else if (pressedKeys.contains(KeyCode.D) && pressedKeys.contains(KeyCode.SPACE)){
//                // Move left and shoot
//                if (punk.isCanShoot()){
//                    punk.setXPos(punk.getPunkImageView().getLayoutX());
//                    punk.shoot();
//                    punk.setCanShoot(false);
//                    delayShoot.play();
//                }
//                punk.runLeft();
//            } else if (pressedKeys.contains(KeyCode.A)) {
//                // Move right
//                punk.setXPos(punk.getPunkImageView().getLayoutX());
//                System.out.println("XPos : punk.getXPos()");
//                punk.runRight();
//            } else if (pressedKeys.contains(KeyCode.D)){
//                //Move Left
//                punk.setXPos(punk.getPunkImageView().getLayoutX());
//                System.out.println("XPos : punk.getXPos()");
//                punk.runLeft();
//            } else if (pressedKeys.contains(KeyCode.SPACE)) {
//                // Shoot
//                if (punk.isCanShoot()){
//                    System.out.println("Boom!");
//                    punk.setXPos(punk.getPunkImageView().getLayoutX());
//                    punk.shoot();
//                    punk.setCanShoot(false);
//                    delayShoot.play();
//                }
//            }
//        });
//        currentmap.setOnKeyReleased(event -> {
//            pressedKeys.remove(event.getCode());
//            punk.setPunkAnimation(punk.getPunkIdle(), 4, 4, 48, 48);
//        });
//=======
//    public void hitDamage() {
//        ((ForestMapPane) currentPane).setMindControl(true);
//        Timeline disableMindControlTimer = new Timeline(new KeyFrame(Duration.seconds(4), e ->
//                ((ForestMapPane) currentPane).setMindControl(false)));
//        disableMindControlTimer.play();
//>>>>>>> 38b75a3f4b6ea31eb30e132f1f71618ba33f4467
    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public AnchorPane getCurrentPane() {
        return currentPane;
    }

    public void setCurrentPane(AnchorPane currentPane) {
        this.currentPane = currentPane;
    }

    @Override
    public void noDecreaseHP() {
        setHp(getHp() + 1);
    }
}
