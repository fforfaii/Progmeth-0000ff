package gui;

import javafx.scene.layout.HBox;

public class JoinPane_Unuse extends HBox {
    private static JoinPane_Unuse instance;
    public JoinPane_Unuse() {
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

        this.getChildren().add(WelcomePane_Unuse.getInstance());
    }

    public static JoinPane_Unuse getInstance() {
        if (instance == null) {
            instance = new JoinPane_Unuse();
        }
        return instance;
    }
}
