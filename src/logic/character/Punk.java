package logic.character;

public class Punk {
    double xPos;
    private static Punk instance;
    private int hp;
    private int score;
    private int atk;
    private int speed;
    public Punk() {
        // always start at (0,0)
        setxPos(0);
        this.atk = 1;
        setScore(0);
        setHp(3);
        setSpeed(15);
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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = Math.max(0, score);
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = Math.max(0, speed);
    }
}
