package logic.skills;

import logic.character.Punk;

public class Heal {
    public static void effect() {
        Punk.getInstance().setHp(Punk.getInstance().getHp() + 1);
    }
}
