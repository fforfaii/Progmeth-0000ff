package logic.character;


import javafx.scene.image.ImageView;

public class MindGhost extends Enemy { //if hit: inverted control
    public MindGhost(){
        setHp(1);
    }
    //need to check if hit or not in the GameLogic.update()
    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void effect(){

    }
    public void runAnimation(){

    }

    @Override
    public ImageView getImageView() {
        return null;
    }
}
