public class Warrior extends Character1 {
    //this.Fire = true;

    public Warrior(String name, int level, int XP) {
        super(name, "Warrior", XP, level, 5, 0, 0);
        this.fire = true;
    }
    @Override
    public int getDamage(Entity enemy, Spell ability) {
        return super.getDamage(enemy, ability);
    }
    @Override
    public void receiveDamage(int damage) {
        super.receiveDamage(damage);
    }
}
