package gui;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import logic.GameLogic;
import logic.ability.Hitable;
import logic.character.*;
import main.Main;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ForestMapPane extends AnchorPane {
    private static ForestMapPane instance;
    private ImageView coin;
    private ImageView pause;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    int randomIndex;
//    private boolean canShoot; --> set in Punk instead set in here
    private int addScore = 1;
    Punk punk;
    ArrayList<Enemy> enemies;
    public ForestMapPane(){
        //Set Background and Ground
        setBackground(new Background(GameLogic.getBGImage("BG_forest.jpg")));
        ImageView groundImageView = GameLogic.getGroundImage("rock_ground_long.png");
        setTopAnchor(groundImageView,530.0);

        // Set Main Character
        punk = new Punk();
        punk.initPunkAnimation();
        setTopAnchor(punk.getPunkImageView(),453.0);
        punk.setCanShoot(true);
        getChildren().addAll(groundImageView, punk.getPunkImageView(), punk.getPunkShot());
        GameLogic.setIsGameOver(false);

        // Set Coin
        coin = new ImageView(new Image(ClassLoader.getSystemResource("coin.png").toString()));
        coin.setVisible(false);
        getChildren().add(coin);
        coinFall();

        // Set hpBoard and scoreBoard
        hpBoard = HpBoard.getInstance();
        hpBoard.setAlignment(Pos.CENTER_LEFT);
        setTopAnchor(hpBoard,10.0);
        setLeftAnchor(hpBoard,15.0);

        scoreBoard = ScoreBoard.getInstance();
        setRightAnchor(scoreBoard,20.0);
        setTopAnchor(scoreBoard,8.0);
        getChildren().addAll(hpBoard, scoreBoard);

        //Event Handler for KeyPressed
        GameLogic.getPlayerInput(this);

        //Set enemies
        enemies = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            Random random = new Random();
            double randomX = 5.0 + (1080.0 - 5.0)*random.nextDouble();
            double randomY = 10.0 + (70.0 - 10.0)*random.nextDouble();
            System.out.println(i + "-" + "RanX : " + randomX + ", RandY: " + randomY);
            enemies.add(new Minion(randomX, randomY));
            setTopAnchor(enemies.get(i).getImageView(), 50.0);
            enemies.get(i).runAnimation(this);
            getChildren().add(enemies.get(i).getImageView());
        }
        for (int i = 3; i < 6; i++){
            Random random = new Random();
            double randomX = 5.0 + (1080.0 - 5.0)*random.nextDouble();
            double randomY = 10.0 + (70.0 - 10.0)*random.nextDouble();
            System.out.println(i + "-" + "RanX : " + randomX + ", RandY: " + randomY);
            enemies.add(new AttackGhost(randomX, randomY));
            setTopAnchor(enemies.get(i).getImageView(), 50.0);
            enemies.get(i).runAnimation(this);
            getChildren().add(enemies.get(i).getImageView());
            if (enemies.get(i) instanceof AttackGhost){
                getChildren().add(((AttackGhost) enemies.get(i)).getFireBall());
            }
        }
        for (int i = 6; i < 7; i++) {
            Random random = new Random();
            double randomX = 5.0 + (1080.0 - 5.0)*random.nextDouble();
            double randomY = 10.0 + (70.0 - 10.0)*random.nextDouble();
            System.out.println("RanX : "+ randomX +", RandY: "+randomY);
            enemies.add(new MindGhost(randomX, randomY));
            setTopAnchor(enemies.get(i).getImageView(), 50.0);
            enemies.get(i).runAnimation(this);
            getChildren().add(enemies.get(i).getImageView());
//            if (enemies.get(i) instanceof Hitable){
//                ((Hitable) enemies.get(i)).hitDamage(this);
//            }
        }

        // Set exit Button
        pause = new ImageView(new Image(ClassLoader.getSystemResource("exit.png").toString()));
        pause.setFitWidth(110);
        pause.setFitHeight(44);
        setBottomAnchor(pause, 10.0);
        setRightAnchor(pause, 10.0);
        getChildren().add(pause);
        pause.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                GameLogic.setHighscoreEachMap(Constant.getIndexMap("ForestMap"), punk.getScore());
                fadeExitPage();
            }
        });

        //update game
        GameLogic.checkPunkShotHit(this, enemies);
    }

    private void getPlayerInput() {
        Set<KeyCode> pressedKeys = new HashSet<>();
        this.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
            Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(punk.getDelayShoot()), e -> punk.setCanShoot(true)));

            if (pressedKeys.contains(KeyCode.D) && pressedKeys.contains(KeyCode.SPACE)) {
                // Move right and shoot
                System.out.println("D & SPACE");
                if (punk.isCanShoot()){
                    punk.setXPos(punk.getPunkImageView().getLayoutX());
                    punk.shoot();
                    punk.setCanShoot(false);
                    delayShoot.play();
                }
                punk.runRight();
            } else if (pressedKeys.contains(KeyCode.A) && pressedKeys.contains(KeyCode.SPACE)){
                // Move left and shoot
                System.out.println("A & SPACE");
                if (punk.isCanShoot()){
                    punk.setXPos(punk.getPunkImageView().getLayoutX());
                    punk.shoot();
                    punk.setCanShoot(false);
                    delayShoot.play();
                }
                punk.runLeft();
            } else if (pressedKeys.contains(KeyCode.D)) {
                // Move right
                System.out.println("D");
                punk.setXPos(punk.getPunkImageView().getLayoutX());
                System.out.println("XPos : punk.getXPos()");
                punk.runRight();
            } else if (pressedKeys.contains(KeyCode.A)){
                //Move Left
                System.out.println("A");
                punk.setXPos(punk.getPunkImageView().getLayoutX());
                System.out.println("XPos : punk.getXPos()");
                punk.runLeft();
            } else if (pressedKeys.contains(KeyCode.SPACE)) {
                // Shoot
                if (punk.isCanShoot()){
                    System.out.println("Boom!");
                    punk.setXPos(punk.getPunkImageView().getLayoutX());
                    punk.shoot();
                    punk.setCanShoot(false);
                    delayShoot.play();
                }
            }
        });
        this.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
            punk.setPunkAnimation(punk.getPunkIdle(), 4, 4, 48, 48);
        });
    }

//    public boolean isCanShoot() {
//        return canShoot;
//    }
//
//    public void setCanShoot(boolean canShoot) {
//        this.canShoot = canShoot;
//    }

    private void fadeExitPage() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            try {
                System.out.println("Exit !");
                Main.getInstance().changeSceneJava(new MapPane());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        fadeOut.play();
    }

    @Override
    public String toString() {
        return "ForestMap";
    }

    public int getAddScore() {
        return addScore;
    }

    public void setAddScore(int addScore) {
        this.addScore = addScore;
    }

    public int randomIndex() {
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
        randomIndex = randomIndex();
        AnimationTimer FallDown = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long currentTime) {
                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
                System.out.println("playerScore = " + punk.getScore() + " fall : " + coin.getTranslateY());
                System.out.println("playerHp = " + punk.getHp());
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    coin.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
                    coin.setTranslateY(0.0);
                    coin.setFitWidth(30);
                    coin.setFitHeight(30);
                    slideCoin(coin);
                    lastUpdate = currentTime;
                    randomIndex = randomIndex();
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

        fallTransition.setOnFinished(event -> {
            coinImage.setTranslateY(0.0);
            coinImage.setVisible(false);
        });
        fallTransition.play();
    }
    public static ForestMapPane getInstance() {
        if (instance == null) {
            instance = new ForestMapPane();
        }
        return instance;
    }
}