package logic.character;

public class Minions extends Enemy { //can do nothing but player needs to avoid
    public Minions(){
        setHp(1);
    }
    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void effect(){}
}
