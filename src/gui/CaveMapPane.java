package gui;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.character.Minions;
import logic.character.Punk;
import logic.character.SlowGhost;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CaveMapPane extends AnchorPane {
    private static CaveMapPane instance;
    private ImageView mainChar;
    private ImageView Boom;
    private ImageView Coin;
    private ImageView Minion;
    private ArrayList<ImageView> Hp;
    private Animation mainAni;
    private Animation minionsAni;
    private Image Gun;
    private Image runLeft;
    private Image runRight;
    private Image Idle;
    private Image minion;
    private HBox hpBoard;
    private HBox ScoreBoard;
    private boolean canShoot;
    private boolean canHit = true;
    private int tmp = 1; // for play ghost slide
    int randomIndex; // for CoinFall
    ArrayList<Integer> xPos_Down = new ArrayList<Integer>(); // for collect rand x position for ghost to go down
    Punk punk;
    Minions minions;
    public CaveMapPane() {
        setBGImage();

        // Set Ground
        Image groundImage = new Image(ClassLoader.getSystemResource("rock_ground_long.png").toString());
        ImageView groundImageView = new ImageView(groundImage);
        setTopAnchor(groundImageView,530.0);

        getChildren().add(groundImageView);

        // Preload Run Animation
        Gun = new Image(ClassLoader.getSystemResource("Punk_Gun_Resize.png").toString());
        runLeft = new Image(ClassLoader.getSystemResource("Punk_runleft.png").toString());
        runRight = new Image(ClassLoader.getSystemResource("Punk_runright.png").toString());
        Idle = new Image(ClassLoader.getSystemResource("Punk_idle.png").toString());
        minion = new Image(ClassLoader.getSystemResource("ghost1.png").toString());

        // Set Main Character
        punk = Punk.getInstance();
        mainChar = new ImageView(new Image(ClassLoader.getSystemResource("Punk_idle.png").toString()));
        mainAni = new SpriteAnimation(mainChar,Duration.millis(1000),4,4,0,0,48,48);
        mainAni.setCycleCount(Animation.INDEFINITE);
        mainChar.setFitWidth(100);
        mainChar.setFitHeight(100);
        mainAni.play();
        setTopAnchor(mainChar,453.0);

        // Set GunBoom
        canShoot = true;
        Boom = new ImageView(new Image(ClassLoader.getSystemResource("gun1.png").toString()));
        Boom.setFitWidth(60); //12
        Boom.setFitHeight(360); //72
        Boom.setLayoutY(punk.getYPos());
        Boom.setVisible(false);

        // Set Coin
        Coin = new ImageView(new Image(ClassLoader.getSystemResource("coin.png").toString()));
        Coin.setVisible(false);
        getChildren().add(Coin);

        // Set heart
        hpBoard = new HBox();
        hpBoard.setAlignment(Pos.CENTER_LEFT);
        hpBoard.setSpacing(8);
        hpBoard.setPrefWidth(91);
        hpBoard.setPrefHeight(20);
        for (int i = 0; i < 3; i++) {
            ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
            hp.setFitHeight(20);
            hp.setFitWidth(25);
            hpBoard.getChildren().add(hp);
        }
        setTopAnchor(hpBoard,10.0);
        setLeftAnchor(hpBoard,15.0);
        getChildren().add(hpBoard);

        // Set ScoreBoard ( must stay after set punk )
        ScoreBoard = new HBox();
        ScoreBoard.setPadding(new Insets(8));
        ScoreBoard.setPrefWidth(80);
        ScoreBoard.setPrefHeight(25);
        ScoreBoard.setBackground(new Background(new BackgroundFill(Color.rgb(247, 243, 229), new CornerRadii(10.0), null)));
        ScoreBoard.setAlignment(Pos.CENTER);
        Text text = new Text("Your Score :  ");
        Text Score = new Text("" + punk.getScore());
        setTextScoreBoard(text);
        setTextScoreBoard(Score);
        ScoreBoard.getChildren().addAll(text,Score);
        setRightAnchor(ScoreBoard,25.0);
        setTopAnchor(ScoreBoard,5.0);
        getChildren().add(ScoreBoard);

        // Set Ghost1
        minions = Minions.getInstance();
        Minion = new ImageView(minion);
        minionsAni = new SpriteAnimation(Minion,Duration.millis(1000),6,6,0,0,48,48);
        minionsAni.setCycleCount(Animation.INDEFINITE);
        Minion.setFitHeight(80);
        Minion.setFitWidth(80);
        minionsAni.play();
        setTopAnchor(Minion, 50.0);
        RunGhostAnimation();

        getChildren().addAll(mainChar, Boom, Minion);

        // Keyboard Input
        this.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case A:
                        // go left
                        System.out.println("A");
                        if (mainChar.getLayoutX() >= 5.0) mainChar.setLayoutX(mainChar.getLayoutX()-punk.getSpeed());
                        setMainChar(runLeft,6,6,48,48);
                        break;
                    case D:
                        // go right
                        System.out.println("D");
                        if (mainChar.getLayoutX() <= 1080) mainChar.setLayoutX(mainChar.getLayoutX()+punk.getSpeed());
                        setMainChar(runRight,6,6,48,48);
                        break;
                    case SPACE:
                        // release power
                        if (!canShoot){
                            return;
                        }
                        canShoot = false;
                        Shoot(Gun);
                        Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(Punk.getInstance().getDelayShoot()), event -> canShoot = true));
                        cooldownTimer.play();
                        System.out.println("Boom!");
                        break;
                }
                punk.setXPos(mainChar.getLayoutX());
                System.out.println("Punk X : " + punk.getXPos());
                System.out.println("Punk Y : " + punk.getYPos());
            }
        });
        this.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                // idle
                setMainChar(Idle,4,4,48,48);
            }
        });

        // Run AnimationTimer to Check Boom Hit
        CheckBoomHit(Minion);

        // Set CoinFall
        CoinFall();
    }
    public void CheckGhostHit(ImageView ghost) {
//        Bounds GhostBounds = ghost.getBoundsInParent();
        if (! canHit){
            return;
        }
        Bounds GhostBounds = new BoundingBox(
                ghost.getBoundsInParent().getMinX(),
                ghost.getBoundsInParent().getMinY(),
                ghost.getBoundsInParent().getWidth(),
                80
        );
        Bounds mainCharBounds = new BoundingBox(
                mainChar.getBoundsInParent().getMinX() + 20,
                mainChar.getBoundsInParent().getMinY(),
                20,
                100
        );
        if (GhostBounds.intersects(mainCharBounds) && ghost.isVisible()) {
            System.out.println("Ghost hit detected");
            System.out.println("hp : " + punk.getHp());
            System.out.println("GhostBound : " + minions.getxPos() + " , " + minions.getyPos());
            System.out.println("PunkBound : " + punk.getXPos() + " , " + punk.getYPos());
            punk.setHp(punk.getHp() - 1);
            deleteHeart();
            canHit = false;
            Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> canHit = true));
            cooldownTimer.play();
        }
    }
    public void deleteHeart() {
        int size = hpBoard.getChildren().size();
        System.out.println("Size before deletion: " + size);
        hpBoard.getChildren().remove(size-1);
        if (punk.getHp() == 0){
            punk.setDead(true);
            //ย้ายหน้า gameover
            return;
        }
    }
    public void addHeart() {
        if (hpBoard.getChildren().size() <= 3){
            ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
            hp.setFitHeight(20);
            hp.setFitWidth(25);
            hpBoard.getChildren().add(hp);
        }
    }
    public void setTextScoreBoard(Text text) {
        text.setFont(Font.font("Monospace", FontWeight.EXTRA_BOLD,12));
        text.setFill(Color.rgb(48, 34, 3));
    }
    public void SetScoreboard() {
        ScoreBoard.getChildren().remove(1);
        Text Score = new Text("" + punk.getScore());
        setTextScoreBoard(Score);
        ScoreBoard.getChildren().add(Score);
    }
    public void CheckCoinHit(ImageView coin) {

        Bounds CoinBounds = coin.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                mainChar.getBoundsInParent().getMinX() + 20,
                mainChar.getBoundsInParent().getMinY(),
                20,
                mainChar.getBoundsInParent().getHeight() / 2
        );
        if (CoinBounds.intersects(mainCharBounds) && coin.isVisible() && coin.getTranslateY() >= punk.getYPos()){
            punk.setScore(punk.getScore() + 1);
            coin.setTranslateY(0.0);
            coin.setVisible(false);
            SetScoreboard();
        }
    }
    public int randomIndexforCoinFall() {
        Random random = new Random();
        int randomIndex = random.nextInt(6);
        return randomIndex;
    }
    public void CoinFall() {
        Random random = new Random();

        ArrayList<Double> durations = new ArrayList<>();
        durations.add(3.0);
        durations.add(3.5);
        durations.add(4.0);
        durations.add(4.5);
        durations.add(2.0);
        durations.add(2.5);
        randomIndex = randomIndexforCoinFall();
        AnimationTimer FallDown = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long currentTime) {
                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
//                System.out.println(randomIndexforCoinFall());
                System.out.println("playerscore = " + punk.getScore() + " fall : " + Coin.getTranslateY());
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    Coin.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
                    Coin.setTranslateY(0.0);
                    Coin.setFitWidth(30);
                    Coin.setFitHeight(30);
                    SlideCoin(Coin);
                    lastUpdate = currentTime;
                    randomIndex = randomIndexforCoinFall();
                }
                CheckCoinHit(Coin);
            }
        };
        FallDown.start();
    }
    public void SlideCoin(ImageView Coin) {
        Coin.setVisible(true);
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), Coin);
        fallTransition.setFromY(0);
        fallTransition.setToY(545);
        fallTransition.setCycleCount(1);

        // ต้องมี check mainChar can get coin ?

        fallTransition.setOnFinished(event -> {
            Coin.setTranslateY(0.0);
            Coin.setVisible(false);
        });
        fallTransition.play();
    }
    public void CheckBoomHit(ImageView ghost) {
        AnimationTimer checkHit = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                Bounds BoomBounds = Boom.getBoundsInParent();
                Bounds MinionsBounds = ghost.getBoundsInParent();
                if (BoomBounds.intersects(MinionsBounds) && Boom.isVisible()){
                    // Don't forget to set HP of that ghost
                    getChildren().remove(ghost);
                }
            }
        };
        checkHit.start();
    }

    public void RunGhostAnimation() {
        AnimationTimer GhostAnimationTimer = new AnimationTimer() {
            private long startTime = System.nanoTime();
            private long lastUpdate = 0;
            @Override
            public void handle(long currentTime) {
                // Slide X axis
                if (currentTime - lastUpdate >= 10_000_000_000L){
                    SlideXPos(Minion);
                    lastUpdate = currentTime;
                }
                // Get Position & Set to Minions class
                minions.setxPos(getXPos(Minion));
                minions.setyPos(getYPos(Minion));

                //System.out.println("x : " + minions.getxPos() + " y : " + minions.getyPos());

                // get random XPos
                if (xPos_Down.size() < 20){
                    xPos_Down.add(xPos_Down.size(),randXPos());
                }
                // Check xPos to goDown
                int stay = (int) Minion.getTranslateX();
                if (xPos_Down.contains(stay)){
                    // remove used xPos
                    xPos_Down.remove(xPos_Down.indexOf(stay));
                    System.out.println("stay = " + stay + " go down !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    // Stop slide X axis
                    tmp = 0;
                    SlideXPos(Minion);

                    if (currentTime - lastUpdate >= 4_000_000_000L){
                        goDown(Minion);
                        lastUpdate = currentTime;
                    }
                    tmp = 1;
                }
                int ck = (int) minions.getxPos();
//                System.out.println("ตำแหน่งผีตอนนี้ " + ck);
//                System.out.println("ตำแหน่งที่ต้องลง " + xPos_Down);

                if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                    // Check ghost hit
                    CheckGhostHit(Minion);
                }
            }
        };
        GhostAnimationTimer.start();
    }
    public double getXPos(ImageView imageView) {
        return imageView.getTranslateX();
    }
    public double getYPos(ImageView imageView) {
        return imageView.getTranslateY();
    }
    public void SlideXPos(ImageView imageView) {
        // Create TranslateTransition for left-right movement
        TranslateTransition translateXTransition = new TranslateTransition(Duration.seconds(5), imageView);
        translateXTransition.setFromX(10);
        translateXTransition.setToX(1142 - imageView.getFitWidth());
        translateXTransition.setCycleCount(1);
        translateXTransition.setAutoReverse(true);
        translateXTransition.setOnFinished(event -> {
            // Create another TranslateTransition to move back from right-left
            TranslateTransition reverseTransition = new TranslateTransition(Duration.seconds(5), imageView);
            reverseTransition.setFromX(1142 - imageView.getFitWidth());
            reverseTransition.setToX(10);
            reverseTransition.setCycleCount(1);
            reverseTransition.setAutoReverse(false);
            reverseTransition.play();
        });

        if (tmp == 1 ) {
            translateXTransition.play();
        } else if (tmp == 0) {
            translateXTransition.stop();
        }
    }
    public int randXPos() {
        int randXPos = ThreadLocalRandom.current().nextInt(10, 1062);
        return randXPos;
    }
    public void goDown(ImageView imageView) {
        // Move down
        TranslateTransition translateYTransitionDown = new TranslateTransition(Duration.seconds(2), imageView);
        translateYTransitionDown.setFromY(0);
        translateYTransitionDown.setToY(460);
        translateYTransitionDown.setCycleCount(1);
        translateYTransitionDown.setAutoReverse(true);

        // Move up
        TranslateTransition translateYTransitionUp = new TranslateTransition(Duration.seconds(2), imageView);
        translateYTransitionUp.setFromY(460);
        translateYTransitionUp.setToY(0);
        translateYTransitionUp.setCycleCount(1);
        translateYTransitionUp.setAutoReverse(true);
        translateYTransitionUp.setDelay(Duration.seconds(0)); // No Delay before moving up

        SequentialTransition sequentialTransition = new SequentialTransition(translateYTransitionDown, translateYTransitionUp);
        sequentialTransition.play();
    }

    public void Shoot(Image image) {
        // Set mainChar when Shoot
        mainChar.setImage(image);
        mainChar.setViewport(new javafx.geometry.Rectangle2D(96, 0, 48, 48));

        // Set Boom when start Shooting
        Boom.setVisible(true);
        Boom.setFitWidth(24);
        Boom.setFitHeight(120);
        Boom.setLayoutX(punk.getXPos() + 22);
        punk.setPunkShotXPos(Boom.getLayoutX());
        punk.setPunkShotYPos(Boom.getLayoutY());
//        System.out.println("Boom start: " + "(" + punk.getBoomxPos() + "," + punk.getBoomyPos() + ")");

        // Animate Boom moving upwards
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), Boom);
        transition.setByY(-453); // Move the Boom from its initial position (453) to 0
        transition.setOnFinished(event -> {
            // Hide Boom and reset its position after the animation finishes
            Boom.setVisible(false);
            Boom.setLayoutY(mainChar.getLayoutY());

            punk.setPunkShotXPos(Boom.getLayoutX());
            punk.setPunkShotYPos(Boom.getLayoutY());
//            System.out.println("Boom reset: "+ "(" + punk.getBoomxPos() + "," + punk.getBoomyPos() + ")");
            Boom.setTranslateY(0);
        });
        transition.play();
        punk.setPunkShotXPos(Boom.getLayoutX());
        punk.setPunkShotYPos(Boom.getTranslateY());
//        System.out.println("Boom finish: " + "(" + punk.getBoomxPos() + "," + punk.getBoomyPos() + ")");
    }

    public void setMainChar(Image Image, int count, int column, int width, int height) {
        mainChar.setImage(Image);
        SpriteAnimation.getInstance().setCount(count);
        SpriteAnimation.getInstance().setColumns(column);
        SpriteAnimation.getInstance().setWidth(width);
        SpriteAnimation.getInstance().setHeight(height);
        mainAni.setCycleCount(Animation.INDEFINITE);
        mainChar.setFitWidth(100);
        mainChar.setFitHeight(100);
        mainAni.play();
        setTopAnchor(mainChar,453.0);
    }

    public void setBGImage() {
        String img_path = ClassLoader.getSystemResource("BG_Cave.jpg").toString();
        Image img = new Image(img_path);
        BackgroundImage bg_img = new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
        setBackground(new Background(bg_img));
    }

    public static CaveMapPane getInstance() {
        if (instance == null) {
            instance = new CaveMapPane();
        }
        return instance;
    }
}
