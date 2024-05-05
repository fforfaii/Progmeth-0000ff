package logic;

import gui.MapPane;

public class Punk {
    double xPos;
    private static Punk instance;
    public Punk() {
        // always start at (0,0)
        setxPos(0);
    }

    public static Punk getInstance() {
        if (instance == null) {
            instance = new Punk();
        }
        return instance;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }
}
