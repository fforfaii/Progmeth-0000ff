package logic.skills;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.character.Punk;

public class ExtraDamage {
    public static void effect(){
        Punk.getInstance().setAtk(Punk.getInstance().getAtk() + 1);
        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> Punk.getInstance().setAtk(1)));
        cooldownTimer.play();
    }
}
