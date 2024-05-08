package logic;

import gui.ScoreBoard;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import logic.character.Punk;

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
