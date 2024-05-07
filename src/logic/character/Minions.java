package logic.character;

import gui.SpriteAnimation;
import javafx.animation.Animation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Minions extends Enemy { //can do nothing but player needs to avoid
    private static Minions instance;
    private double xPos;
    private double yPos;
    private ImageView MinionsimageView;
    private Animation MinionsAni;

    public Minions(double x, double y){
        setHp(1);
        setxPos(x);
        setyPos(y);
        MinionsimageView = new ImageView(new Image(ClassLoader.getSystemResource("ghost1.png").toString()));
        MinionsAni = new SpriteAnimation(MinionsimageView,Duration.millis(1000),6,6,0,0,48,48);
        MinionsAni.setCycleCount(Animation.INDEFINITE);
        MinionsimageView.setFitHeight(80);
        MinionsimageView.setFitWidth(80);
        MinionsAni.play();
    }

    public ImageView getMinionsimageView() {
        return MinionsimageView;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public static Minions getInstance() {
        if (instance == null) {
            instance = new Minions(10.0,0.0);
        }
        return instance;
    }

    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void effect(){}
}
