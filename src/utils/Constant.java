package utils;

import java.util.ArrayList;

public class Constant {
    private static Constant instance;
    private ArrayList<String> skillsname;

    public Constant() {
        // Set skillsname ArrayList
        skillsname = new ArrayList<>();
        skillsname.add("ExtraScore");
        skillsname.add("ExtraDamage");
        skillsname.add("Heal");
        skillsname.add("MoveFaster");
        skillsname.add("Disappear");
        skillsname.add("FasterAttack");
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

    public static int getIndexMap(String mapname) {
        switch (mapname) {
            case "CaveMap":
                return 0;
            case "ForestMap":
                return 1;
            case "FactoryMap":
                return 2;
            case "JungleMap":
                return 3;
            default: return -1;
        }
    }
}
