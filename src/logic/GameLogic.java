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
import main.Main;

import java.io.IOException;

public class GameLogic {
    public static void checkCoinHit(ImageView coinImage) {
        Bounds coinBounds = coinImage.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY(),
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() / 2
        );
        if (coinBounds.intersects(mainCharBounds) && coinImage.isVisible() && coinImage.getTranslateY() >= Punk.getInstance().getYPos() - 30){
            Punk.getInstance().setScore(Punk.getInstance().getScore() + 1);
            coinImage.setTranslateY(0.0);
            coinImage.setVisible(false);
            ScoreBoard.getInstance().setScoreboard();
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
