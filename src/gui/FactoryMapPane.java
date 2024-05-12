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
import java.util.ArrayList;
import java.util.Random;

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

            if (GameLogic.getEnemies().get(i) instanceof AttackGhost){
                getChildren().add(((AttackGhost) GameLogic.getEnemies().get(i)).getFireball());
            }
            if (GameLogic.getEnemies().get(i) instanceof PoisonGhost){
                getChildren().add(((PoisonGhost) GameLogic.getEnemies().get(i)).getPoison());
            }
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

    }

    private void fadeExitPage() {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), this);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> {
            try {
                System.out.println("Exit !");
                PlaySound.stopAllmapBG();
                PlaySound.death.play();
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