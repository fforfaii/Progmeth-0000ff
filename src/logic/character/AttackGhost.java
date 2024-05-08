package logic.character;

public class AttackGhost extends Enemy { //normal ghost that can attack punk. no hit damage
    private static AttackGhost instance;
    private double xPos;
    private double yPos;
    public AttackGhost(){
        setHp(1);
    }
    public void hitDamage(){
        Punk.getInstance().setHp(Punk.getInstance().getHp() - 1);
    }
    public void effect(){}

    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public static AttackGhost getInstance() {
        if (instance == null) {
            instance = new AttackGhost();
        }
        return instance;
    }
}