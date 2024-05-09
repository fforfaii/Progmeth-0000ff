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
        switch (map) {
            case "CaveMap":
                CaveMapPane.getInstance().setAddScore(2);
                Timeline cooldownTimer1 = new Timeline(
                    new KeyFrame(Duration.seconds(5), event ->
                            CaveMapPane.getInstance().setAddScore(1)
                    )
                );
                cooldownTimer1.play();
                break;
            case "FactoryMap":
                FactoryMapPane.getInstance().setAddScore(2);
                Timeline cooldownTimer2 = new Timeline(
                        new KeyFrame(Duration.seconds(5), event ->
                                FactoryMapPane.getInstance().setAddScore(1)
                        )
                );
                cooldownTimer2.play();
                break;
            case "ForestMap":
                ForestMapPane.getInstance().setAddScore(2);
                Timeline cooldownTimer3 = new Timeline(
                        new KeyFrame(Duration.seconds(5), event ->
                                ForestMapPane.getInstance().setAddScore(1)
                        )
                );
                cooldownTimer3.play();
                break;
            case "JungleMap":
                JungleMapPane.getInstance().setAddScore(2);
                Timeline cooldownTimer4 = new Timeline(
                        new KeyFrame(Duration.seconds(5), event ->
                                JungleMapPane.getInstance().setAddScore(1)
                        )
                );
                cooldownTimer4.play();
                break;

        }
    }
}
