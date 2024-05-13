package logic.skills;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.character.Punk;

public class FasterAttack {
    public static void effect(){
        Punk.getInstance().setDelayShoot(Punk.getInstance().getDelayShoot() - 1.5);
        Punk.getInstance().setShotImageView("gun2.png");
        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(10), event ->{
            Punk.getInstance().setDelayShoot(Punk.getInstance().getDelayShoot() + 1.5);
            Punk.getInstance().setShotImageView("gun1.png");
        }));
        cooldownTimer.play();
    }
}
