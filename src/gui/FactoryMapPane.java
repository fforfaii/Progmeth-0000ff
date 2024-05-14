package gui;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.util.Duration;
import logic.GameLogic;
import logic.character.*;
import main.Main;
import sound.PlaySound;
import utils.Constant;

import java.io.IOException;

public class FactoryMapPane extends AnchorPane {
    private static FactoryMapPane instance;
    private ImageView exit;
    private ImageView coin;
    private ImageView skill;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    Punk punk;
    public FactoryMapPane() {
        // Set BGsound
        PlaySound.factoryMapBG.play();

        //Set Background and Ground
        setBackground(new Background(GameLogic.getBGImage("BG_factory.png")));
        ImageView groundImageView = GameLogic.getGroundImage("factory_ground.png");
        setTopAnchor(groundImageView,510.0);
        this.getChildren().add(groundImageView);

        // Set Main Character
        punk = new Punk();
        punk.initPunkAnimation();
        setTopAnchor(punk.getPunkImageView(),453.0);
        punk.setCanShoot(true);
        getChildren().addAll(punk.getPunkImageView(), punk.getPunkShot());

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
        GameLogic.reversePlayerInput(this);
        GameLogic.getReverseContinuousMovement().stop();
        GameLogic.getPlayerInput(this);

        //Set enemies
        GameLogic.getEnemies().clear();
        for (int i = 0; i < 3; i++){
            GameLogic.getEnemies().add(new Minion(10.0, 10.0));
            setTopAnchor(GameLogic.getEnemies().get(i).getImageView(), 50.0);
            GameLogic.getEnemies().get(i).runAnimation(this, GameLogic.getEnemies().get(i));
            getChildren().add(GameLogic.getEnemies().get(i).getImageView());
        }
        for (int i = 3; i < 6; i++){
            GameLogic.getEnemies().add(new AttackGhost(10.0, 10.0));
            setTopAnchor(GameLogic.getEnemies().get(i).getImageView(), 50.0);
            GameLogic.getEnemies().get(i).runAnimation(this, GameLogic.getEnemies().get(i));
            getChildren().add(GameLogic.getEnemies().get(i).getImageView());
            getChildren().add(((AttackGhost) GameLogic.getEnemies().get(i)).getFireball());
        }
        for (int i = 6; i < 8; i++){
            GameLogic.getEnemies().add(new MindGhost(10.0, 10.0));
            setTopAnchor(GameLogic.getEnemies().get(i).getImageView(), 50.0);
            GameLogic.getEnemies().get(i).runAnimation(this, GameLogic.getEnemies().get(i));
            getChildren().add(GameLogic.getEnemies().get(i).getImageView());
        }
        for (int i = 8; i < 10; i++){
            GameLogic.getEnemies().add(new SlowGhost(10.0, 10.0));
            setTopAnchor(GameLogic.getEnemies().get(i).getImageView(), 50.0);
            GameLogic.getEnemies().get(i).runAnimation(this, GameLogic.getEnemies().get(i));
            getChildren().add(GameLogic.getEnemies().get(i).getImageView());
        }

        // Set skills
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
                PlaySound.factoryMapBG.stop();
                PlaySound.exit.play();
                GameLogic.setHighScoreEachMap(Constant.getIndexMap("FactoryMap"),punk.getScore());
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
        GameLogic.checkPunkShotHit(this);
    }

    private void fadeExitPage() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            try {
                System.out.println("Exit !");
                PlaySound.stopAllMapBG();
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
        return "FactoryMap";
    }
    public static FactoryMapPane getInstance() {
        if (instance == null) {
            instance = new FactoryMapPane();
        }
        return instance;
    }
}