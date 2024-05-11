package logic.character;

import gui.SpriteAnimation;
import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import logic.GameLogic;
import logic.ability.GoDownable;
import logic.ability.Hitable;
import logic.ability.Imperishable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class SlowGhost extends Enemy implements Imperishable, GoDownable, Hitable { //if hit: slow
    private static SlowGhost instance;
    private int hp;
    private double xPos;
    private double yPos;
    private AnchorPane currentPane;
    private ImageView slowGhostImageView;
    private Animation slowGhostAnimation;
    public SlowGhost(double x, double y){
        setHp(1);
        setXPos(x);
        setYPos(y);
        slowGhostImageView = new ImageView(new Image(ClassLoader.getSystemResource("slowghost.png").toString()));
        slowGhostAnimation = new SpriteAnimation(slowGhostImageView,Duration.millis(1000),5,5,0,0,128,128);
        slowGhostAnimation.setCycleCount(Animation.INDEFINITE);
        slowGhostImageView.setFitWidth(80);
        slowGhostImageView.setFitHeight(80);
        slowGhostAnimation.play();
    }
    //need to check if hit or not in the GameLogic.update()
    @Override
    public void hitDamage(AnchorPane currentPane) {
        Punk.getInstance().setSpeed(Punk.getInstance().getSpeed() - 7.5);
        Timeline cooldownEffect = new Timeline(new KeyFrame(Duration.seconds(4), event -> Punk.getInstance().setSpeed(Punk.getInstance().getSpeed() + 7.5)));
        cooldownEffect.play();
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
                    GameLogic.slideXPos(slowGhostImageView.getTranslateX(), slowGhostImageView, 5,getImageView().getFitWidth());
                    lastUpdate = currentTime;
                }
                // Get Position & Set to Minions class
                setXPos(slowGhostImageView.getTranslateX());
                setYPos(slowGhostImageView.getTranslateY());

                // get random XPos
                if (xPosDown.size() < 20) {
                    xPosDown.add(xPosDown.size(), (int) GameLogic.randXPos());
                }
                // Check xPos to goDown
                int stay = (int) slowGhostImageView.getTranslateX();
                if (xPosDown.contains(stay)) {
                    // remove used xPos
                    xPosDown.remove(xPosDown.indexOf(stay));

                    if (currentTime - lastUpdate >= 4_000_000_000L) {
                        goDown(slowGhostImageView);
                        lastUpdate = currentTime;
                    }
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check ghost hit
                    GameLogic.checkGhostHit(currentPane, getInstance(), slowGhostImageView);
                }
            }
        };
        GhostAnimationTimer.start();
    }

    @Override
    public ImageView getImageView() {
        return slowGhostImageView;
    }
    public Animation getAnimation() {
        return slowGhostAnimation;
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
//    public void effect(){
//        Punk.getInstance().setSpeed(8);
//        Timeline cooldownEffect = new Timeline(new KeyFrame(Duration.seconds(4), event -> Punk.getInstance().setSpeed(15)));
//        cooldownEffect.play();
//    }

    @Override
    public void noDecreaseHP() {
        setHp(getHp() + 1);
    }

    public static SlowGhost getInstance() {
        if (instance == null) {
            instance = new SlowGhost(10.0,0.0);
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

    public AnchorPane getCurrentPane() {
        return currentPane;
    }

    public void setCurrentPane(AnchorPane currentPane) {
        this.currentPane = currentPane;
    }
}
