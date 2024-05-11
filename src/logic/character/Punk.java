package logic.character;

import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Punk {
    double xPos;
    double yPos;
    private static Punk instance;
    private int hp;
    private int score;
    private int atk;
    private int speed;
    private double delayShoot;
    private double punkShotXPos;
    private double punkShotYPos;
    private boolean isDead;
    private boolean canHit;
    private boolean canShoot;
    private boolean isShield;
    private ImageView punkImageView; //mainChar
    private Animation punkAnimation; //mainAni
    private Image runLeft; //runLeft
    private Image runRight; //runRight
    private Image punkGun; //Gun
    private Image punkIdle; //Idle
    private ImageView punkShot; //Boom
    private int scorePerCoin;
    private boolean immortalDelay;
    private boolean mindGhostDelay;
    public Punk() {
        // always start at (0.0,453.0)
        setXPos(0); // ระยะห่างจากขอบซ้ายของ window
        setYPos(453.0); // ระยะห่างจากขอบบนของ window
        this.atk = 1;
        setDead(false);
        setScore(0);
        setHp(3);
        setSpeed(15);
        setDelayShoot(0.5);
        setScorePerCoin(1);
        setImmortalDelay(false);
        setCanHit(true);
        setCanShoot(true);
        setShield(false);
        setMindGhostDelay(false);
        punkImageView = new ImageView(new Image(ClassLoader.getSystemResource("Punk_idle.png").toString()));
        punkAnimation = new SpriteAnimation(punkImageView, Duration.millis(1000),4,4,0,0,48,48);
        runLeft = new Image(ClassLoader.getSystemResource("Punk_runleft.png").toString());
        runRight = new Image(ClassLoader.getSystemResource("Punk_runright.png").toString());
        punkGun = new Image(ClassLoader.getSystemResource("Punk_Gun_Resize.png").toString());
        punkIdle = new Image(ClassLoader.getSystemResource("Punk_idle.png").toString());
        punkShot = new ImageView(new Image(ClassLoader.getSystemResource("gun1.png").toString()));
        punkShot.setFitWidth(12);
        punkShot.setFitHeight(72);
        punkShot.setVisible(false);
        instance = this;
    }
    public ImageView getShotImageView() {
        return punkShot;
    }
    public void setShotImageView(String picname) {
        punkShot.setImage(new Image(ClassLoader.getSystemResource(picname).toString()));
        punkShot.setFitWidth(12);
        punkShot.setFitHeight(72);
        punkShot.setVisible(false);
    }
    public void initPunkAnimation(){
        punkAnimation.setCycleCount(Animation.INDEFINITE);
        punkImageView.setFitWidth(100);
        punkImageView.setFitHeight(100);
        punkAnimation.play();
    }
    public void setPunkAnimation(Image Image, int count, int column, int width, int height){
        punkImageView.setImage(Image);
        SpriteAnimation.getInstance().setCount(count);
        SpriteAnimation.getInstance().setColumns(column);
        SpriteAnimation.getInstance().setWidth(width);
        SpriteAnimation.getInstance().setHeight(height);
        punkAnimation.setCycleCount(Animation.INDEFINITE);
        punkImageView.setFitWidth(100);
        punkImageView.setFitHeight(100);
        punkAnimation.play();
    }
    public void runLeft(){
        if (punkImageView.getLayoutX() >= 5.0) {
            punkImageView.setLayoutX(punkImageView.getLayoutX() - getSpeed());
        }
        setPunkAnimation(runLeft,6,6,48,48);
    }
    public void runRight(){
        if (punkImageView.getLayoutX() <= 1080) {
            punkImageView.setLayoutX(punkImageView.getLayoutX() + getSpeed());
        }
        setPunkAnimation(runRight,6,6,48,48);
    }
    public void shoot(){
        getPunkImageView().setImage(getPunkGun());
        getPunkImageView().setViewport(new javafx.geometry.Rectangle2D(96, 0, 48, 48));
        // Set Boom when start Shooting
        punkShot.setVisible(true);
        punkShot.setFitWidth(24);
        punkShot.setFitHeight(120);
        punkShot.setLayoutX(getXPos() + 22);
        setPunkShotXPos(punkShot.getLayoutX());
        setPunkShotYPos(punkShot.getLayoutY());
        punkShot.setLayoutY(453);

        // Animate Boom moving upwards
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), punkShot);
        transition.setByY(-453); // Move the Boom from its initial position (453) to 0
        transition.setOnFinished(event -> {
            // Hide Boom and reset its position after the animation finishes
            punkShot.setVisible(false);
            punkShot.setLayoutY(punkImageView.getLayoutY());

            setPunkShotXPos(punkShot.getLayoutX());
            setPunkShotYPos(punkShot.getLayoutY());
            punkShot.setTranslateY(0);
        });
        transition.play();
        setPunkShotXPos(punkShot.getLayoutX());
        setPunkShotYPos(punkShot.getTranslateY());
    }
    public static Punk getInstance() {
        if (instance == null) {
            instance = new Punk();
        }
        return instance;
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp >= 3){
            this.hp = 3;
        } else if (this.hp <= 0){
            this.hp = 0;
        } else {
            this.hp = hp;
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = Math.max(0, score);
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = Math.max(0, speed);
    }
    public double getDelayShoot() {
        return delayShoot;
    }

    public void setDelayShoot(double delayShoot) {
        this.delayShoot = delayShoot;
    }

    public double getPunkShotXPos() {
        return punkShotXPos;
    }

    public void setPunkShotXPos(double punkShotXPos) {
        this.punkShotXPos = punkShotXPos;
    }

    public double getPunkShotYPos() {
        return punkShotYPos;
    }

    public void setPunkShotYPos(double punkShotYPos) {
        this.punkShotYPos = punkShotYPos;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public ImageView getPunkImageView() {
        return punkImageView;
    }

    public void setPunkImageView(ImageView punkImageView) {
        this.punkImageView = punkImageView;
    }

    public Animation getPunkAnimation() {
        return punkAnimation;
    }

    public void setPunkAnimation(Animation punkAnimation) {
        this.punkAnimation = punkAnimation;
    }
    public Image getPunkGun() {
        return punkGun;
    }
    public void setPunkGun(Image punkGun) {
        this.punkGun = punkGun;
    }
    public Image getPunkIdle() {
        return punkIdle;
    }
    public void setPunkIdle(Image punkIdle) {
        this.punkIdle = punkIdle;
    }
    public ImageView getPunkShot() {
        return punkShot;
    }
    public void setPunkShot(ImageView punkShot) {
        this.punkShot = punkShot;
    }

    public int getScorePerCoin() {
        return scorePerCoin;
    }

    public void setScorePerCoin(int scorePerCoin) {
        this.scorePerCoin = scorePerCoin;
    }

    public boolean isImmortalDelay() {
        return immortalDelay;
    }

    public void setImmortalDelay(boolean immortalDelay) {
        this.immortalDelay = immortalDelay;
    }

    public boolean isCanHit() {
        return canHit;
    }

    public void setCanHit(boolean canbeHit) {
        this.canHit = canbeHit;
    }

    public boolean isCanShoot() {
        return canShoot;
    }

    public void setCanShoot(boolean canShoot) {
        this.canShoot = canShoot;
    }

    public boolean isShield() {
        return isShield;
    }

    public void setShield(boolean shield) {
        isShield = shield;
    }

    public boolean isMindGhostDelay() {
        return mindGhostDelay;
    }

    public void setMindGhostDelay(boolean mindGhostDelay) {
        this.mindGhostDelay = mindGhostDelay;
    }
}