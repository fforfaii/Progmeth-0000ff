package gui;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class AboutGamePane extends ScrollPane {
    private static AboutGamePane instance;
    VBox contents;
    public AboutGamePane() {
        setFitToHeight(true);
        setFitToWidth(true);
        contents = new VBox();

        for (int i = 1; i < 6; i++) {
            ImageView eachContent = new ImageView(new Image(ClassLoader.getSystemResource("howto" + i + ".png").toString()));
            contents.getChildren().add(eachContent);
        }

        setContent(contents);
    }

    public static AboutGamePane getInstance() {
        if (instance == null) {
            instance = new AboutGamePane();
        }
        return instance;
    }
}
