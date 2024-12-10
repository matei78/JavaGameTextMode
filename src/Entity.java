import java.util.ArrayList;
import java.util.Random;

public abstract class Entity implements Battle {
    public ArrayList<Spell> Abilities;
    public int currentHealth, maxHealth;
    public int currentMana, maxMana;
    public int DefaultDamageGiven;
    public boolean fire;
    public boolean ice;
    public boolean earth;

    public Entity(int maxHealth, int maxMana, int DefaultDamageGiven, boolean fire, boolean ice, boolean earth) {
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.currentHealth = maxHealth;
        this.DefaultDamageGiven = DefaultDamageGiven;
        this.fire = fire;
        this.ice = ice;
        this.earth = earth;
        this.Abilities = new ArrayList<>();
        Random rand = new Random();
        int x = rand.nextInt(3) + 1;
        Spell d1 = new Fire();
        Spell d2 = new Ice();
        Spell d3 = new Earth();
        this.Abilities.add(d1);
        this.Abilities.add(d2);
        this.Abilities.add(d3);
        for (int i = 0; i < x; i++) {
            int y = rand.nextInt(3) + 1;
            if (y == 1)
                this.Abilities.add(d1);
            if (y == 2)
                this.Abilities.add(d2);
            if (y == 3)
                this.Abilities.add(d3);
        }
    }

    public void RegenHealth(int x) {
        this.currentHealth = this.currentHealth + x;
        if (this.currentHealth > 100)
            this.currentHealth = 100;
    }

    public void RegenMana(int x) {
        this.currentMana = this.currentMana + x;
        if (this.currentMana > 100)
            this.currentMana = 100;
    }

    public void AbilitiesToString() {
        for (int i = 0; i < Abilities.size(); i++) {
            int k = i +1;
            System.out.println(String.valueOf(k) + ". " + Abilities.get(i).toString());
        }
    }


    public abstract int getDamage(Entity e, Spell abilty);

    public abstract void receiveDamage(int damage);

    public int UseAbility(Spell ability, Entity e, int nr) {
        int damage = 0;
        if(ability.name == "Ice" && this.currentMana >= ability.minMana) {
            damage = this.getDamage(e, ability);
            e.receiveDamage(damage);
            this.Abilities.remove(nr);
            this.currentMana = this.currentMana - ability.minMana;
            return 1;
            //return ability;
        }
        if(ability.name == "Fire" && this.currentMana >= ability.minMana) {
            damage = this.getDamage(e, ability);
            e.receiveDamage(damage);
            this.Abilities.remove(nr);
            this.currentMana = this.currentMana - ability.minMana;
            return 1;
            //return ability;
        }
        if(ability.name == "Earth" && this.currentMana >= ability.minMana) {
            damage = this.getDamage(e, ability);
            e.receiveDamage(damage);
            this.Abilities.remove(nr);
            this.currentMana = this.currentMana - ability.minMana;
            return 1;
            //r/eturn ability;
        }
        return 0;
    }

}
