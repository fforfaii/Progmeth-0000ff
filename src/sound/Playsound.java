package sound;

import javafx.scene.media.AudioClip;

public class Playsound {
    private static final Playsound instance = new Playsound();
    public static AudioClip defaultBG;
    public static AudioClip CavemapBG;
    public static AudioClip ForestmapBG;
    public static AudioClip FactorymapBG;
    public static AudioClip JunglemapBG;
    public static AudioClip skillhit;
    public static AudioClip exit;
    public static AudioClip getcoin;
    public static AudioClip death;
    public static AudioClip gameoverBG;
    public static AudioClip poisonhit;
    public static AudioClip Playershoot;
    public static AudioClip ghostandfireballhit;

    static {
        loadResource();
    }

    public static void loadResource() {
        defaultBG = new AudioClip(ClassLoader.getSystemResource("backgroundgame.mp3").toString());
        defaultBG.setCycleCount(AudioClip.INDEFINITE);
        defaultBG.play();

        CavemapBG = new AudioClip(ClassLoader.getSystemResource("bgmapcave.mp3").toString());
        CavemapBG.setCycleCount(AudioClip.INDEFINITE);
        CavemapBG.setVolume(0.5);

        ForestmapBG = new AudioClip(ClassLoader.getSystemResource("bgmapforest.mp3").toString());
        ForestmapBG.setCycleCount(AudioClip.INDEFINITE);
        ForestmapBG.setVolume(0.5);

        FactorymapBG = new AudioClip(ClassLoader.getSystemResource("bgmapfactory.mp3").toString());
        FactorymapBG.setCycleCount(AudioClip.INDEFINITE);
        FactorymapBG.setVolume(0.5);

        JunglemapBG = new AudioClip(ClassLoader.getSystemResource("bgmapjungle.mp3").toString());
        JunglemapBG.setCycleCount(AudioClip.INDEFINITE);
        JunglemapBG.setVolume(0.5);

        skillhit = new AudioClip(ClassLoader.getSystemResource("skillhit.wav").toString());
        exit = new AudioClip(ClassLoader.getSystemResource("aboutgame.mp4").toString());
        getcoin = new AudioClip(ClassLoader.getSystemResource("getcoin.mp3").toString());
        death = new AudioClip(ClassLoader.getSystemResource("death.mp3").toString());
        gameoverBG = new AudioClip(ClassLoader.getSystemResource("gameover.mp3").toString());
        poisonhit = new AudioClip(ClassLoader.getSystemResource("poison.wav").toString());
        Playershoot = new AudioClip(ClassLoader.getSystemResource("shoot.mp4").toString());
        ghostandfireballhit = new AudioClip(ClassLoader.getSystemResource("ghostandfireballhit.mp4").toString());
    }

    public static void stopAllmapBG() {
        CavemapBG.stop();
        FactorymapBG.stop();
        ForestmapBG.stop();
        JunglemapBG.stop();
    }

    public static Playsound getInstance() {
        return instance;
    }
}
