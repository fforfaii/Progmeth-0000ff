package logic.skills;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import logic.character.Punk;

public class Disappear {
    private static Timeline timeline = new Timeline();
    public static void effect(){
        Punk.getInstance().getPunkImageView().setOpacity(0.5);
        Punk.getInstance().setCanHit(false);
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> {
                    Punk.getInstance().getPunkImageView().setOpacity(1.0);
                    Punk.getInstance().setCanHit(true);
                })
        );
        timeline.play();
    }
    public static Timeline getTimeline(){
        return timeline;
    }
}
