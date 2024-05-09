package gui;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.GameLogic;
import logic.character.AttackGhost;
import logic.character.Minion;
import logic.character.Punk;
import main.Main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class CaveMapPane extends AnchorPane {
    private static CaveMapPane instance;
    private ImageView mainChar;
    private ImageView Boom;
    private ImageView Coin;
    private ImageView FireBall;
    private ImageView minionImageView;
    private ImageView attackGhostImageView;
    private Animation mainAni;
    private Animation minionsAni;
    private Animation attackghostAni;
    private Image Gun;
    private Image runLeft;
    private Image runRight;
    private Image Idle;
    private Image minionImage;
    private Image attackGhostImage;
    private HBox hpBoard;
    private HBox ScoreBoard;
    private boolean canShoot;
    private boolean canHitGhost = true;
    private boolean canHitFireball = true;
    private int addScore = 1;
    int randomIndex;
    ArrayList<Integer> xPos_Down = new ArrayList<Integer>(); // for collect rand x position for ghost to go down
    Punk punk;
    Minion minion;
    AttackGhost attackGhost;
    public CaveMapPane() {

        // Set Ground
        setBackground(new Background(GameLogic.getBGImage("BG_Cave.jpg")));
        ImageView groundImageView = GameLogic.getGroundImage("rock_ground_long.png");
        setTopAnchor(groundImageView,530.0);
        this.getChildren().add(groundImageView);

        // Preload Run Animation
        Gun = new Image(ClassLoader.getSystemResource("Punk_Gun_Resize.png").toString());
        runLeft = new Image(ClassLoader.getSystemResource("Punk_runleft.png").toString());
        runRight = new Image(ClassLoader.getSystemResource("Punk_runright.png").toString());
        Idle = new Image(ClassLoader.getSystemResource("Punk_idle.png").toString());
        minionImage = new Image(ClassLoader.getSystemResource("ghost1.png").toString());
        attackGhostImage = new Image(ClassLoader.getSystemResource("ghost2.png").toString());

        // Set Main Character
        punk = new Punk();
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

        // Set Ghost1 --> Minion
        minion = logic.character.Minion.getInstance();
        minionImageView = new ImageView(minionImage);
        minionsAni = new SpriteAnimation(minionImageView,Duration.millis(1000),6,6,0,0,48,48);
        minionsAni.setCycleCount(Animation.INDEFINITE);
        minionImageView.setFitHeight(80);
        minionImageView.setFitWidth(80);
        minionsAni.play();
        setTopAnchor(minionImageView, 50.0);
        RunMinionAnimation();

        // Set Ghost2 --> AttackGhost
        attackGhost = logic.character.AttackGhost.getInstance();
        attackGhostImageView = new ImageView(attackGhostImage);
        attackghostAni = new SpriteAnimation(attackGhostImageView,Duration.millis(1000),6,6,0,0,48,48);
        attackghostAni.setCycleCount(Animation.INDEFINITE);
        attackGhostImageView.setFitWidth(80);
        attackGhostImageView.setFitHeight(80);
        attackghostAni.play();
        setTopAnchor(attackGhostImageView,50.0);
        RunAttackGhostAnimation();

        // Set FireBall
        FireBall = new ImageView(new Image(ClassLoader.getSystemResource("fireball.gif").toString()));
        FireBall.setRotate(90); // หมุนให้เป็นรูปแนวตั้ง
        FireBall.setLayoutY(50.0);
        FireBall.setVisible(false);

        getChildren().addAll(mainChar, Boom, Coin, hpBoard, ScoreBoard, minionImageView, FireBall, attackGhostImageView);

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
        CheckBoomHit(minionImageView);
        CheckBoomHit(attackGhostImageView);

        // Set CoinFall
        CoinFall();
    }

    @Override
    public String toString() {
        return "CaveMap";
    }

    public int getAddScore() {
        return addScore;
    }

    public void setAddScore(int addScore) {
        this.addScore = addScore;
    }

    public void CheckFireballHit(ImageView fireball) {
        if (! canHitFireball){
            return;
        }
        Bounds FireballBounds = fireball.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                mainChar.getBoundsInParent().getMinX() + 20,
                mainChar.getBoundsInParent().getMinY() + 22,
                20,
                mainChar.getBoundsInParent().getHeight() / 2
        );
        if (FireballBounds.intersects(mainCharBounds) && fireball.isVisible()){
            System.out.println("FireBall hit detected");
            punk.setHp(punk.getHp() - 1);
            fireball.setTranslateY(0.0);
            fireball.setVisible(false);
            deleteHeart();
            canHitFireball = false;
            Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> canHitFireball = true));
            cooldownTimer.play();
        }
    }
    public void CheckGhostHit(ImageView ghost) {
        if (! canHitGhost){
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
            punk.setHp(punk.getHp() - 1);
            deleteHeart();
            canHitGhost = false;
            Timeline cooldownTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> canHitGhost = true));
            cooldownTimer.play();
        }
    }
    public void deleteHeart() {
        int size = hpBoard.getChildren().size();
        System.out.println("Size before deletion: " + size);
        if (size!=0) hpBoard.getChildren().remove(size-1);
        System.out.println(punk.getHp());
        if (punk.isDead()){
            return;
        }
        if (punk.getHp() == 0) {
            punk.setDead(true);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), this);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                try {
                    System.out.println("Game Over !");
                    Main.getInstance().changeSceneJava(GameOverPane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            fadeOut.play();
        }
    }
