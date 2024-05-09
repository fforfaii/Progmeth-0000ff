package logic.skills;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.character.Punk;

public class Disappear {
    public static void effect(){
        Punk.getInstance().getPunkImageView().setOpacity(0.5);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> {
                    Punk.getInstance().getPunkImageView().setOpacity(1.0);
                })
        );
        timeline.play();
    }
}
