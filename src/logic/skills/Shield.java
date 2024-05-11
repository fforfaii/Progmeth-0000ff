package logic.skills;

import javafx.animation.AnimationTimer;
import logic.character.Punk;

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
                    Punk.getInstance().setShield(false);
                    stop();
                }else {
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
