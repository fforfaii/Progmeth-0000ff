package logic.character;

public class AttackGhost extends Enemy { //normal ghost that can attack punk. no hit damage
    public AttackGhost(){
        setHp(1);
    }
    //need to check if hit or not in the GameLogic.update()
    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void effect(){}
}
