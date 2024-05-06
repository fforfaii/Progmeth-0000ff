package logic.character;

public class PoisonGhost extends Enemy { //if punk get poison: cannot attack for 3 secs
    public PoisonGhost(){
        setHp(1);
    }
    //need to check if hit or not in the GameLogic.update()
    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void effect(){}
}
