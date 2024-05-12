package sound;

import javafx.scene.media.AudioClip;

public class PlaySound {
    private static final PlaySound instance = new PlaySound();
    public static AudioClip defaultBG;
    public static AudioClip caveMapBG;
    public static AudioClip forestMapBG;
    public static AudioClip factoryMapBG;
    public static AudioClip jungleMapBG;
    public static AudioClip skillHit;
    public static AudioClip exit;
    public static AudioClip getCoin;
    public static AudioClip death;
    public static AudioClip gameOverBG;
    public static AudioClip poisonHit;
    public static AudioClip playerShoot;
    public static AudioClip ghostAndFireballHit;

    static {
        loadResource();
    }

    public static void loadResource() {
        defaultBG = new AudioClip(ClassLoader.getSystemResource("backgroundgame.mp3").toString());
        defaultBG.setCycleCount(AudioClip.INDEFINITE);
        defaultBG.play();

        caveMapBG = new AudioClip(ClassLoader.getSystemResource("bgmapcave.mp3").toString());
        caveMapBG.setCycleCount(AudioClip.INDEFINITE);
        caveMapBG.setVolume(0.5);

        forestMapBG = new AudioClip(ClassLoader.getSystemResource("bgmapforest.mp3").toString());
        forestMapBG.setCycleCount(AudioClip.INDEFINITE);
        forestMapBG.setVolume(0.5);

        factoryMapBG = new AudioClip(ClassLoader.getSystemResource("bgmapfactory.mp3").toString());
        factoryMapBG.setCycleCount(AudioClip.INDEFINITE);
        factoryMapBG.setVolume(0.5);

        jungleMapBG = new AudioClip(ClassLoader.getSystemResource("bgmapjungle.mp3").toString());
        jungleMapBG.setCycleCount(AudioClip.INDEFINITE);
        jungleMapBG.setVolume(0.5);

        skillHit = new AudioClip(ClassLoader.getSystemResource("skillhit.wav").toString());
        exit = new AudioClip(ClassLoader.getSystemResource("exit.mp4").toString());
        getCoin = new AudioClip(ClassLoader.getSystemResource("getcoin.mp3").toString());
        death = new AudioClip(ClassLoader.getSystemResource("death.mp3").toString());
        gameOverBG = new AudioClip(ClassLoader.getSystemResource("gameover.mp3").toString());
        poisonHit = new AudioClip(ClassLoader.getSystemResource("poison.wav").toString());
        playerShoot = new AudioClip(ClassLoader.getSystemResource("shoot.mp4").toString());
        ghostAndFireballHit = new AudioClip(ClassLoader.getSystemResource("ghostandfireballhit.mp4").toString());
    }

    public static void stopAllmapBG() {
        caveMapBG.stop();
        factoryMapBG.stop();
        forestMapBG.stop();
        jungleMapBG.stop();
    }

    public static PlaySound getInstance() {
        return instance;
    }
}
