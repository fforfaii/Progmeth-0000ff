package logic.skills;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.character.Punk;

public class MoveFaster {
    public static void effect(){
        Punk.getInstance().setSpeed(Punk.getInstance().getSpeed() + 7.5);
        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> Punk.getInstance().setSpeed(Punk.getInstance().getSpeed() - 7.5)));
        cooldownTimer.play();
    }
}
