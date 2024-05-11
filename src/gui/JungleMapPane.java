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
import logic.character.Enemy;
import logic.character.Punk;
import main.Main;
import sound.PlaySound;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;

public class JungleMapPane extends AnchorPane {
    private static JungleMapPane instance;
    private ImageView coin;
    private ImageView skill;
    private ImageView pause;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    private ArrayList<Enemy> enemies;
    Punk punk;
    public JungleMapPane() {
        // Set BGsound
        PlaySound.jungleMapBG.play();

        //Set Background and Ground
        setBackground(new Background(GameLogic.getBGImage("BG_jungle.jpg")));
        ImageView groundImageView = GameLogic.getGroundImage("glass_ground_long.png");
        setTopAnchor(groundImageView,515.0);

        // Set Main Character
        punk = new Punk();
        punk.initPunkAnimation();
        setTopAnchor(punk.getPunkImageView(),453.0);
        punk.setCanShoot(true);
        getChildren().addAll(groundImageView, punk.getPunkImageView(), punk.getPunkShot());

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

        // Set skills
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
                PlaySound.jungleMapBG.stop();
                PlaySound.exit.play();
                GameLogic.setHighScoreEachMap(Constant.getIndexMap("JungleMap"),punk.getScore());
                fadeExitPage();
            }
        });
        pause.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                PlaySound.defaultBG.play();
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
        return "JungleMap";
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
