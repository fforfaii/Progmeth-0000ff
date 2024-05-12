package gui;

import javafx.animation.*;
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
import sound.PlaySound;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class CaveMapPane extends AnchorPane {
    private static CaveMapPane instance;
    private ImageView coin;
    private ImageView exit;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    private ImageView skill;
    Punk punk;
    public CaveMapPane() {
        // Set BGsound
        PlaySound.caveMapBG.play();
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
        HpBoard.updateHpBoard();
        hpBoard.setAlignment(Pos.CENTER_LEFT);
        setTopAnchor(hpBoard,10.0);
        setLeftAnchor(hpBoard,15.0);

        scoreBoard = ScoreBoard.getInstance();
        scoreBoard.setScoreboard();
        setRightAnchor(scoreBoard,20.0);
        setTopAnchor(scoreBoard,8.0);
        getChildren().addAll(hpBoard, scoreBoard);

        //Event Handler for KeyPressed
        GameLogic.getPlayerInput(this);

        //Set enemies
        GameLogic.getEnemies().clear();
        for (int i = 0; i < 3; i++){
            Random random = new Random();
            double randomX = 5.0 + (1080.0 - 5.0)*random.nextDouble();
            double randomY = 10.0 + (70.0 - 10.0)*random.nextDouble();
            System.out.println(i + "-" + "RanX : " + randomX + ", RandY: " + randomY);
            GameLogic.getEnemies().add(new Minion(randomX, randomY));
            setTopAnchor(GameLogic.getEnemies().get(i).getImageView(), 50.0);
            GameLogic.getEnemies().get(i).runAnimation(this, GameLogic.getEnemies().get(i));
            getChildren().add(GameLogic.getEnemies().get(i).getImageView());
        }
        for (int i = 3; i < 6; i++){
            Random random = new Random();
            double randomX = 5.0 + (1080.0 - 5.0)*random.nextDouble();
            double randomY = 10.0 + (70.0 - 10.0)*random.nextDouble();
            System.out.println(i + "-" + "RanX : " + randomX + ", RandY: " + randomY);
            GameLogic.getEnemies().add(new AttackGhost(randomX, randomY));
            setTopAnchor(GameLogic.getEnemies().get(i).getImageView(), 50.0);
            GameLogic.getEnemies().get(i).runAnimation(this, GameLogic.getEnemies().get(i));
            getChildren().add(GameLogic.getEnemies().get(i).getImageView());

            if (GameLogic.getEnemies().get(i) instanceof AttackGhost){
                getChildren().add(((AttackGhost) GameLogic.getEnemies().get(i)).getFireball());
            }
        }

        //Set skills
        skill = new ImageView();
        GameLogic.setSkillImage(skill, GameLogic.randomSkill());
        skill.setVisible(false);
        getChildren().add(skill);
        GameLogic.skillFall(skill, this);

        // Set exit Button
        exit = new ImageView(new Image(ClassLoader.getSystemResource("exit.png").toString()));
        exit.setFitWidth(110);
        exit.setFitHeight(44);
        setBottomAnchor(exit, 10.0);
        setRightAnchor(exit, 10.0);
        getChildren().add(exit);
        exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PlaySound.caveMapBG.stop();
                PlaySound.exit.play();
                GameLogic.setHighScoreEachMap(Constant.getIndexMap("CaveMap"),punk.getScore());
                fadeExitPage();
            }
        });
        exit.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PlaySound.defaultBG.play();
            }
        });

        //update game
        GameLogic.setIsGameOver(false);
        GameLogic.updateGame(this);
    }
    private void fadeExitPage() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            try {
                System.out.println("Exit !");
                PlaySound.stopAllmapBG();
//                PlaySound.death.play();
                Punk.getInstance().setDead(true);
                GameLogic.setIsGameOver(true);
                GameLogic.updateGame(this);
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