package gui;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

public class SpriteAnimation extends Transition {
    private static SpriteAnimation instance;
    private ImageView imageView;
    private int count;
    private int columns;
    private int offsetX;
    private int offsetY;
    private int width;
    private int height;
    private int lastIndex;

    public SpriteAnimation(
            ImageView imageView,
            Duration duration,
            int count, int columns,
            int offsetX, int offsetY,
            int width, int height) {
        this.imageView = imageView;
        this.count = count;
        this.columns = columns;
        // cooordinate ที่เริ่ม
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        // ขนาดของ spritesheet
        this.width = width;
        this.height = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * count), count - 1);
        if (index != lastIndex) {
            final int x = (index % columns) * width  + offsetX;
            final int y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }

    public static SpriteAnimation getInstance() {
        if (instance == null) {
            instance = new SpriteAnimation(null,Duration.millis(1000),4,4,0,0,48,48);
        }
        return instance;
    }
}
