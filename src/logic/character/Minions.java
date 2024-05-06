package logic.character;

public class Minions extends Enemy { //can do nothing but player needs to avoid
    private static Minions instance;
    double xPos;
    double yPos;
    public Minions(){
        setHp(1);
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public static Minions getInstance() {
        if (instance == null) {
            instance = new Minions();
        }
        return instance;
    }

    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void effect(){}
}
