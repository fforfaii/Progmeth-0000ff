package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import main.Main;

import java.io.IOException;

public class AboutGamePane extends AnchorPane {
    private static AboutGamePane instance;
    private final VBox CONTENT_BOX;
    private final Button GO_TO_MAP;
    public AboutGamePane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(648);
        scrollPane.setFitToWidth(true);

        CONTENT_BOX = new VBox();
        for (int i = 1; i < 6; i++) {
            ImageView eachContent = new ImageView(new Image(ClassLoader.getSystemResource("howto" + i + ".png").toString()));
            CONTENT_BOX.getChildren().add(eachContent);
        }
        scrollPane.setContent(CONTENT_BOX);

        GO_TO_MAP = new Button("Back");
        GO_TO_MAP.setFont(Font.font("Monospace", FontWeight.BOLD, 15));
        GO_TO_MAP.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.getInstance().changeSceneJava(MapPane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        HBox buttonBox = new HBox();
        buttonBox.getChildren().add(GO_TO_MAP);
        buttonBox.setStyle("-fx-padding: 10; -fx-alignment: top-left;");

        AnchorPane.setTopAnchor(buttonBox, 0.0);
        AnchorPane.setLeftAnchor(buttonBox, 0.0);
        getChildren().addAll(scrollPane, buttonBox);
    }

    public static AboutGamePane getInstance() {
        if (instance == null) {
            instance = new AboutGamePane();
        }
        return instance;
    }
}
