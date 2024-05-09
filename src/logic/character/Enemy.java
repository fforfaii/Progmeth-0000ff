package logic.character;


import javafx.scene.image.ImageView;

import java.util.Random;

public abstract class Enemy { //template for every enemy
    int hp;
    public abstract void hitDamage();
    public abstract void effect();
    public abstract void runAnimation();
    public abstract ImageView getImageView();
    public static double randYPos(){
        Random random = new Random();
        return 10.0 + (250.0 - 10.0)*random.nextDouble();
    }
    public void setHp(int hp){
        this.hp = Math.max(0, hp);
    }
    public int getHp(){
        return hp;
    }
}
