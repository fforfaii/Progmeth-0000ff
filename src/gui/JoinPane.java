package gui;

import javafx.animation.Animation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.util.Duration;

public class JoinPane extends HBox {
    private static JoinPane instance;
    public JoinPane() {
//        StackPane stackPane = new StackPane();
//        stackPane.setMinSize(288,162);

//        Image image = new Image(ClassLoader.getSystemResource("Punk_idle.png").toString());
//        ImageView imageView = new ImageView(image);
//        imageView.setViewport(new Rectangle2D(0,0,48,48));
//        imageView.setFitHeight(300);
//        imageView.setFitWidth(300);
//        Animation animation = new SpriteAnimation(
//                imageView,
//                Duration.millis(1000),
//                4,4,
//                0,0,
//                48,48);
//        animation.setCycleCount(Animation.INDEFINITE);
//        animation.play();

//        stackPane.getChildren().add(imageView);

        this.getChildren().add(WelcomePane.getInstance());
    }

    public static JoinPane getInstance() {
        if (instance == null) {
            instance = new JoinPane();
        }
        return instance;
    }
}
