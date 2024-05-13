package utils;

import java.util.ArrayList;

public class Constant {
    private static Constant instance;
    private final ArrayList<String> SKILLS_NAME;

    public Constant() {
        // Set skillsName ArrayList
        SKILLS_NAME = new ArrayList<>();
        SKILLS_NAME.add("ExtraScore");
        SKILLS_NAME.add("ExtraDamage");
        SKILLS_NAME.add("Heal");
        SKILLS_NAME.add("MoveFaster");
        SKILLS_NAME.add("Disappear");
        SKILLS_NAME.add("FasterAttack");
    }

    public static Constant getInstance() {
        if (instance == null) {
            instance = new Constant();
        }
        return instance;
    }

    public ArrayList<String> getSkillsName() {
        return SKILLS_NAME;
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
