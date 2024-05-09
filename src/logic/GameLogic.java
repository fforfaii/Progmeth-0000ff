package logic;

import gui.GameOverPane;
import gui.HpBoard;
import gui.MapPane;
import gui.ScoreBoard;
import javafx.animation.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import logic.character.AttackGhost;
import logic.character.Enemy;
import logic.character.Punk;
import logic.skills.*;
import main.Main;
import utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameLogic {
    public static String randomSkill() {
        ArrayList<String> Skills = Constant.getInstance().getSkillsname();
        Random random = new Random();
        int randomIndex = random.nextInt(Skills.size());
        return Skills.get(randomIndex);
    }
    public static void checkCoinHit(ImageView coinImage) {
        Bounds coinBounds = coinImage.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 30,
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() - 5
        );
        if (coinBounds.intersects(mainCharBounds) && coinImage.isVisible() && coinImage.getTranslateY() >= Punk.getInstance().getYPos() - 30){
            Punk.getInstance().setScore(Punk.getInstance().getScore() + Punk.getInstance().getScorePerCoin());
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
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() - 5
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
    public static void checkPunkShotHit(AnchorPane currentPane, ArrayList<Enemy> enemies) {
        AnimationTimer checkHit = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                for (Enemy eachEnemy: enemies){
                    Bounds BoomBounds = Punk.getInstance().getPunkShot().getBoundsInParent();
                    Bounds GhostBounds = eachEnemy.getImageView().getBoundsInParent();
                    if (BoomBounds.intersects(GhostBounds) && Punk.getInstance().getPunkShot().isVisible()){
                        // Don't forget to set HP of that ghost
                        currentPane.getChildren().remove(eachEnemy.getImageView());
                        if (eachEnemy instanceof AttackGhost){
                            currentPane.getChildren().remove(((AttackGhost) eachEnemy).getFireBall());
                        }
                    }
                }
            }
        };
        checkHit.start();
    }
    //for AttackGhost
    public static void checkFireballHit(AnchorPane currentPane, ImageView fireball) {
        if (Punk.getInstance().isImmortalDelay()) {
            return;
        }
        Bounds FireballBounds = fireball.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 22,
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() / 2
        );
        if (FireballBounds.intersects(mainCharBounds) && fireball.isVisible()) {
            System.out.println("FireBall hit detected");
            Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
            fireball.setTranslateY(0.0);
            fireball.setVisible(false);
            deleteHeart(currentPane);
            Punk.getInstance().setImmortalDelay(true);
            Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> Punk.getInstance().setImmortalDelay(false)));
            delayTimer.play();
        }
    }

    //for every type of enemy
    public void checkGhostHit(AnchorPane currentPane, ImageView ghost) {
        if (Punk.getInstance().isImmortalDelay()){
            return;
        }
        Bounds GhostBounds = new BoundingBox(
                ghost.getBoundsInParent().getMinX(),
                ghost.getBoundsInParent().getMinY(),
                ghost.getBoundsInParent().getWidth(),
                80
        );
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY(),
                20,
                100
        );
        if (GhostBounds.intersects(mainCharBounds) && ghost.isVisible()) {
            System.out.println("Ghost hit detected");
            Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
            deleteHeart(currentPane);
            Punk.getInstance().setImmortalDelay(true);
            Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> Punk.getInstance().setImmortalDelay(true)));
            delayTimer.play();
        }
    }
    public static void deleteHeart(AnchorPane currentPane) {
        int size = HpBoard.getInstance().getChildren().size();
        System.out.println("Size before deletion: " + size);
        if (size != 0) HpBoard.getInstance().getChildren().remove(size - 1);
        System.out.println(Punk.getInstance().getHp());
        if (Punk.getInstance().isDead()){
            return;
        }
        if (Punk.getInstance().getHp() == 0) {
            Punk.getInstance().setDead(true);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), currentPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                try {
                    System.out.println("Game Over !");
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
    public static void slideYPos(ImageView imageView, int duration) {
        imageView.setVisible(true);
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(duration), imageView);
        fallTransition.setFromY(0);
        fallTransition.setToY(545);
        fallTransition.setCycleCount(1);

        fallTransition.setOnFinished(event -> {
            imageView.setTranslateY(0.0);
            imageView.setVisible(false);
        });
        fallTransition.play();
    }

    public static double slideXPos(double lastXPos, ImageView imageView, int duration) {
        // Create TranslateTransition for left-right movement
        TranslateTransition translateXTransition = new TranslateTransition(Duration.seconds(duration), imageView);
        translateXTransition.setFromX(lastXPos);
        translateXTransition.setToX(1142 - imageView.getFitWidth()); //final path
        translateXTransition.setCycleCount(1);
        translateXTransition.setAutoReverse(true);
        final double newLastXPos = randXPos();
        translateXTransition.setOnFinished(event -> {
            // Create another TranslateTransition to move back from right-left
            TranslateTransition reverseTransition = new TranslateTransition(Duration.seconds(duration), imageView);
            reverseTransition.setFromX(1142 - imageView.getFitWidth());
            reverseTransition.setToX(newLastXPos);
            reverseTransition.setCycleCount(1);
            reverseTransition.setAutoReverse(false);
            reverseTransition.play();
//            translateXTransition.play();
        });
        translateXTransition.play();
        return newLastXPos;
    }
    public static double randXPos() {
        double randXPos = ThreadLocalRandom.current().nextDouble(10, 1062);
        return randXPos;
    }
    public static int randomIndex() {
        Random random = new Random();
        int randomIndex = random.nextInt(6);
        return randomIndex;
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
