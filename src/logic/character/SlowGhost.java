package logic.character;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import logic.ability.Imperishable;


public class SlowGhost extends Enemy implements Imperishable { //if hit: slow
    public SlowGhost(){
        setHp(1);
    }
    //need to check if hit or not in the GameLogic.update()
    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void runAnimation(AnchorPane currentPane){

    }

    @Override
    public ImageView getImageView() {
        return null;
    }

    public void effect(){
        Punk.getInstance().setSpeed(8);
        Timeline cooldownEffect = new Timeline(new KeyFrame(Duration.seconds(4), event -> Punk.getInstance().setSpeed(15)));
        cooldownEffect.play();
    }

    @Override
    public void noDecreaseHP() {
        setHp(getHp() + 1);
    }
}
