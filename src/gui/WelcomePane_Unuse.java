package gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class WelcomePane_Unuse extends StackPane {
    private static WelcomePane_Unuse instance;
    private ImageView imageView1;
    private ImageView imageView2;
    private boolean useImage1 = true;
    private WelcomePane_Unuse() {
        setImage();
        //setSpacing(10);
        //setAlignment(Pos.CENTER);
        setMinSize(864,486);

        VBox vBox = new VBox();
        // Set Welcome Text
        Text welcomeTxt = new Text("Welcome " + LoginPane.getPlayername() + " !");
        welcomeTxt.setFont(Font.font("Monospace", FontWeight.BOLD,80));
        welcomeTxt.setFill(Color.rgb(179,89,0));

        // Set High Score Text
        Text highscoreTxt = new Text("your high score : " + 0); // ดึง highscore จาก logic
        highscoreTxt.setFont(Font.font("Monospace", FontWeight.BOLD,20));
        highscoreTxt.setFill(Color.WHITE);

        // Set Start Button
        Button startBtn = new Button("START");
        startBtn.setFont(Font.font("Monospace" ,FontWeight.BOLD,25));
        startBtn.setTextFill(Color.WHITE);
        startBtn.setStyle("-fx-background-color: #004466; -fx-border-color: #ffffff; -fx-border-width: 3px;");
//        startBtn.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                try {
//                    Main.getInstance().changeSceneJava(LoginPane.getInstance());
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });

        vBox.getChildren().addAll(welcomeTxt,highscoreTxt,startBtn);
    }

    public void setImage() {
        String img_path = ClassLoader.getSystemResource("BG_NY.jpg").toString();
        Image img1 = new Image(img_path);
        Image img2 = new Image(img_path);
        imageView1 = new ImageView(img1);
        imageView2 = new ImageView(img2);
        imageView1.setFitWidth(1152);
        imageView2.setFitWidth(1152);
        imageView1.setFitHeight(648);
        imageView2.setFitHeight(648);
        imageView1.setPreserveRatio(false);
        imageView2.setPreserveRatio(false);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(imageView1.translateXProperty(), 0),
                        new KeyValue(imageView2.translateXProperty(), 0)),
                new KeyFrame(Duration.seconds(5), event -> swapImages()),
                new KeyFrame(Duration.seconds(10), new KeyValue(imageView1.translateXProperty(), -imageView1.getImage().getWidth()),
                        new KeyValue(imageView2.translateXProperty(), -imageView1.getImage().getWidth()))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        this.getChildren().add(imageView1);
//        BackgroundImage bg_img = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
//        setBackground(new Background(bg_img));
    }

    private void swapImages() {
        if (useImage1) {
            imageView1.setImage(new Image("BG_factory.png"));
        } else {
            imageView1.setImage(new Image("BG_factory.png"));
        }
        useImage1 = !useImage1;
    }

    public static WelcomePane_Unuse getInstance() {
        if (instance == null) {
            instance = new WelcomePane_Unuse();
        }
        return instance;
    }
}