//    public void deleteHeart() {
//        int size = hpBoard.getChildren().size();
//        System.out.println("Size before deletion: " + size);
//        if (size != 0) hpBoard.getChildren().remove(size - 1);
//        System.out.println(punk.getHp());
//        if (punk.isDead()) {
//            return;
//            if (punk.getHp() == 0) {
//
//                punk.setDead(true);
//
//                FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), this);
//
//                fadeOut.setFromValue(1.0);
//                fadeOut.setToValue(0.0);
//
//                fadeOut.setOnFinished(event -> {
//                    try {
//                        Main.getInstance().changeSceneJava(GameOverPane.getInstance());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//                fadeOut.play();
//            }

//            if (punk.getHp() == 0) {
//                punk.setDead(true);
//                FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), this);
//                fadeOut.setFromValue(1.0);
//                fadeOut.setToValue(0.0);
//                fadeOut.setOnFinished(event -> {
//                    try {
//                        System.out.println("Game Over !");
//                        Main.getInstance().changeSceneJava(GameOverPane.getInstance());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//                });
//                fadeOut.play();
//            }
//        }
//    }

        public void addHeart () {
            if (hpBoard.getChildren().size() <= 3) {
                ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
                hp.setFitHeight(20);
                hp.setFitWidth(25);
                hpBoard.getChildren().add(hp);
            }
        }
        public void setTextScoreBoard (Text text){
            text.setFont(Font.font("Monospace", FontWeight.EXTRA_BOLD, 12));
            text.setFill(Color.rgb(48, 34, 3));
        }
        public void SetScoreboard () {
            ScoreBoard.getChildren().remove(1);
            Text Score = new Text("" + punk.getScore());
            setTextScoreBoard(Score);
            ScoreBoard.getChildren().add(Score);
        }
        public void CheckCoinHit (ImageView coin){
            Bounds CoinBounds = coin.getBoundsInParent();
            Bounds mainCharBounds = new BoundingBox(
                    mainChar.getBoundsInParent().getMinX() + 20,
                    mainChar.getBoundsInParent().getMinY(),
                    20,
                    mainChar.getBoundsInParent().getHeight() / 2
            );
            if (CoinBounds.intersects(mainCharBounds) && coin.isVisible() && coin.getTranslateY() >= punk.getYPos()) {
                punk.setScore(punk.getScore() + 1);
                coin.setTranslateY(0.0);
                coin.setVisible(false);
                SetScoreboard();
            }
        }
        public int randomIndex () {
            Random random = new Random();
            int randomIndex = random.nextInt(6);
            return randomIndex;
        }
        public void CoinFall () {
            Random random = new Random();

            ArrayList<Double> durations = new ArrayList<>();
            durations.add(3.0);
            durations.add(3.5);
            durations.add(4.0);
            durations.add(4.5);
            durations.add(2.0);
            durations.add(2.5);
            randomIndex = randomIndex();
            AnimationTimer FallDown = new AnimationTimer() {
                private long lastUpdate = 0;

                @Override
                public void handle(long currentTime) {
                    double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
                    if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                        Coin.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
                        Coin.setTranslateY(0.0);
                        Coin.setFitWidth(30);
                        Coin.setFitHeight(30);
                        SlideYPos(Coin, 1);
                        lastUpdate = currentTime;
                        randomIndex = randomIndex();
                    }
                    CheckCoinHit(Coin);
                }
            };
            FallDown.start();
        }
        public void CheckBoomHit (ImageView ghost){
            AnimationTimer checkHit = new AnimationTimer() {
                @Override
                public void handle(long currentTime) {
                    Bounds BoomBounds = Boom.getBoundsInParent();
                    Bounds GhostBounds = ghost.getBoundsInParent();
                    if (BoomBounds.intersects(GhostBounds) && Boom.isVisible()) {
                        // Don't forget to set HP of that ghost
                        getChildren().remove(ghost);
                    }
                }
            };
            checkHit.start();
        }

        public void RunAttackGhostAnimation () {
            ArrayList<Double> durations = new ArrayList<>();
            durations.add(2.0);
            durations.add(3.0);
            durations.add(1.5);
            durations.add(3.5);
            durations.add(2.5);
            durations.add(4.0);
            randomIndex = randomIndex();
            AnimationTimer GhostAnimationTimer = new AnimationTimer() {
                private long startTime = System.nanoTime();
                private long lastUpdate = 0;

                @Override
                public void handle(long currentTime) {
                    // Slide X axis
                    if (currentTime - lastUpdate >= 6_000_000_000L) {
                        SlideXPos(attackGhostImageView, 3);
                        lastUpdate = currentTime;
                    }
                    // Get Position & Set to Minions class
                    attackGhost.setXPos(getXPos(attackGhostImageView));
                    attackGhost.setYPos(getYPos(attackGhostImageView));
                    // Release Power
                    double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
                    if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                        FireBall.setLayoutX(attackGhost.getXPos() + 30);
                        FireBall.setTranslateY(50.0);
                        FireBall.setFitWidth(40);
                        FireBall.setFitHeight(40);
                        SlideYPos(FireBall, 1);
                        lastUpdate = currentTime;
                        randomIndex = randomIndex();
                    }

                    if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                        // Check fireball hit
                        CheckFireballHit(FireBall);
                    }
                }
            };
            GhostAnimationTimer.start();
        }

