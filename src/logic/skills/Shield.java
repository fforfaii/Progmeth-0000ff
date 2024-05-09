package logic.skills;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import logic.character.Punk;
import utils.Constant;

import java.util.concurrent.TimeUnit;

public class Shield {
    private static boolean isHit;

    public Shield() {
        setIsHit(false);
    }

    public static void effect(){
        Punk.getInstance().setShield(true);

        AnimationTimer shielTimer = new AnimationTimer() {
            private long startTime = 0;
            private boolean isVisible = true; // Track image visibility

            @Override
            public void handle(long now) {
                if (IsHit()){
                    Punk.getInstance().getPunkImageView().setOpacity(1.0);
                    Punk.getInstance().setShield(false);
                    stop();
                }else {
                    Punk.getInstance().getPunkImageView().setOpacity(0.5);
                    // Blink every 0.5 seconds
                    if (now - startTime > 200_000_000) {
                        isVisible = !isVisible;
                        Punk.getInstance().getPunkImageView().setVisible(isVisible);
                        startTime = now;
                    }
                }
            }
        };
        shielTimer.start();
    }

    public static boolean IsHit() {
        return isHit;
    }

    public static void setIsHit(boolean isHit) {
        Shield.isHit = isHit;
    }
}
