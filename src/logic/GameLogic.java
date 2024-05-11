package logic;

import gui.GameOverPane;
import gui.HpBoard;
import gui.MapPane;
import gui.ScoreBoard;
import javafx.animation.*;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import logic.ability.Hitable;
import logic.character.*;
import logic.skills.*;
import main.Main;
import utils.Constant;

import javax.swing.plaf.multi.MultiMenuItemUI;
import java.io.IOException;
import java.security.Key;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class GameLogic {
    private static final ArrayList<Integer> HighScore = new ArrayList<>(Arrays.asList(0, 0, 0, 0));
    private static String currentMap;
    private static int randomIndex;
    private static boolean splashDelay = false;
    private static boolean isGameOver = false;
    private static boolean isLeftKeyPressed = false;
    private static boolean isRightKeyPressed = false;
    private static boolean isSpaceKeyPressed = false;
    private static Timeline continuousMovement = new Timeline();
    private static Timeline reverseContinuousMovement = new Timeline();

    public static void getPlayerInput(AnchorPane currentPane) {

        continuousMovement = new Timeline(new KeyFrame(Duration.millis(50), e -> {
            if (isLeftKeyPressed && isSpaceKeyPressed) {
                // Move left and shoot
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().runLeft();
                if (Punk.getInstance().isCanShoot()){
                    Punk.getInstance().shoot();
                    Punk.getInstance().setCanShoot(false);
                    Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(Punk.getInstance().getDelayShoot()), event -> Punk.getInstance().setCanShoot(true)));
                    delayShoot.play();
                }
            } else if (isRightKeyPressed && isSpaceKeyPressed) {
                // Move right and shoot
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().runRight();
                if (Punk.getInstance().isCanShoot()){
                    Punk.getInstance().shoot();
                    Punk.getInstance().setCanShoot(false);
                    Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(Punk.getInstance().getDelayShoot()), event -> Punk.getInstance().setCanShoot(true)));
                    delayShoot.play();
                }
            } else if (isLeftKeyPressed) {
                // Move left
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().runLeft();
            } else if (isRightKeyPressed) {
                // Move right
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().runRight();
            } else if (isSpaceKeyPressed && Punk.getInstance().isCanShoot()){
                System.out.println("Boom!");
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().shoot();
                Punk.getInstance().setCanShoot(false);
                Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(Punk.getInstance().getDelayShoot()), event -> Punk.getInstance().setCanShoot(true)));
                delayShoot.play();
            }
        }));
        continuousMovement.setCycleCount(Timeline.INDEFINITE);

        currentPane.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.A) {
                isLeftKeyPressed = true;
            } else if (keyCode == KeyCode.D) {
                isRightKeyPressed = true;
            } else if (keyCode == KeyCode.SPACE) {
                isSpaceKeyPressed = true;
            }
        });

        currentPane.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.A) {
                isLeftKeyPressed = false;
            } else if (keyCode == KeyCode.D) {
                isRightKeyPressed = false;
            } else if (keyCode == KeyCode.SPACE) {
                isSpaceKeyPressed = false;
            }
            Punk.getInstance().setPunkAnimation(Punk.getInstance().getPunkIdle(), 4, 4, 48, 48);
        });

        continuousMovement.play();
    }
    public static void reversePlayerInput(AnchorPane currentPane) {

        reverseContinuousMovement = new Timeline(new KeyFrame(Duration.millis(50), event -> {
            if (isRightKeyPressed && isSpaceKeyPressed) {
                // Move left and shoot
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().runLeft();
                if (Punk.getInstance().isCanShoot()){
                    Punk.getInstance().shoot();
                    Punk.getInstance().setCanShoot(false);
                    Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(Punk.getInstance().getDelayShoot()), e -> Punk.getInstance().setCanShoot(true)));
                    delayShoot.play();
                }
            } else if (isLeftKeyPressed && isSpaceKeyPressed) {
                // Move right and shoot
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().runRight();
                if (Punk.getInstance().isCanShoot()){
                    Punk.getInstance().shoot();
                    Punk.getInstance().setCanShoot(false);
                    Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(Punk.getInstance().getDelayShoot()), e -> Punk.getInstance().setCanShoot(true)));
                    delayShoot.play();
                }
            } else if (isRightKeyPressed) {
                // Move left
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().runLeft();
            } else if (isLeftKeyPressed) {
                // Move right
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().runRight();
            } else if (isSpaceKeyPressed && Punk.getInstance().isCanShoot()){
                System.out.println("Boom!");
                Punk.getInstance().setXPos(Punk.getInstance().getPunkImageView().getLayoutX());
                Punk.getInstance().shoot();
                Punk.getInstance().setCanShoot(false);
                Timeline delayShoot = new Timeline(new KeyFrame(Duration.seconds(Punk.getInstance().getDelayShoot()), e -> Punk.getInstance().setCanShoot(true)));
                delayShoot.play();
            }
        }));
        reverseContinuousMovement.setCycleCount(Timeline.INDEFINITE);

        currentPane.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.A) {
                isLeftKeyPressed = true;
            } else if (keyCode == KeyCode.D) {
                isRightKeyPressed = true;
            } else if (keyCode == KeyCode.SPACE) {
                isSpaceKeyPressed = true;
            }
        });

        currentPane.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            if (keyCode == KeyCode.A) {
                isLeftKeyPressed = false;
            } else if (keyCode == KeyCode.D) {
                isRightKeyPressed = false;
            } else if (keyCode == KeyCode.SPACE) {
                isSpaceKeyPressed = false;
            }
            Punk.getInstance().setPunkAnimation(Punk.getInstance().getPunkIdle(), 4, 4, 48, 48);
        });

        reverseContinuousMovement.play();
    }
    public static String randomSkill() {
        ArrayList<String> Skills = Constant.getInstance().getSkillsname();
        Random random = new Random();
        int randomIndex = random.nextInt(Skills.size());
        return Skills.get(randomIndex);
    }
    public static void setSkillImage(ImageView skillImageView, String skillName) {
        // Set icon that fall down
        switch (skillName) {
            case "Shield":
                skillImageView.setImage(new Image(ClassLoader.getSystemResource("shield.png").toString()));
                skillImageView.setFitHeight(30);
                skillImageView.setFitWidth(30);
                break;
            case "ExtraScore":
                skillImageView.setImage(new Image(ClassLoader.getSystemResource("extrascore.png").toString()));
                skillImageView.setFitHeight(35);
                skillImageView.setFitWidth(35);
                break;
            case "ExtraDamage":
                skillImageView.setImage(new Image(ClassLoader.getSystemResource("extradamage.png").toString()));
                skillImageView.setFitHeight(50);
                skillImageView.setFitWidth(50);
                break;
            case "Heal":
                skillImageView.setImage(new Image(ClassLoader.getSystemResource("heal.png").toString()));
                skillImageView.setFitHeight(50);
                skillImageView.setFitWidth(50);
                break;
            case "MoveFaster":
                skillImageView.setImage(new Image(ClassLoader.getSystemResource("movefaster.png").toString()));
                skillImageView.setFitHeight(50);
                skillImageView.setFitWidth(50);
                break;
            case "Disappear":
                skillImageView.setImage(new Image(ClassLoader.getSystemResource("disappear.png").toString()));
                skillImageView.setFitHeight(65);
                skillImageView.setFitWidth(65);
                break;
        }
    }
    public static void skillFall(ImageView skillImageView) {
        // Set falldown movement
        Random random = new Random();
        ArrayList<Double> durations = new ArrayList<>();
        durations.add(3.0);
        durations.add(3.5);
        durations.add(4.0);
        durations.add(4.5);
        durations.add(2.0);
        durations.add(2.5);
        randomIndex = randomIndex(); // for getting duration
        AnimationTimer FallDown = new AnimationTimer() {
            private long lastUpdate = 0;
            private String randSkill = GameLogic.randomSkill();
            @Override
            public void handle(long currentTime) {
                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    skillImageView.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
                    skillImageView.setTranslateY(0.0);
                    skillImageView.setFitWidth(40);
                    skillImageView.setFitHeight(40);
                    GameLogic.slideYPos(skillImageView, 2.0, 525);
                    lastUpdate = currentTime;
                    randomIndex = randomIndex();
                    randSkill = GameLogic.randomSkill();
                    setSkillImage(skillImageView, randSkill);
                }
                GameLogic.checkSkillHit(this.toString(), skillImageView, randSkill);
            }
        };
        FallDown.start();
    }
    public static void coinFall(ImageView coin){
        Random random = new Random();
        ArrayList<Double> durations = new ArrayList<>();
        durations.add(3.0);
        durations.add(3.5);
        durations.add(4.0);
        durations.add(4.5);
        durations.add(2.0);
        durations.add(2.5);
        randomIndex = randomIndex();
        AnimationTimer fallDown = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long currentTime) {
                double elapsedTimeSeconds = (currentTime - lastUpdate) / 1_000_000_000.0;
                if (elapsedTimeSeconds >= durations.get(randomIndex)) {
                    coin.setLayoutX(10.0 + (random.nextDouble() * (1060.0 - 10.0)));
                    coin.setTranslateY(0.0);
                    coin.setFitWidth(30);
                    coin.setFitHeight(30);
                    slideYPos(coin, 1.5, 535);
                    lastUpdate = currentTime;
                    randomIndex = randomIndex();
                }
                GameLogic.checkCoinHit(coin);
            }
        };
        fallDown.start();
    }
