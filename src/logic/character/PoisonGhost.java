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
    private ImageView Poison;
    public PoisonGhost(double x, double y){
        setHp(1);
        setXPos(x);
        setYPos(y);
        poisonGhostImageView = new ImageView(new Image(ClassLoader.getSystemResource("poisonghost.gif").toString()));
//        poisonGhostAnimation = new SpriteAnimation(poisonGhostImageView, Duration.millis(1000), 6, 6, 0, 0, 48, 48);
//        poisonGhostAnimation.setCycleCount(Animation.INDEFINITE);
        poisonGhostImageView.setFitWidth(80);
        poisonGhostImageView.setFitHeight(80);
//        poisonGhostAnimation.play();

        // Set Poison
        Poison = new ImageView(new Image(ClassLoader.getSystemResource("poison.png").toString()));
            // set position at the same x,y of PoisonGhost
        Poison.setLayoutY(getYPos());
        Poison.setLayoutX(getXPos());
        Poison.setVisible(false);
    }
    //need to check if hit or not in the GameLogic.update()
    public void hitDamage(AnchorPane currentPane) {
        System.out.println("Poison Hit");
        Punk.getInstance().setCanShoot(false);
        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> Punk.getInstance().setCanShoot(true)));
        cooldownTimer.play();
    }
    public void runAnimation(AnchorPane currentPane){
        ArrayList<Integer> xPosDown = new ArrayList<>();
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
            private long lastUpdate = 0;
            private long lastMove = 0;
            private double randomStart = poisonGhostImageView.getTranslateX();

            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastMove >= 6_000_000_000L) {
                    double newRandomStart = GameLogic.slideXPos(randomStart, poisonGhostImageView, 4,getImageView().getFitWidth());
                    lastMove = currentTime;
                    randomStart = newRandomStart;
                }
                // Get Position & Set to Minions class
                setXPos(poisonGhostImageView.getTranslateX());
                setYPos(poisonGhostImageView.getTranslateY());
                // get random XPos
                if (xPosDown.size() < 20) {
                    xPosDown.add(xPosDown.size(), (int) GameLogic.randXPos());
                }
                // Check xPos to goDown
                int stay = (int) poisonGhostImageView.getTranslateX();
                if (xPosDown.contains(stay)) {
                    // remove used xPos
                    xPosDown.remove(xPosDown.indexOf(stay));

                    if (currentTime - lastUpdate >= 4_000_000_000L) {
                        goDown(poisonGhostImageView);
                        lastUpdate = currentTime;
                    }
                }
                // Release Power
                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
                int randomIndex = GameLogic.randomIndex();
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    Poison.setLayoutY(getYPos());
                    Poison.setLayoutX(getXPos() + 30);
                    Poison.setTranslateY(50.0);
                    Poison.setFitWidth(40);
                    Poison.setFitHeight(40);
                    GameLogic.slideYPos(Poison, 1);
                    lastUpdate = currentTime;
//                    randomIndex = GameLogic.randomIndex();
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check poison hit
                    GameLogic.checkPoisonHit(currentPane, Poison, getInstance());
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

    public ImageView getPoison() {
        return Poison;
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
