package logic.character;


import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Random;

public abstract class Enemy { //template for every enemy
    int hp;
    private double xPos;
    private double yPos;
    public abstract void runAnimation(AnchorPane currentPane);
    public abstract ImageView getImageView();
    public static double randYPos(){
        Random random = new Random();
        return 10.0 + (300.0 - 10.0)*random.nextDouble();
    }
    public void setHp(int hp){
        this.hp = Math.max(0, hp);
    }
    public int getHp(){
        return hp;
    }

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }
}
