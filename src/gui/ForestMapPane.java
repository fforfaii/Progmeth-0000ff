package gui;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.GameLogic;
import logic.character.Punk;
import main.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class ForestMapPane extends AnchorPane {
    private static ForestMapPane instance;
    private ImageView coin;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    int randomIndex;
    ArrayList<Integer> xPos_Down = new ArrayList<Integer>(); // for collect rand x position for ghost to go down
    private boolean canShoot;
    Punk punk;
    public ForestMapPane(){
        //Set Background and Ground
        setBackground(new Background(GameLogic.getBGImage("BG_forest1.jpg")));
        ImageView groundImageView = GameLogic.getGroundImage("rock_ground_long.png");
        setTopAnchor(groundImageView,530.0);
        this.getChildren().add(groundImageView);

        // Set Main Character
        punk = Punk.getInstance();
        punk.initPunkAnimation();
        setTopAnchor(punk.getPunkImageView(),453.0);
        canShoot = true;
        getChildren().addAll(punk.getPunkImageView(), punk.getPunkShot());

        // Set Coin
        coin = new ImageView(new Image(ClassLoader.getSystemResource("coin.png").toString()));
        coin.setVisible(false);
        getChildren().add(coin);
        coinFall();

        // Set heart
        hpBoard = HpBoard.getInstance();
        hpBoard.setAlignment(Pos.CENTER_LEFT);
        setTopAnchor(hpBoard,10.0);
        setLeftAnchor(hpBoard,15.0);
        getChildren().add(hpBoard);

        // Set ScoreBoard ( must stay after set punk )
        scoreBoard = ScoreBoard.getInstance();
        setRightAnchor(scoreBoard,20.0);
        setTopAnchor(scoreBoard,8.0);
        getChildren().add(scoreBoard);

        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        // go left
                        System.out.println("A");
                        System.out.println(punk.getPunkImageView().getLayoutX());
                        punk.runLeft();
                        break;
                    case D:
                        // go right
                        System.out.println("D");
                        System.out.println(punk.getPunkImageView().getLayoutX());
                        punk.runRight();
                        break;
                    case SPACE:
                        // release power
                        if (!canShoot){
                            return;
                        }
                        punk.shoot();
                        System.out.println("Boom!");
                        canShoot = false;
                        Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(3), event -> canShoot = true));
                        delayShoot.play();
                        break;
                }
                punk.setXPos(punk.getPunkImageView().getLayoutX());
                System.out.println(punk.getXPos());
            }
        });
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) { //Idle
                punk.setPunkAnimation(punk.getPunkIdle(),4,4,48,48);
            }
        });
//        GameLogic.checkPunkShotHit(this, minion);
//        GameLogic.checkPunkShotHit(this, attackGhost);
    }
//    public void deleteHeart(Node node) {
//        int size = hpBoard.getChildren().size();
//        System.out.println("Size before deletion: " + size);
//        if (size!=0) hpBoard.getChildren().remove(size-1);
//        System.out.println(punk.getHp());
//        if (punk.isDead()){
//            return;
//        }
//        if (punk.getHp() == 0) {
//            punk.setDead(true);
//            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), node);
//            fadeOut.setFromValue(1.0);
//            fadeOut.setToValue(0.0);
//            fadeOut.setOnFinished(event -> {
//                try {
//                    System.out.println("Game Over !");
//                    Main.getInstance().changeSceneJava(GameOverPane.getInstance());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            });
//            fadeOut.play();
//        }
//    }
//    public void addHeart() {
//        if (hpBoard.getChildren().size() <= 3){
//            ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
//            hp.setFitHeight(20);
//            hp.setFitWidth(25);
//            hpBoard.getChildren().add(hp);
//        }
//    }
    public int randomIndexForCoinFall() {
        Random random = new Random();
        return random.nextInt(6);
    }
    public void coinFall() {
        Random random = new Random();
        ArrayList<Double> durations = new ArrayList<>();
        durations.add(3.0);
        durations.add(3.5);
        durations.add(4.0);
        durations.add(4.5);
        durations.add(2.0);
        durations.add(2.5);
        randomIndex = randomIndexForCoinFall();
        AnimationTimer FallDown = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long currentTime) {
                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
//                System.out.println(randomIndexforCoinFall());
                System.out.println("playerScore = " + punk.getScore() + " fall : " + coin.getTranslateY());
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    coin.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
                    coin.setTranslateY(0.0);
                    coin.setFitWidth(30);
                    coin.setFitHeight(30);
                    slideCoin(coin);
                    lastUpdate = currentTime;
                    randomIndex = randomIndexForCoinFall();
                }
                GameLogic.checkCoinHit(coin);
            }
        };
        FallDown.start();
    }
    public void slideCoin(ImageView coinImage) {
        coinImage.setVisible(true);
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1.5), coin);
        fallTransition.setFromY(0);
        fallTransition.setToY(545);
        fallTransition.setCycleCount(1);
        // ต้องมี check mainChar can get coin ?

        fallTransition.setOnFinished(event -> {
            coinImage.setTranslateY(0.0);
            coinImage.setVisible(false);
        });
        fallTransition.play();
    }
    public double getXPos(ImageView imageView) {
        return imageView.getTranslateX();
    }
    public double getYPos(ImageView imageView) {
        return imageView.getTranslateY();
    }
    public static ForestMapPane getInstance() {
        if (instance == null) {
            instance = new ForestMapPane();
        }
        return instance;
    }
}