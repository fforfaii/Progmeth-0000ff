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
import sound.Playsound;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;

public class FactoryMapPane extends AnchorPane {
    private static FactoryMapPane instance;
    private ImageView pause;
    private ImageView coin;
    private ImageView skill;
    private HpBoard hpBoard;
    private ScoreBoard scoreBoard;
    private ArrayList<Enemy> enemies;
    Punk punk;
    public FactoryMapPane() {
        // Set BGsound
        Playsound.FactorymapBG.play();

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

        // Set hpBoard & scoreBoard
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
                Playsound.FactorymapBG.stop();
                Playsound.exit.play();
                GameLogic.setHighScoreEachMap(Constant.getIndexMap("FactoryMap"),punk.getScore());
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
//    public void setSkillImage(ImageView skillImageView, String skillname) {
//        // Set icon that fall down
////        ImageView skillimageview = new ImageView();
//        switch (skillname) {
//            case "Shield":
//                skillImageView.setImage(new Image(ClassLoader.getSystemResource("shield.png").toString()));
//                skillImageView.setFitHeight(30);
//                skillImageView.setFitWidth(30);
//                break;
//            case "ExtraScore":
//                skillImageView.setImage(new Image(ClassLoader.getSystemResource("extrascore.png").toString()));
//                skillImageView.setFitHeight(35);
//                skillImageView.setFitWidth(35);
//                break;
//            case "ExtraDamage":
//                skillImageView.setImage(new Image(ClassLoader.getSystemResource("extradamage.png").toString()));
//                skillImageView.setFitHeight(50);
//                skillImageView.setFitWidth(50);
//                break;
//            case "Heal":
//                skillImageView.setImage(new Image(ClassLoader.getSystemResource("heal.png").toString()));
//                skillImageView.setFitHeight(50);
//                skillImageView.setFitWidth(50);
//                break;
//            case "MoveFaster":
//                skillImageView.setImage(new Image(ClassLoader.getSystemResource("movefaster.png").toString()));
//                skillImageView.setFitHeight(50);
//                skillImageView.setFitWidth(50);
//                break;
//            case "Disappear":
//                skillImageView.setImage(new Image(ClassLoader.getSystemResource("disappear.png").toString()));
//                skillImageView.setFitHeight(65);
//                skillImageView.setFitWidth(65);
//                break;
//        }
//    }
//    public void skillFall(ImageView skillImageView) {
//        // Set falldown movement
//        Random random = new Random();
//        ArrayList<Double> durations = new ArrayList<>();
//        durations.add(3.0);
//        durations.add(3.5);
//        durations.add(4.0);
//        durations.add(4.5);
//        durations.add(2.0);
//        durations.add(2.5);
//        randomIndex = randomIndex(); // for getting duration
//        AnimationTimer FallDown = new AnimationTimer() {
//            private long lastUpdate = 0;
//            private String randSkill = GameLogic.randomSkill();
//            @Override
//            public void handle(long currentTime) {
//                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
//                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
//                    skillImageView.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
//                    skillImageView.setTranslateY(0.0);
//                    skillImageView.setFitWidth(40);
//                    skillImageView.setFitHeight(40);
//                    GameLogic.slideYPos(skillImageView, 2.0, 525);
//                    lastUpdate = currentTime;
//                    randomIndex = randomIndex();
//                    randSkill = GameLogic.randomSkill();
//                    setSkillImage(skillImageView, randSkill);
//                }
//                GameLogic.checkSkillHit(this.toString(), skillImageView, randSkill);
//            }
//        };
//        FallDown.start();
//    }
    public double getXPos(ImageView imageView) {
        return imageView.getTranslateX();
    }
    public double getYPos(ImageView imageView) {
        return imageView.getTranslateY();
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
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