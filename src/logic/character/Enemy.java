package logic.character;

public abstract class Enemy { //template for every enemy
    int hp;
    public abstract void hitDamage();
    public abstract void effect();
    public void setHp(int hp){
        this.hp = Math.max(0, hp);
    }
    public int getHp(){
        return hp;
    }

}
