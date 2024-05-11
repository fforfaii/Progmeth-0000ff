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
        mindGhostImageView.setTranslateX(GameLogic.randXPos());
        mindGhostImageView.setTranslateY(randYPos());
        mindGhostImageView.setFitWidth(80);
        mindGhostImageView.setFitHeight(80);
        mindGhostAnimation.play();
    }
    public void runAnimation(AnchorPane currentPane){
        ArrayList<Integer> xPosDown = new ArrayList<>();
        AnimationTimer GhostAnimationTimer = new AnimationTimer() {
            private long startTime = System.nanoTime();
            private long lastSlide = 0;
            private long lastDown = 0;
            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastSlide >= 4_000_000_000L) {
                    GameLogic.slideXPos(mindGhostImageView.getTranslateX(), mindGhostImageView, 2, GameLogic.randXPos() / 1.2);
                    lastSlide = currentTime;
                }
                // Get Position & Set to class
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

                    if (currentTime - lastDown >= 4_000_000_000L) {
                        goDown(mindGhostImageView);
                        lastDown = currentTime;
                    }
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check ghost hit
                    GameLogic.checkGhostHit(currentPane, getInstance(), mindGhostImageView);
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
    @Override
    public void hitDamage(AnchorPane currentPane) {
        if (Punk.getInstance().isMindGhostDelay()) {
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
