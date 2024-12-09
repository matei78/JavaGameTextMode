public class Rogue extends Character1 {
    public Rogue(String name, int level, int XP) {
        super(name,"Rogue", XP, level, 0, 0, 5);
        this.earth = true;
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
