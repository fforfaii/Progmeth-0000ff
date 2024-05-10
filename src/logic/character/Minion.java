package logic.character;

import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import logic.GameLogic;
import logic.ability.GoDownable;
import logic.ability.Hitable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Minion extends Enemy implements Hitable, GoDownable { //can do nothing but player needs to avoid
    private static Minion instance;
    private int hp;
    private double xPos;
    private double yPos;
    private ImageView minionImageView;
    private Animation minionAnimation;

    public Minion(double x, double y){
        setHp(1);
        setXPos(x);
        setYPos(y);
        minionImageView = new ImageView(new Image(ClassLoader.getSystemResource("minion.png").toString()));
        minionAnimation = new SpriteAnimation(minionImageView,Duration.millis(1000),6,6,0,0,48,48);
        minionAnimation.setCycleCount(Animation.INDEFINITE);
        minionImageView.setFitHeight(80);
        minionImageView.setFitWidth(80);
        minionAnimation.play();
    }
    public void runAnimation(AnchorPane currentPane) {
        ArrayList<Integer> xPosDown = new ArrayList<>();
        AnimationTimer GhostAnimationTimer = new AnimationTimer() {
            private long startTime = System.nanoTime();
            private long lastUpdate = 0;
            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastUpdate >= 6_000_000_000L) {
                    GameLogic.slideXPos(minionImageView.getTranslateX(), minionImageView, 4,getImageView().getFitWidth());
                    lastUpdate = currentTime;
                }
                // Get Position & Set to Minions class
                setXPos(minionImageView.getTranslateX());
                setYPos(minionImageView.getTranslateY());

                // get random XPos
                if (xPosDown.size() < 20) {
                    xPosDown.add(xPosDown.size(), (int) GameLogic.randXPos());
                }
                // Check xPos to goDown
                int stay = (int) minionImageView.getTranslateX();
                if (xPosDown.contains(stay)) {
                    // remove used xPos
                    xPosDown.remove(xPosDown.indexOf(stay));
                    System.out.println("stay = " + stay + " go down !!!!!!!!");

                    if (currentTime - lastUpdate >= 4_000_000_000L) {
                        goDown(minionImageView);
                        lastUpdate = currentTime;
                    }
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check ghost hit
                    GameLogic.checkGhostHit(currentPane, getInstance());
                }
            }
        };
        GhostAnimationTimer.start();
    }
    public ImageView getImageView() {
        return minionImageView;
    }

    public Animation getAnimation(){
        return minionAnimation;
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
        this.xPos = xPos + 10;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }
    @Override
    public void hitDamage() {
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
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

    public static Minion getInstance() {
        if (instance == null) {
            instance = new Minion(10.0, 0.0);
        }
        return instance;
    }
}
