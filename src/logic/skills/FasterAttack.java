package logic.skills;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.character.Punk;

public class FasterAttack {
    public static void effect(){
        Punk.getInstance().setDelayShoot(3);
        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(12), event -> Punk.getInstance().setDelayShoot(5)));
        cooldownTimer.play();
    }
}
