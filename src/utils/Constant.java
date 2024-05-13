package utils;

import java.util.ArrayList;

public class Constant {
    private static Constant instance;
    private final ArrayList<String> skillsName;

    public Constant() {
        // Set skillsName ArrayList
        skillsName = new ArrayList<>();
        skillsName.add("ExtraScore");
        skillsName.add("ExtraDamage");
        skillsName.add("Heal");
        skillsName.add("MoveFaster");
        skillsName.add("Disappear");
        skillsName.add("FasterAttack");
    }

    public static Constant getInstance() {
        if (instance == null) {
            instance = new Constant();
        }
        return instance;
    }

    public ArrayList<String> getSkillsName() {
        return skillsName;
    }

    public static int getIndexMap(String mapName) {
        switch (mapName) {
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
