package logic.character;


import gui.SpriteAnimation;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import logic.GameLogic;
import logic.ability.GoDownable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PoisonGhost extends Enemy implements GoDownable { //if punk get poison: cannot attack for 3 secs
    private static PoisonGhost instance;
    private int hp;
    private double xPos;
    private double yPos;
    private ImageView poisonGhostImageView;
//    private Animation poisonGhostAnimation;
    private ImageView poison;
    public PoisonGhost(double x, double y){
        setHp(1);
        setXPos(x);
        setYPos(y);
        poisonGhostImageView = new ImageView(new Image(ClassLoader.getSystemResource("poisonghost.gif").toString()));
//        poisonGhostAnimation = new SpriteAnimation(poisonGhostImageView, Duration.millis(1000), 6, 6, 0, 0, 48, 48);
//        poisonGhostAnimation.setCycleCount(Animation.INDEFINITE);
        poisonGhostImageView.setFitWidth(80);
        poisonGhostImageView.setFitHeight(80);
        poisonGhostImageView.setTranslateX(GameLogic.randXPos());
//        poisonGhostImageView.setTranslateY(randYPos() / 1.8);
//        poisonGhostAnimation.play();

        // Set Poison
        poison = new ImageView(new Image(ClassLoader.getSystemResource("poison.png").toString()));
            // set position at the same x,y of poisonGhost

        poison.setLayoutY(getYPos());
        poison.setTranslateY(poisonGhostImageView.getTranslateY());
        poison.setLayoutX(getXPos());
        poison.setVisible(false);
        instance = this;
    }
    //need to check if hit or not in the GameLogic.update()
    public void hitDamage(AnchorPane currentPane) {
        Punk.getInstance().setCanShoot(false);
        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(10), event -> Punk.getInstance().setCanShoot(true)));
        cooldownTimer.play();
    }
    public void runAnimation(AnchorPane currentPane, Enemy enemy){
        ArrayList<Double> durations = new ArrayList<>();
        durations.add(2.0);
        durations.add(3.0);
        durations.add(1.5);
        durations.add(3.5);
        durations.add(2.5);
        durations.add(4.0);
//        int randomIndex = GameLogic.randomIndex();
        AnimationTimer GhostAnimationTimer = new AnimationTimer() {
            private long startTime = System.nanoTime();
            private long lastShoot = 0;
            private long lastMove = 0;

            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastMove >= 5_000_000_000L) {
                    GameLogic.slideXPos(poisonGhostImageView.getTranslateX(), poisonGhostImageView, 3, GameLogic.randXPos() / 1.2);
                    lastMove = currentTime;
                }
                // Get Position & Set to class
                setXPos(poisonGhostImageView.getTranslateX());
                setYPos(poisonGhostImageView.getTranslateY());

                // Release Power
                double elapsedTimeSeconds = (currentTime - lastShoot) / 1_000_000_000.0;
                int randomIndex = GameLogic.randomIndex();
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    poison.setLayoutX(getXPos() + 30);
                    poison.setTranslateY(poisonGhostImageView.getTranslateY() + 20);
                    poison.setVisible(false);
                    poison.setFitWidth(40);
                    poison.setFitHeight(40);
                    GameLogic.slideYPos(poison, 1, poisonGhostImageView.getTranslateY() + 20, 535);
                    lastShoot = currentTime;
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check poison hit
                    GameLogic.checkPoisonHit(currentPane, poison, (PoisonGhost) enemy);
                }
            }
        };
        GhostAnimationTimer.start();
    }

    @Override
    public ImageView getImageView() {
        return poisonGhostImageView;
    }
    @Override
    public int getHp() {
        return hp;
    }

    @Override
    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
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

    public ImageView getPoison() {
        return poison;
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

    public static PoisonGhost getInstance() {
        if (instance == null) {
            instance = new PoisonGhost(10.0, 10.0);
        }
        return instance;
    }
}
