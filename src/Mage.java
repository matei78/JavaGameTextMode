public class Mage extends Character1 {
    public Mage(String name, int level, int XP) {
        super(name, "Mage", XP, level, 0, 5, 0);
        this.ice = true;
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
