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
    private AnimationTimer ghostAnimationTimer;

    public Minion(double x, double y){
        setHp(3);
        setXPos(x);
        setYPos(y);
        minionImageView = new ImageView(new Image(ClassLoader.getSystemResource("minion.png").toString()));
        minionAnimation = new SpriteAnimation(minionImageView,Duration.millis(1000),6,6,0,0,48,48);
        minionAnimation.setCycleCount(Animation.INDEFINITE);
        minionImageView.setTranslateX(GameLogic.randXPos());
        minionImageView.setTranslateY(randYPos());
        minionImageView.setFitHeight(80);
        minionImageView.setFitWidth(80);
        minionAnimation.play();
    }
    public void runAnimation(AnchorPane currentPane, Enemy enemy) {
        ArrayList<Integer> xPosDown = new ArrayList<>();
        ghostAnimationTimer = new AnimationTimer() {
            private long startTime = System.nanoTime();
            private long lastSlide = 0;
            private long lastDown = 0;
            @Override
            public void handle(long currentTime) {
                // Slide X axis
                System.out.println("MinionTimer Running");
                if (currentTime - lastSlide >= 7_000_000_000L) {
                    GameLogic.slideXPos(minionImageView.getTranslateX(), minionImageView, 4, GameLogic.randXPos() / 1.2);
                    lastSlide = currentTime;
                }
                // Get Position & Set to class
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

                    if (currentTime - lastDown >= 4_000_000_000L) {
                        goDown(minionImageView);
                        lastDown = currentTime;
                    }
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check ghost hit
                    GameLogic.checkGhostHit(currentPane, enemy, minionImageView);
                }
            }
        };
        ghostAnimationTimer.start();
    }
    @Override
    public void hitDamage(AnchorPane currentPane) {
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    @Override
    public void goDown(ImageView imageView) {
        // Move down
        TranslateTransition translateYTransitionDown = new TranslateTransition(Duration.seconds(2), imageView);
        translateYTransitionDown.setFromY(imageView.getTranslateY());
        translateYTransitionDown.setToY(460);
        translateYTransitionDown.setCycleCount(1);
        translateYTransitionDown.setAutoReverse(true);

        // Move up
        TranslateTransition translateYTransitionUp = new TranslateTransition(Duration.seconds(2), imageView);
        translateYTransitionUp.setFromY(460);
        randYPos();
        translateYTransitionUp.setToY(randYPos());
        imageView.setTranslateY(randYPos());
        translateYTransitionUp.setCycleCount(1);
        translateYTransitionUp.setAutoReverse(true);
        translateYTransitionUp.setDelay(Duration.seconds(0)); // No Delay before moving up

        SequentialTransition sequentialTransition = new SequentialTransition(translateYTransitionDown, translateYTransitionUp);
        sequentialTransition.play();
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
        if (hp<0) {
            this.hp = 0;
        } else if (hp>3) {
            this.hp = 3;
        } else {
            this.hp = hp;
        }
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

    public AnimationTimer getAnimationTimer() {
        return ghostAnimationTimer;
    }

    public static Minion getInstance() {
        if (instance == null) {
            instance = new Minion(10.0, 0.0);
        }
        return instance;
    }
}
