package logic;

import gui.GameOverPane;
import gui.HpBoard;
import gui.MapPane;
import gui.ScoreBoard;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import logic.character.Punk;
import logic.skills.*;
import main.Main;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameLogic {
    private static ArrayList<Integer> HighScore = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
    private static String CurrentMap;

//    public GameLogic(ArrayList<Integer> highScore) {
//        // 0=CaveMap 1=ForestMap 2=Factory 3=JungleMap
//        HighScore = new ArrayList<Integer>(4);
//        for (int i = 0; i < 4; i++) {
//            HighScore.add(i,0);
//        }
//    }

    public static String getCurrentMap() {
        return CurrentMap;
    }

    public static void setCurrentMap(String currentMap) {
        CurrentMap = currentMap;
    }

    public static void setHighscoreEachMap(int indexMap, int newscore) {
        int oldscore = HighScore.get(indexMap);
        if (newscore > oldscore) HighScore.set(indexMap, newscore);
    }
    public static int getHighscoreEachMap(String mapname){
        return HighScore.get(Constant.getIndexMap(mapname));
    }
    public static String randomSkill() {
        ArrayList<String> Skills = Constant.getInstance().getSkillsname();
        Random random = new Random();
        int randomIndex = random.nextInt(Skills.size());
        return Skills.get(randomIndex);
    }
    public static void checkCoinHit(ImageView coinImage, int addScore) {
        Bounds coinBounds = coinImage.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 30,
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() - 5
        );
        if (coinBounds.intersects(mainCharBounds) && coinImage.isVisible() && coinImage.getTranslateY() >= Punk.getInstance().getYPos() - 30){
            Punk.getInstance().setScore(Punk.getInstance().getScore() + addScore);
            coinImage.setTranslateY(0.0);
            coinImage.setVisible(false);
            ScoreBoard.getInstance().setScoreboard();
        }
    }
    public static void checkSkillHit(String map, ImageView skillImage,String skillname) {
        Bounds coinBounds = skillImage.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 30,
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight()-5
        );
        // If player can get skill
        if (coinBounds.intersects(mainCharBounds) && skillImage.isVisible() && skillImage.getTranslateY() >= Punk.getInstance().getYPos() - 30){
            skillImage.setTranslateY(0.0);
            skillImage.setVisible(false);
            // Call effect skillname
            switch (skillname) {
                case "Shield":
                    Shield.effect();
                    break;
                case "ExtraScore":
                    ExtraScore.effect(map);
                    break;
                case "ExtraDamage":
                    ExtraDamage.effect();
                    break;
                case "Heal":
                    Heal.effect();
                    break;
                case "MoveFaster":
                    MoveFaster.effect();
                    break;
                case "Disappear":
                    Disappear.effect();
                    break;
            }
        }
    }
    public static void checkPunkShotHit(AnchorPane currentPane, ImageView ghost) {
        AnimationTimer checkHit = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                Bounds BoomBounds = Punk.getInstance().getPunkShot().getBoundsInParent();
                Bounds GhostBounds = ghost.getBoundsInParent();
                if (BoomBounds.intersects(GhostBounds) && Punk.getInstance().getPunkShot().isVisible()){
                    // Don't forget to set HP of that ghost
                    currentPane.getChildren().remove(ghost);
                }
            }
        };
        checkHit.start();
    }

//    public static void CheckGhostHit(AnchorPane currentPane, ImageView ghost) {
//        if (! canHitGhost){
//            return;
//        }
//        Bounds GhostBounds = new BoundingBox(
//                ghost.getBoundsInParent().getMinX(),
//                ghost.getBoundsInParent().getMinY(),
//                ghost.getBoundsInParent().getWidth(),
//                80
//        );
//        Bounds mainCharBounds = new BoundingBox(
//                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
//                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY(),
//                20,
//                100
//        );
//        if (GhostBounds.intersects(mainCharBounds) && ghost.isVisible()) {
//            System.out.println("Ghost hit detected");
//            Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
//            deleteHeart();
//            canHitGhost = false;
//            Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> canHitGhost = true));
//            delayTimer.play();
//        }
//    }
//    public static void CheckFireballHit(AnchorPane currentPane, ImageView fireball) {
//        if (! currentPane.canHitFireball){
//            return;
//        }
//        Bounds FireballBounds = fireball.getBoundsInParent();
//        Bounds mainCharBounds = new BoundingBox(
//                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
//                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 22,
//                20,
//                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() / 2
//        );
//        if (FireballBounds.intersects(mainCharBounds) && fireball.isVisible()){
//            System.out.println("FireBall hit detected");
//            Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
//            fireball.setTranslateY(0.0);
//            fireball.setVisible(false);
//            deleteHeart();
//            canHitFireball = false;
//            Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> canHitFireball = true));
//            delayTimer.play();
//        }
//    }
    public void deleteHeart(Node node) {
        int size = HpBoard.getInstance().getChildren().size();
        System.out.println("Size before deletion: " + size);
        if (size!=0) HpBoard.getInstance().getChildren().remove(size-1);
        System.out.println(Punk.getInstance().getHp());
        if (Punk.getInstance().isDead()){
            return;
        }
        if (Punk.getInstance().getHp() == 0) {
            Punk.getInstance().setDead(true);

            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), node);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                try {
                    System.out.println("Game Over !");
//                    GameLogic.setHighscoreEachMap(Constant.getIndexMap(getCurrentMap()),Punk.getInstance().getScore());
                    Main.getInstance().changeSceneJava(GameOverPane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            fadeOut.play();
        }
    }
    public void addHeart() {
        if (HpBoard.getInstance().getChildren().size() <= 3){
            ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
            hp.setFitHeight(20);
            hp.setFitWidth(25);
            HpBoard.getInstance().getChildren().add(hp);
        }
    }
    public static BackgroundImage getBGImage(String BGPath){
        String img_path = ClassLoader.getSystemResource(BGPath).toString();
        Image img = new Image(img_path);
        return new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
    }
    public static ImageView getGroundImage(String ImgPath){
        Image groundImage = new Image(ClassLoader.getSystemResource(ImgPath).toString());
        return new ImageView(groundImage);
    }
}
