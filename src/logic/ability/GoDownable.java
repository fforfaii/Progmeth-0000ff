package logic.ability;

import javafx.animation.Animation;
import javafx.scene.image.ImageView;

public interface GoDownable {
    void goDown(ImageView imageView);
    Animation getAnimation();
}
