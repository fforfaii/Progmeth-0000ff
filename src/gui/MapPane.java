package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.GameLogic;
import main.Main;
import sound.PlaySound;

import java.io.IOException;

public class MapPane extends VBox {
    private Text welcome;
    private Button MapBTN1;
    private Button MapBTN2;
    private Button MapBTN3;
    private Button MapBTN4;
    private static MapPane instance;
    public MapPane() {
        instance = this;
        setBGImage();
        setAlignment(Pos.CENTER);
        setSpacing(25);

        // Set welcome text (head)
        welcome = new Text("Welcome " + LoginPane.getPlayerName() + " !");
        welcome.setFont(Font.font("Monospace", FontWeight.BOLD,35));
        welcome.setFill(Color.WHITE);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        VBox vBox1 = setMap("BG_Cave.jpg","CaveMap");
        VBox vBox2 = setMap("BG_forest.jpg", "ForestMap");
        VBox vBox3 = setMap("BG_factory.png", "FactoryMap");
        VBox vBox4 = setMap("BG_jungle.jpg", "JungleMap");
        hBox.getChildren().addAll(vBox1, vBox2, vBox3, vBox4);

        // Change Scene to 'Each' MapPane
        MapBTN1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    PlaySound.defaultBG.stop();
                    GameLogic.setCurrentMap("CaveMap");
                    Main.getInstance().changeSceneJava(new CaveMapPane());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        MapBTN2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    PlaySound.defaultBG.stop();
                    GameLogic.setCurrentMap("ForestMap");
                    Main.getInstance().changeSceneJava(new ForestMapPane());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        MapBTN3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    PlaySound.defaultBG.stop();
                    GameLogic.setCurrentMap("FactoryMap");
                    Main.getInstance().changeSceneJava(new FactoryMapPane());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        MapBTN4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    PlaySound.defaultBG.stop();
                    GameLogic.setCurrentMap("JungleMap");
                    Main.getInstance().changeSceneJava(new JungleMapPane());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        // Set AboutGame button
        Button aboutGame = new Button("About Game");
        aboutGame.setFont(Font.font("Monospace", FontWeight.BOLD,15));
        aboutGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    Main.getInstance().changeSceneJava(AboutGamePane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        getChildren().addAll(welcome,hBox,aboutGame);
    }

    public VBox setMap(String mapImage,String mapName) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(150,200);
        vBox.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        Image img = new Image(ClassLoader.getSystemResource(mapImage).toString());
        ImageView imageView = new ImageView(img);
        imageView.setFitHeight(200);
        imageView.setFitWidth(150);
        Button mapText = new Button();
        mapText.setFont(Font.font("Monospace", FontWeight.BOLD,20));
        mapText.setTextFill(Color.rgb(0,51,102));
        mapText.setPrefWidth(150);
        switch (mapName) {
            case "CaveMap":
                mapText.setText("Cave");
                MapBTN1 = mapText;
                break;
            case "ForestMap":
                mapText.setText("Forest");
                MapBTN2 = mapText;
                break;
            case "FactoryMap":
                mapText.setText("Factory");
                MapBTN3 = mapText;
                break;
            case "JungleMap":
                mapText.setText("Jungle");
                MapBTN4 = mapText;
            default: break;
        }
        Text space1 = new Text(" ");
        space1.setFont(Font.font("Monospace", FontWeight.BOLD,8));
        Text highScore = new Text("" + GameLogic.getHighScoreEachMap(mapName));
        highScore.setFont(Font.font("Monospace", FontWeight.BOLD,16));
        Text space2 = new Text(" ");
        space2.setFont(Font.font("Monospace", FontWeight.BOLD,8));
        vBox.getChildren().addAll(imageView,mapText,space1,highScore,space2);

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
