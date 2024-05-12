package logic.ability;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public interface GoDownable {
    void goDown(ImageView imageView);
    Animation getAnimation();
}
