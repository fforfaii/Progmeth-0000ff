package gui;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.util.Duration;
import logic.GameLogic;
import logic.character.Punk;
import main.Main;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class JungleMapPane extends AnchorPane {
    private static JungleMapPane instance;
    private ImageView coin;
    private ImageView skill;
    private ImageView pause;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    int randomIndex;
    ArrayList<Integer> xPos_Down = new ArrayList<Integer>(); // for collect rand x position for ghost to go down
    private boolean canShoot;
    private int addScore = 1;
    Punk punk;
    public JungleMapPane() {
        //Set Background and Ground
        setBackground(new Background(GameLogic.getBGImage("BG_jungle.jpg")));
        ImageView groundImageView = GameLogic.getGroundImage("glass_ground_long.png");
        setTopAnchor(groundImageView,515.0);
        this.getChildren().add(groundImageView);

        // Set Main Character
        punk = new Punk();
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

        // Set skills
        skill = new ImageView();
        setSkillImage(GameLogic.randomSkill());
        skill.setVisible(false);
        getChildren().add(skill);
        skillFall(skill);

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
                GameLogic.setHighscoreEachMap(Constant.getIndexMap("JungleMap"),punk.getScore());
                fadeExitPage();
            }
        });

        Set<KeyCode> pressedKeys = new HashSet<>();

        this.setOnKeyPressed(event -> {
            pressedKeys.add(event.getCode());
            Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(punk.getDelayShoot()), e -> canShoot = true));

            if (pressedKeys.contains(KeyCode.D) && pressedKeys.contains(KeyCode.SPACE)) {
                // Move right and shoot
                System.out.println("D & SPACE");
                if (canShoot){
                    punk.setXPos(punk.getPunkImageView().getLayoutX());
                    punk.shoot();
                    canShoot = false;
                    delayShoot.play();
                }
                punk.runRight();
            } else if (pressedKeys.contains(KeyCode.A) && pressedKeys.contains(KeyCode.SPACE)){
                // Move left and shoot
                System.out.println("A & SPACE");
                if (canShoot){
                    punk.setXPos(punk.getPunkImageView().getLayoutX());
                    punk.shoot();
                    canShoot = false;
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
                if (canShoot){
                    System.out.println("Boom!");
                    punk.setXPos(punk.getPunkImageView().getLayoutX());
                    punk.shoot();
                    canShoot = false;
                    delayShoot.play();
                }
            }
        });
        this.setOnKeyReleased(event -> {
            pressedKeys.remove(event.getCode());
            // Idle
            punk.setPunkAnimation(punk.getPunkIdle(), 4, 4, 48, 48);
        });
    }

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
        return "JungleMap";
    }

    public int getAddScore() {
        return addScore;
    }

    public void setAddScore(int addScore) {
        this.addScore = addScore;
    }

    public void setSkillImage(String skillname) {
        // Set icon that fall down
//        ImageView skillimageview = new ImageView();
        switch (skillname) {
            case "Shield":
                skill.setImage(new Image(ClassLoader.getSystemResource("shield.png").toString()));
                skill.setFitHeight(30);
                skill.setFitWidth(30);
                break;
            case "ExtraScore":
                skill.setImage(new Image(ClassLoader.getSystemResource("extrascore.png").toString()));
                skill.setFitHeight(35);
                skill.setFitWidth(35);
                break;
            case "ExtraDamage":
                skill.setImage(new Image(ClassLoader.getSystemResource("extradamage.png").toString()));
                skill.setFitHeight(50);
                skill.setFitWidth(50);
                break;
            case "Heal":
                skill.setImage(new Image(ClassLoader.getSystemResource("heal.png").toString()));
                skill.setFitHeight(50);
                skill.setFitWidth(50);
                break;
            case "MoveFaster":
                skill.setImage(new Image(ClassLoader.getSystemResource("movefaster.png").toString()));
                skill.setFitHeight(50);
                skill.setFitWidth(50);
                break;
            case "Disappear":
                skill.setImage(new Image(ClassLoader.getSystemResource("disappear.png").toString()));
                skill.setFitHeight(65);
                skill.setFitWidth(65);
                break;
        }
    }
    public void skillFall(ImageView skillimageview) {
        // Set falldown movement
        Random random = new Random();
        ArrayList<Double> durations = new ArrayList<>();
        durations.add(3.0);
        durations.add(3.5);
        durations.add(4.0);
        durations.add(4.5);
        durations.add(2.0);
        durations.add(2.5);
        randomIndex = randomIndex(); // for getting duration
        AnimationTimer FallDown = new AnimationTimer() {
            private long lastUpdate = 0;
            private String randSkill = GameLogic.randomSkill();
            @Override
            public void handle(long currentTime) {
                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    skillimageview.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
                    skillimageview.setTranslateY(0.0);
                    skillimageview.setFitWidth(40);
                    skillimageview.setFitHeight(40);
                    slideYPos(skillimageview,2.0, 525);
                    lastUpdate = currentTime;
                    randomIndex = randomIndex();
                    randSkill = GameLogic.randomSkill();
                    setSkillImage(randSkill);
                }
                GameLogic.checkSkillHit(this.toString(),skillimageview,randSkill);
            }
        };
        FallDown.start();
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
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    coin.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
                    coin.setTranslateY(0.0);
                    coin.setFitWidth(30);
                    coin.setFitHeight(30);
                    slideYPos(coin, 1.5, 535);
                    lastUpdate = currentTime;
                    randomIndex = randomIndex();
                }
                GameLogic.checkCoinHit(coin,addScore);
            }
        };
        FallDown.start();
    }
    public void slideYPos(ImageView imageView, double speed, int ground) {
        imageView.setVisible(true);
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(speed), imageView);
        fallTransition.setFromY(0);
        fallTransition.setToY(ground);
        fallTransition.setCycleCount(1);

        fallTransition.setOnFinished(event -> {
            imageView.setTranslateY(0.0);
            imageView.setVisible(false);
        });
        fallTransition.play();
    }
    public double getXPos(ImageView imageView) {
        return imageView.getTranslateX();
    }
    public double getYPos(ImageView imageView) {
        return imageView.getTranslateY();
    }
    public static JungleMapPane getInstance() {
        if (instance == null) {
            instance = new JungleMapPane();
        }
        return instance;
    }
}
