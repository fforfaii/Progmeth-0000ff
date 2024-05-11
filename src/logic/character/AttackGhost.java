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
    private int hp;
    private double xPos;
    private double yPos;
    private ImageView attackGhostImageView;
    private Animation attackghostAnimation;
    private ImageView fireball;
    public AttackGhost(double x, double y){
        setHp(3);
        setXPos(x);
        setYPos(y);
        attackGhostImageView = new ImageView(new Image(ClassLoader.getSystemResource("attackghost.png").toString()));
        attackghostAnimation = new SpriteAnimation(attackGhostImageView, Duration.millis(1000), 6, 6, 0, 0, 48, 48);
        attackghostAnimation.setCycleCount(Animation.INDEFINITE);
        attackGhostImageView.setTranslateX(GameLogic.randXPos());
        attackGhostImageView.setTranslateY(randYPos() / 1.8);
        attackGhostImageView.setFitWidth(80);
        attackGhostImageView.setFitHeight(80);
        attackghostAnimation.play();

        // Set FireBall
        fireball = new ImageView(new Image(ClassLoader.getSystemResource("fireball.gif").toString()));
        fireball.setRotate(90); // หมุนให้เป็นรูปแนวตั้ง
        fireball.setLayoutY(50.0);
        fireball.setVisible(false);
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
            private long lastShoot = 0;
            private long lastMove = 0;

            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastMove >= 5_000_000_000L) {
                    GameLogic.slideXPos(attackGhostImageView.getTranslateX(), attackGhostImageView, 3, GameLogic.randXPos() / 1.2);
                    lastMove = currentTime;
                }
                // Get Position & Set to class
                setXPos(attackGhostImageView.getTranslateX());
                setYPos(attackGhostImageView.getTranslateY());

                // Release Power
                double elapsedTimeSeconds = (currentTime - lastShoot) / 1_000_000_000.0;
                int randomIndex = GameLogic.randomIndex();
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    fireball.setLayoutX(getXPos() + 30);
                    fireball.setTranslateY(attackGhostImageView.getTranslateY() + 20);
                    fireball.setVisible(false);
                    fireball.setFitWidth(40);
                    fireball.setFitHeight(40);
                    GameLogic.slideYPos(fireball, 1, attackGhostImageView.getTranslateY() + 20, 535);
                    lastShoot = currentTime;
                }

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check fireball hit
                    GameLogic.checkFireballHit(currentPane, fireball, getInstance());
                }
            }
        };
        GhostAnimationTimer.start();
    }

    @Override
    public ImageView getImageView() {
        return attackGhostImageView;
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
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }
    
    public ImageView getFireball(){
        return fireball;
    }
    public static AttackGhost getInstance() {
        if (instance == null) {
            instance = new AttackGhost(10.0, 10.0);
        }
        return instance;
    }
}