//    public static void slideCoin(ImageView coinImage) {
//        coinImage.setVisible(true);
//        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1.5), coinImage);
//        fallTransition.setFromY(0);
//        fallTransition.setToY(545);
//        fallTransition.setCycleCount(1);
//
//        fallTransition.setOnFinished(event -> {
//            coinImage.setTranslateY(0.0);
//            coinImage.setVisible(false);
//        });
//        fallTransition.play();
//    }
    public static void checkCoinHit(ImageView coinImage) {
        Bounds coinBounds = coinImage.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 30,
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() - 5
        );
        if (coinBounds.intersects(mainCharBounds) && coinImage.isVisible() && coinImage.getTranslateY() >= Punk.getInstance().getYPos() - 30){
            Punk.getInstance().setScore(Punk.getInstance().getScore() + Punk.getInstance().getScorePerCoin());
            coinImage.setTranslateY(0.0);
            coinImage.setVisible(false);
            ScoreBoard.getInstance().setScoreboard();
        }
    }
    public static void checkSkillHit(String map, ImageView skillImage,String skillname) {
        Bounds coinBounds = skillImage.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 30,
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() - 5
        );
        // If player can get skill
        if (coinBounds.intersects(mainCharBounds) && skillImage.isVisible() && skillImage.getTranslateY() >= Punk.getInstance().getYPos() - 30){
            skillImage.setTranslateY(0.0);
            skillImage.setVisible(false);
            // Call effect skillname
            switch (skillname) {
                case "Shield":
                    Shield.effect();
                    break;
                case "ExtraScore":
                    ExtraScore.effect(map);
                    break;
                case "ExtraDamage":
                    ExtraDamage.effect();
                    break;
                case "Heal":
                    Heal.effect();
                    break;
                case "MoveFaster":
                    MoveFaster.effect();
                    break;
                case "Disappear":
                    Disappear.effect();
                    break;
                case "FasterAttack":
                    FasterAttack.effect();
                    break;
            }
        }
    }
    public static void checkPunkShotHit(AnchorPane currentPane, ArrayList<Enemy> enemies) {
        AnimationTimer checkHit = new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                for (Enemy eachEnemy: enemies){
                    if (splashDelay){
                        return;
                    }
                    Bounds punkShotBounds = Punk.getInstance().getPunkShot().getBoundsInParent();
                    Bounds GhostBounds = eachEnemy.getImageView().getBoundsInParent();
                    if (punkShotBounds.intersects(GhostBounds) && Punk.getInstance().getPunkShot().isVisible()){
                        Punk.getInstance().getPunkShot().setVisible(false);
                        eachEnemy.setHp(eachEnemy.getHp() - 1); // Decrease Ghost HP when hit
                        if (eachEnemy instanceof Minion || eachEnemy instanceof AttackGhost) {
                            switch (eachEnemy.getHp()){
                                case 2:
                                    eachEnemy.getImageView().setOpacity(0.7);
                                    break;
                                case 1:
                                    eachEnemy.getImageView().setOpacity(0.3);
                                    break;
                            }
                        }
                        if (eachEnemy instanceof SlowGhost){
                            ((SlowGhost) eachEnemy).noDecreaseHP(); // Undecrease SlowGhost HP (immortal)
                        }
                        if (eachEnemy instanceof MindGhost){
                            ((MindGhost) eachEnemy).noDecreaseHP(); // Undecrease MindGhost HP (immortal)
                            Punk.getInstance().setMindGhostDelay(true);
                            Timeline mindGhostDelay = new Timeline(new KeyFrame(Duration.seconds(4), event -> Punk.getInstance().setMindGhostDelay(false)));
                        }
                        if (eachEnemy.getHp() == 0) {
                            currentPane.getChildren().remove(eachEnemy.getImageView());
                            if (eachEnemy instanceof AttackGhost){
                                // Get AttackGhost's Fireball out !
                                currentPane.getChildren().remove(((AttackGhost) eachEnemy).getFireBall());
                            }
                            if (eachEnemy instanceof PoisonGhost){
                                // Get PoisonGhost's Poison out !
                                currentPane.getChildren().remove(((PoisonGhost) eachEnemy).getPoison());
                            }
                        }
                        splashDelay = true;
                        Timeline delay = new Timeline(new KeyFrame(Duration.seconds(Math.max(0.5, Punk.getInstance().getDelayShoot() - 1)), event -> splashDelay = false));
                        delay.play();
                    }
                }
            }
        };
        checkHit.start();
    }
    //for AttackGhost
    public static void checkFireballHit(AnchorPane currentPane, ImageView fireball, AttackGhost attackGhost) {
        if (Punk.getInstance().isImmortalDelay()) {
            return;
        }
        if (!Punk.getInstance().isCanHit()) {
            return;
        }
        Bounds FireballBounds = fireball.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 22,
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() / 2
        );
        if (FireballBounds.intersects(mainCharBounds) && fireball.isVisible()) {
            System.out.println("FireBall hit detected");
            if (Punk.getInstance().isShield()){
                Shield.setIsHit(true);
                return;
            }
            attackGhost.hitDamage();
            fireball.setTranslateY(0.0);
            fireball.setVisible(false);
            deleteHeart(currentPane);
            Punk.getInstance().setImmortalDelay(true);
            Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> Punk.getInstance().setImmortalDelay(false)));
            delayTimer.play();
        }
    }

    //for PoisonGhost
    public static void checkPoisonHit(AnchorPane currentPane, ImageView poison, PoisonGhost poisonGhost) {
        if (Punk.getInstance().isImmortalDelay()) {
            return;
        }
        if (!Punk.getInstance().isCanHit()) {
            return;
        }
        Bounds FireballBounds = poison.getBoundsInParent();
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY() + 22,
                20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getHeight() / 2
        );

        if (FireballBounds.intersects(mainCharBounds) && poison.isVisible()) {
            System.out.println("Poison hit detected");
            if (Punk.getInstance().isShield()){
                Shield.setIsHit(true);
                return;
            }
            poisonGhost.hitDamage(currentPane);
            // set position of poison at the same as PoisonGhost
            poison.setTranslateY(poisonGhost.getYPos());
            poison.setTranslateX(poisonGhost.getXPos());
            poison.setVisible(false);
            Punk.getInstance().setImmortalDelay(true);
            Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> Punk.getInstance().setImmortalDelay(false)));
            delayTimer.play();
        }
    }

    //for every type of enemy
    public static void checkGhostHit(AnchorPane currentPane, Enemy enemy, ImageView enemyimageview) {
        Bounds ghostBounds = new BoundingBox(
                enemyimageview.getBoundsInParent().getMinX(),
                enemyimageview.getBoundsInParent().getMinY(),
                enemyimageview.getBoundsInParent().getWidth(),
                80
        );
        Bounds mainCharBounds = new BoundingBox(
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinX() + 20,
                Punk.getInstance().getPunkImageView().getBoundsInParent().getMinY(),
                20,
                100
        );
//        Rectangle playerRect = new Rectangle(mainCharBounds.getMinX(), mainCharBounds.getMinY(), mainCharBounds.getWidth(), mainCharBounds.getHeight());
//        playerRect.setFill(Color.TRANSPARENT);
//        playerRect.setStroke(Color.BLUE);
//        playerRect.setStrokeWidth(2);
//        currentPane.getChildren().add(playerRect);
//
//        Rectangle ghostRect = new Rectangle(GhostBounds.getMinX(), GhostBounds.getMinY(), GhostBounds.getWidth(), GhostBounds.getHeight());
//        ghostRect.setFill(Color.TRANSPARENT);
//        ghostRect.setStroke(Color.RED);
//        ghostRect.setStrokeWidth(2);
//        currentPane.getChildren().add(ghostRect);
//
//        Timeline rectLast = new Timeline(new KeyFrame(Duration.seconds(0.03), e -> {
//            currentPane.getChildren().remove(playerRect);
//            currentPane.getChildren().remove(ghostRect);
//        }));
//        rectLast.play();

        if (ghostBounds.intersects(mainCharBounds) && enemyimageview.isVisible()) {
            System.out.println("Ghost hit detected");
            if (enemy instanceof Hitable) {
                if (enemy instanceof Minion) {
                    if (Punk.getInstance().isImmortalDelay()){
                        System.out.println("Immortal Delay");
                        return;
                    }
                    ((Minion) enemy).hitDamage(currentPane);
                    deleteHeart(currentPane);
                    Punk.getInstance().setImmortalDelay(true);
                    Timeline delayTimer = new Timeline(new KeyFrame(Duration.seconds(3), event -> Punk.getInstance().setImmortalDelay(false)));
                    delayTimer.play();
                }
                if (enemy instanceof MindGhost) {
                    ((MindGhost) enemy).setCurrentPane(currentPane);
                    if (Punk.getInstance().isMindGhostDelay()){
                        System.out.println("Still MindGhost delay");
                    }
                    if (! Punk.getInstance().isMindGhostDelay()){
                        ((MindGhost) enemy).hitDamage(currentPane);
                        Punk.getInstance().setMindGhostDelay(true);
                        Timeline mindGhostDelay = new Timeline(new KeyFrame(Duration.seconds(4.5), e -> Punk.getInstance().setMindGhostDelay(false)));
                        mindGhostDelay.play();
                    }
                }
                if (enemy instanceof SlowGhost) {
                    ((SlowGhost) enemy).hitDamage(currentPane);
                }
            }
        }
    }
    public static void deleteHeart(AnchorPane currentPane) {
        int size = HpBoard.getInstance().getChildren().size();
        System.out.println("Size before deletion: " + size);
        if (size != 0) HpBoard.getInstance().getChildren().remove(size - 1);
        System.out.println(Punk.getInstance().getHp());
        if (Punk.getInstance().isDead()){
            return;
        }
        if (isGameOver()){
            return;
        }
        if (Punk.getInstance().getHp() == 0) {
            Punk.getInstance().setDead(true);
            setIsGameOver(true);
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), currentPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(event -> {
                try {
                    System.out.println("Game Over !");
//                    GameLogic.setHighscoreEachMap(Constant.getIndexMap(getCurrentMap()),Punk.getInstance().getScore());
                    Main.getInstance().changeSceneJava(GameOverPane.getInstance());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            fadeOut.play();
        }
    }
    public static void addHeart() {
        if (HpBoard.getInstance().getChildren().size() < 3){
            ImageView hp = new ImageView(new Image(ClassLoader.getSystemResource("heart.png").toString()));
            hp.setFitHeight(20);
            hp.setFitWidth(25);
            HpBoard.getInstance().getChildren().add(hp);
        }
    }
    public static void slideYPos(ImageView imageView, double duration, int ground) {
        imageView.setVisible(true);
        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(duration), imageView);
        fallTransition.setFromY(0);
        fallTransition.setToY(ground);
        fallTransition.setCycleCount(1);

        fallTransition.setOnFinished(event -> {
            imageView.setTranslateY(0.0);
            imageView.setVisible(false);
        });
        fallTransition.play();
    }

    public static double slideXPos(double lastXPos, ImageView imageView, int duration, double finalXpath) {
        // Create TranslateTransition for left-right movement
        TranslateTransition translateXTransition = new TranslateTransition(Duration.seconds(duration), imageView);
        translateXTransition.setFromX(lastXPos);
        translateXTransition.setToX(1142 - finalXpath); //final path
        translateXTransition.setCycleCount(1);
        translateXTransition.setAutoReverse(true);
        final double newLastXPos = randXPos();
        translateXTransition.setOnFinished(event -> {
            // Create another TranslateTransition to move back from right-left
            TranslateTransition reverseTransition = new TranslateTransition(Duration.seconds(duration), imageView);
            reverseTransition.setFromX(1142 - finalXpath);
            reverseTransition.setToX(newLastXPos);
            reverseTransition.setCycleCount(1);
            reverseTransition.setAutoReverse(false);
            reverseTransition.play();
        });
        translateXTransition.play();
        return newLastXPos;
    }
    public static double randXPos() {
        double randXPos = ThreadLocalRandom.current().nextDouble(10, 1062);
        return randXPos;
    }
    public static int randomIndex() {
        Random random = new Random();
        int randomIndex = random.nextInt(6);
        return randomIndex;
    }
    public static BackgroundImage getBGImage(String BGPath){
        String img_path = ClassLoader.getSystemResource(BGPath).toString();
        Image img = new Image(img_path);
        return new BackgroundImage(img, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1152,648,false,false,false,false));
    }
    public static ImageView getGroundImage(String ImgPath){
        Image groundImage = new Image(ClassLoader.getSystemResource(ImgPath).toString());
        return new ImageView(groundImage);
    }

    public static boolean isGameOver() {
        return isGameOver;
    }

    public static void setIsGameOver(boolean isGameOver) {
        GameLogic.isGameOver = isGameOver;
    }

    public static Timeline getContinuousMovement() {
        return continuousMovement;
    }

    public static Timeline getReverseContinuousMovement() {
        return reverseContinuousMovement;
    }
    public static String getCurrentMap() {
        return currentMap;
    }

    public static void setCurrentMap(String currentMapIn) {
        currentMap = currentMapIn;
    }

    public static void setHighscoreEachMap(int indexMap, int newscore) {
        int oldscore = HighScore.get(indexMap);
        if (newscore > oldscore) HighScore.set(indexMap, newscore);
    }
    public static int getHighscoreEachMap(String mapname){
        return HighScore.get(Constant.getIndexMap(mapname));
    }
}
