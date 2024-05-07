package logic.skills;

import logic.character.Punk;

public class Heal {
    public void effect(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() + 1);
    }
}
