package logic.skills;

import gui.CaveMapPane;
import gui.FactoryMapPane;
import gui.ForestMapPane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import logic.GameLogic;
import logic.character.Punk;

public class ExtraScore {
    public static void effect(String map){
        // set addScore
        Punk.getInstance().setScorePerCoin(2);
        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(5), event -> Punk.getInstance().setScorePerCoin(1)));
        cooldownTimer.play();
    }
}
