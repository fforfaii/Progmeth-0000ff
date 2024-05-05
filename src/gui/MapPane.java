package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Main;

import java.io.IOException;

public class MapPane extends VBox {
    Text welcome;
    Button MapBTN1;
    Button MapBTN2;
    Button MapBTN3;
    Button MapBTN4;
    private static MapPane instance;
    public MapPane() {
        setBGImage();
        setAlignment(Pos.CENTER);
        setSpacing(25);

        // Set welcome text (head)
        welcome = new Text("Welcome " + LoginPane.getPlayername() + " !");
        welcome.setFont(Font.font("Monospace", FontWeight.BOLD,35));
        welcome.setFill(Color.WHITE);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        VBox vBox1 = setMap("BG_Cave.jpg","Map 1");
        VBox vBox2 = setMap("BG_forest1.jpg", "Map 2");
        VBox vBox3 = setMap("BG_factory.png", "Map 3");
        VBox vBox4 = setMap("BG_forest2.jpg", "Map 4");
        hBox.getChildren().addAll(vBox1, vBox2, vBox3, vBox4);

        // Change Scene to 'Each'MapPane
        MapBTN1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Main.getInstance().changeSceneJava(CaveMapPane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        getChildren().addAll(welcome,hBox);
    }

    public  VBox setMap(String mapImage,String mapName) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(150,200);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        Image img = new Image(ClassLoader.getSystemResource(mapImage).toString());
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);
        Button mapText = new Button(mapName);
        mapText.setFont(Font.font("Monospace", FontWeight.BOLD,20));
        mapText.setTextFill(Color.rgb(0,51,102));
        mapText.setPrefWidth(150);
        switch (mapName) {
            case "Map 1":
                MapBTN1 = mapText;
                break;
            case "Map 2":
                MapBTN2 = mapText;
            case "Map 3":
                MapBTN3 = mapText;
                break;
            case "Map 4":
                MapBTN4 = mapText;
            default: break;
        }
        vBox.getChildren().addAll(imageView,mapText);

        return vBox;
    }

    public void setBGImage() {
        String img_path = ClassLoader.getSystemResource("BG_forest.gif").toString();
        Image img = new Image(img_path);
        BackgroundImage bg_img = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
        setBackground(new Background(bg_img));
    }

    public static MapPane getInstance() {
        if (instance == null) {
            instance = new MapPane();
        }
        return instance;
    }
}
