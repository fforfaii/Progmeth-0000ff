package logic.character;


import javafx.animation.Animation;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import logic.ability.GoDownable;
import logic.ability.Hitable;

public class MindGhost extends Enemy implements Hitable, GoDownable { //if hit: inverted control
    private static MindGhost instance;
    private double xPos;
    private double yPos;
    private ImageView mindGhostImageView;
    private Animation mindghostAnimation;
    public MindGhost(){
        setHp(1);
    }
    //need to check if hit or not in the GameLogic.update()
    @Override
    public void hitDamage() {

    }
    public void effect(){

    }
    public void runAnimation(AnchorPane currentPane){

    }

    @Override
    public ImageView getImageView() {
        return null;
    }

    public static MindGhost getInstance() {
        if (instance == null) {
            instance = new MindGhost();
        }
        return instance;
    }

    @Override
    public void goDown(ImageView imageView) {

    }
}
