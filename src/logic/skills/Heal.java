package logic.skills;

import gui.HpBoard;
import logic.GameLogic;
import logic.character.Punk;

public class Heal {
    public static void effect() {
        Punk.getInstance().setHp(Punk.getInstance().getHp() + 1);
        GameLogic.addHeart();
    }
}
