package logic.character;


import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import logic.GameLogic;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AttackGhost extends Enemy { //normal ghost that can attack punk. no hit damage
    private static AttackGhost instance;
    private double xPos;
    private double yPos;
    private ImageView attackGhostImageView;
    private Animation attackghostAnimation;
    private ImageView fireBall;
    public AttackGhost(double x, double y){
        setHp(1);
        setXPos(x);
        setYPos(y);
        attackGhostImageView = new ImageView(new Image(ClassLoader.getSystemResource("ghost2.png").toString()));
        attackghostAnimation = new SpriteAnimation(attackGhostImageView, Duration.millis(1000), 6, 6, 0, 0, 48, 48);
        attackghostAnimation.setCycleCount(Animation.INDEFINITE);
        attackGhostImageView.setFitWidth(80);
        attackGhostImageView.setFitHeight(80);
        attackghostAnimation.play();

        // Set FireBall
        fireBall = new ImageView(new Image(ClassLoader.getSystemResource("fireball.gif").toString()));
        fireBall.setRotate(90); // หมุนให้เป็นรูปแนวตั้ง
        fireBall.setLayoutY(50.0);
        fireBall.setVisible(false);
    }
    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void runAnimation(AnchorPane currentPane) {
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
            private double randomStart = attackGhostImageView.getTranslateX();

            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastMove >= 6_000_000_000L) {
                    double newRandomStart = GameLogic.slideXPos(randomStart, attackGhostImageView, 3);
                    lastMove = currentTime;
                    randomStart = newRandomStart;
                }
                // Get Position & Set to Minions class
                setXPos(attackGhostImageView.getTranslateX());
                setYPos(attackGhostImageView.getTranslateY());
                // Release Power
                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
                int randomIndex = GameLogic.randomIndex();
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    fireBall.setLayoutX(getXPos() + 30);
                    fireBall.setTranslateY(50.0);
                    fireBall.setFitWidth(40);
                    fireBall.setFitHeight(40);
                    GameLogic.slideYPos(fireBall, 1);
                    lastUpdate = currentTime;
//                    randomIndex = GameLogic.randomIndex();
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check fireBall hit
                    GameLogic.checkFireballHit(currentPane, fireBall);
                }
            }
        };
        GhostAnimationTimer.start();
    }

    @Override
    public ImageView getImageView() {
        return attackGhostImageView;
    }

    public void effect(){}

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
    
    public ImageView getFireBall(){
        return fireBall;
    }

    public static AttackGhost getInstance() {
        if (instance == null) {
            instance = new AttackGhost(10.0, 10.0);
        }
        return instance;
    }
}