package gui;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;
import logic.GameLogic;
import logic.character.*;
import main.Main;
import sound.Playsound;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CaveMapPane extends AnchorPane {
    private static CaveMapPane instance;
    private ImageView coin;
    private ImageView pause;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    private ImageView skill;
    private ArrayList<Enemy> enemies;
    Punk punk;
    public CaveMapPane() {
        // Set BGsound
        Playsound.CavemapBG.play();
        // Set Ground
        setBackground(new Background(GameLogic.getBGImage("BG_Cave.jpg")));
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
        GameLogic.coinFall(coin);

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
            enemies.add(new PoisonGhost(randomX, randomY));
            setTopAnchor(enemies.get(i).getImageView(), 50.0);
            enemies.get(i).runAnimation(this);
            getChildren().add(enemies.get(i).getImageView());

            if (enemies.get(i) instanceof AttackGhost){
                getChildren().add(((AttackGhost) enemies.get(i)).getFireball());
            }
            if (enemies.get(i) instanceof PoisonGhost){
                getChildren().add(((PoisonGhost) enemies.get(i)).getPoison());
            }
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
                getChildren().add(((AttackGhost) enemies.get(i)).getFireball());
            }
            if (enemies.get(i) instanceof PoisonGhost){
                getChildren().add(((PoisonGhost) enemies.get(i)).getPoison());
            }
        }
//        for (int i = 6; i < 9; i++){
//            Random random = new Random();
//            double randomX = 5.0 + (1080.0 - 5.0)*random.nextDouble();
//            double randomY = 10.0 + (70.0 - 10.0)*random.nextDouble();
//            System.out.println(i + "-" + "RanX : " + randomX + ", RandY: " + randomY);
//            enemies.add(new SlowGhost(randomX, randomY));
//            setTopAnchor(enemies.get(i).getImageView(), 50.0);
//            enemies.get(i).runAnimation(this);
//            getChildren().add(enemies.get(i).getImageView());
//
//            if (enemies.get(i) instanceof AttackGhost){
//                getChildren().add(((AttackGhost) enemies.get(i)).getFireball());
//            }
//            if (enemies.get(i) instanceof PoisonGhost){
//                getChildren().add(((PoisonGhost) enemies.get(i)).getPoison());
//            }
//        }
//        for (int i = 9; i < 12; i++){
//            Random random = new Random();
//            double randomX = 5.0 + (1080.0 - 5.0)*random.nextDouble();
//            double randomY = 10.0 + (70.0 - 10.0)*random.nextDouble();
//            System.out.println(i + "-" + "RanX : " + randomX + ", RandY: " + randomY);
//            enemies.add(new MindGhost(randomX, randomY));
//            setTopAnchor(enemies.get(i).getImageView(), 50.0);
//            enemies.get(i).runAnimation(this);
//            getChildren().add(enemies.get(i).getImageView());
//
//            if (enemies.get(i) instanceof AttackGhost){
//                getChildren().add(((AttackGhost) enemies.get(i)).getFireball());
//            }
//            if (enemies.get(i) instanceof PoisonGhost){
//                getChildren().add(((PoisonGhost) enemies.get(i)).getPoison());
//            }
//        }
//        for (int i = 12; i < 15; i++){
//            Random random = new Random();
//            double randomX = 5.0 + (1080.0 - 5.0)*random.nextDouble();
//            double randomY = 10.0 + (70.0 - 10.0)*random.nextDouble();
//            System.out.println(i + "-" + "RanX : " + randomX + ", RandY: " + randomY);
//            enemies.add(new PoisonGhost(randomX, randomY));
//            setTopAnchor(enemies.get(i).getImageView(), 50.0);
//            enemies.get(i).runAnimation(this);
//            getChildren().add(enemies.get(i).getImageView());
//
//            if (enemies.get(i) instanceof AttackGhost){
//                getChildren().add(((AttackGhost) enemies.get(i)).getFireball());
//            }
//            if (enemies.get(i) instanceof PoisonGhost){
//                getChildren().add(((PoisonGhost) enemies.get(i)).getPoison());
//            }
//        }

        //Set skills
        skill = new ImageView();
        GameLogic.setSkillImage(skill, GameLogic.randomSkill());
        skill.setVisible(false);
        getChildren().add(skill);
        GameLogic.skillFall(skill, this);

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
                Playsound.CavemapBG.stop();
                Playsound.exit.play();
                GameLogic.setHighScoreEachMap(Constant.getIndexMap("CaveMap"),punk.getScore());
                fadeExitPage();
            }
        });
        pause.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Playsound.defaultBG.play();
            }
        });

        //update game
        GameLogic.checkPunkShotHit(this, enemies);
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
        return "CaveMap";
    }
    public static CaveMapPane getInstance() {
        if (instance == null) {
            instance = new CaveMapPane();
        }
        return instance;
    }
}