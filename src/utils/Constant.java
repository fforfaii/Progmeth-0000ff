package utils;

import gui.FactoryMapPane;

import java.util.ArrayList;

public class Constant {
    private static Constant instance;
    ArrayList<String> skillsname;

    public Constant() {
        // Set skillsname ArrayList
        skillsname.add("Shield");
        skillsname.add("ExtraScore");
        skillsname.add("ExtraDamage");
        skillsname.add("Heal");
        skillsname.add("MoveFaster");
        skillsname.add("DamageFaster");
        skillsname.add("Disappear");
    }

    public static Constant getInstance() {
        if (instance == null) {
            instance = new Constant();
        }
        return instance;
    }

    public ArrayList<String> getSkillsname() {
        return skillsname;
    }

}
