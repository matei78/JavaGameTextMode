public abstract class Spell {
    public String name;
    public int minMana;
    public int damage;
    public Spell(String name, int minMana, int damage) {
        this.name = name;
        this.minMana = minMana;
        this.damage = damage;
    }

    public String toString() {

        return this.name + " " + this.minMana + " " + this.damage;
    }
}