//        public void RunMinionAnimation () {
//            AnimationTimer GhostAnimationTimer = new AnimationTimer() {
//                private long startTime = System.nanoTime();
//                private long lastUpdate = 0;
//
//                @Override
//                public void handle(long currentTime) {
//                    // Slide X axis
//                    if (currentTime - lastUpdate >= 6_000_000_000L) {
//                        SlideXPos(attackGhostImageView, 3);
//                        if (currentTime - lastUpdate >= 10_000_000_000L) {
//                            SlideXPos(minionImageView, 5);
//                            lastUpdate = currentTime;
//                        }
//                        // Get Position & Set to Minions class
//                        attackGhost.setXPos(getXPos(attackGhostImageView));
//                        attackGhost.setYPos(getYPos(attackGhostImageView));
//                        // Release Power
//                        double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
//                        if (elapsedTimeSeconds >= durations.get(randomIndex)) {
//                            FireBall.setLayoutX(attackGhost.getXPos() + 30);
//                            FireBall.setTranslateY(50.0);
//                            FireBall.setFitWidth(40);
//                            FireBall.setFitHeight(40);
//                            SlideYPos(FireBall, 1);
//                            lastUpdate = currentTime;
//                            randomIndex = randomIndex();
//                        }
//
//                        if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
//                            // Check fireball hit
//                            CheckFireballHit(FireBall);
//                        }
//                    }
//                }
//            };
//            GhostAnimationTimer.start();
//        }

            public void RunMinionAnimation () {
                AnimationTimer GhostAnimationTimer = new AnimationTimer() {
                    private long startTime = System.nanoTime();
                    private long lastUpdate = 0;

                    @Override
                    public void handle(long currentTime) {
                        // Slide X axis
                        if (currentTime - lastUpdate >= 10_000_000_000L) {
                            SlideXPos(minionImageView, 5);
                            lastUpdate = currentTime;
                        }
                        // Get Position & Set to Minions class
                        minion.setXPos(getXPos(minionImageView));
                        minion.setYPos(getYPos(minionImageView));

                        // get random XPos
                        if (xPos_Down.size() < 20) {
                            xPos_Down.add(xPos_Down.size(), randXPos());
                        }
                        // Check xPos to goDown
                        int stay = (int) minionImageView.getTranslateX();
                        if (xPos_Down.contains(stay)) {
                            // remove used xPos
                            xPos_Down.remove(xPos_Down.indexOf(stay));
                            System.out.println("stay = " + stay + " go down !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                            if (currentTime - lastUpdate >= 4_000_000_000L) {
                                goDown(minionImageView);
                                lastUpdate = currentTime;
                            }
                        }

                        if (currentTime - startTime > TimeUnit.SECONDS.toNanos((long) 1)) {
                            // Check ghost hit
                            CheckGhostHit(minionImageView);
                        }
                    }
                };
                GhostAnimationTimer.start();
            }
            public double getXPos (ImageView imageView){
                return imageView.getTranslateX();
            }
            public double getYPos (ImageView imageView){
                return imageView.getTranslateY();
            }
            public void SlideYPos (ImageView imageView,int duration){
                imageView.setVisible(true);
                TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(duration), imageView);
                fallTransition.setFromY(0);
                fallTransition.setToY(545);
                fallTransition.setCycleCount(1);

                fallTransition.setOnFinished(event -> {
                    imageView.setTranslateY(0.0);
                    imageView.setVisible(false);
                });
                fallTransition.play();
            }
            public void SlideXPos (ImageView imageView,int duration){
                // Create TranslateTransition for left-right movement
                TranslateTransition translateXTransition = new TranslateTransition(Duration.seconds(duration), imageView);
                translateXTransition.setFromX(10);
                translateXTransition.setToX(1142 - imageView.getFitWidth());
                translateXTransition.setCycleCount(1);
                translateXTransition.setAutoReverse(true);
                translateXTransition.setOnFinished(event -> {
                    // Create another TranslateTransition to move back from right-left
                    TranslateTransition reverseTransition = new TranslateTransition(Duration.seconds(duration), imageView);
                    reverseTransition.setFromX(1142 - imageView.getFitWidth());
                    reverseTransition.setToX(10);
                    reverseTransition.setCycleCount(1);
                    reverseTransition.setAutoReverse(false);
                    reverseTransition.play();
                });

                translateXTransition.play();
            }
            public int randXPos () {
                int randXPos = ThreadLocalRandom.current().nextInt(10, 1062);
                return randXPos;
            }
            public void goDown (ImageView imageView){
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

            public void Shoot (Image image){
                // Set mainChar when Shoot
                mainChar.setImage(image);
                mainChar.setViewport(new Rectangle2D(96, 0, 48, 48));

                // Set Boom when start Shooting
                Boom.setVisible(true);
                Boom.setFitWidth(24);
                Boom.setFitHeight(120);
                Boom.setLayoutX(punk.getXPos() + 22);
                punk.setPunkShotXPos(Boom.getLayoutX());
                punk.setPunkShotYPos(Boom.getLayoutY());

                // Animate Boom moving upwards
                TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), Boom);
                transition.setByY(-453); // Move the Boom from its initial position (453) to 0
                transition.setOnFinished(event -> {
                    // Hide Boom and reset its position after the animation finishes
                    Boom.setVisible(false);
                    Boom.setLayoutY(mainChar.getLayoutY());

                    punk.setPunkShotXPos(Boom.getLayoutX());
                    punk.setPunkShotYPos(Boom.getLayoutY());
                    Boom.setTranslateY(0);
                });
                transition.play();
                punk.setPunkShotXPos(Boom.getLayoutX());
                punk.setPunkShotYPos(Boom.getTranslateY());
            }

            public void setMainChar (Image Image,int count, int column, int width, int height){
                mainChar.setImage(Image);
                SpriteAnimation.getInstance().setCount(count);
                SpriteAnimation.getInstance().setColumns(column);
                SpriteAnimation.getInstance().setWidth(width);
                SpriteAnimation.getInstance().setHeight(height);
                mainAni.setCycleCount(Animation.INDEFINITE);
                mainChar.setFitWidth(100);
                mainChar.setFitHeight(100);
                mainAni.play();
                setTopAnchor(mainChar, 453.0);
            }

            public static CaveMapPane getInstance () {
                if (instance == null) {
                    instance = new CaveMapPane();
                }
                return instance;
            }
        }
