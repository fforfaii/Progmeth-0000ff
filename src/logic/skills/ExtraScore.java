package logic.skills;

import gui.CaveMapPane;
import gui.FactoryMapPane;
import gui.ForestMapPane;
import gui.JungleMapPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class ExtraScore {
    public static void effect(String map){
        // set addScore
        Punk.getInstance().setScorePerCoin(2);
        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> Punk.getInstance().setScorePerCoin(1)));
        cooldownTimer.play();
    }
}
