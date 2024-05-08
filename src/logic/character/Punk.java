package logic.character;

public class Punk {
    double xPos;
    double yPos;
    private static Punk instance;
    private int hp;
    private int score;
    private int atk;
    private int speed;
    private int delayShoot;
    private double BoomxPos;
    private double BoomyPos;
    private boolean isDead;
    public Punk() {
        // always start at (0.0,453.0)
        setxPos(0); // ระยะห่างจากขอบซ้ายของ window
        setyPos(453.0); // ระยะห่างจากขอบบนของ window
        this.atk = 1;
        setDead(false);
        setScore(0);
        setHp(3);
        setSpeed(15);
        setDelayShoot(2);
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

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp >= 3){
            this.hp = 3;
        } else if (this.hp <= 0){
            this.hp = 0;
        } else {
            this.hp = hp;
        }
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
    public int getDelayShoot() {
        return delayShoot;
    }

    public void setDelayShoot(int delayShoot) {
        this.delayShoot = delayShoot;
    }

    public double getBoomxPos() {
        return BoomxPos;
    }

    public void setBoomxPos(double boomxPos) {
        BoomxPos = boomxPos;
    }

    public double getBoomyPos() {
        return BoomyPos;
    }

    public void setBoomyPos(double boomyPos) {
        BoomyPos = boomyPos;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
