import java.util.Random;

public class Enemy extends Entity {

    public Enemy() {

        super(100, 100, 10, false, false, false);
        Random rand = new Random();
        int k = rand.nextInt(2);
        if (k == 0) {
            this.fire = true;
            this.earth = false;
            this.ice = true;
        }
        else {
            this.fire = false;
            this.earth = true;
            this.ice = false;
        }

        int x = rand.nextInt(30);
        int y = rand.nextInt(100) + 1;
        int z = rand.nextInt(100) + 1;
        this.DefaultDamageGiven = x;
        this.currentHealth = y;
        this.currentMana = z;
        Spell d1 = new Fire();
        Spell d2 = new Ice();
        Spell d3 = new Earth();
        int w = rand.nextInt(3) + 1;
        this.Abilities.add(d1);
        this.Abilities.add(d2);
        this.Abilities.add(d3);
        for (int i = 0; i < w; i++) {
            int q = rand.nextInt(3) + 1;
            if (q == 1)
                this.Abilities.add(d1);
            if (q == 2)
                this.Abilities.add(d2);
            if (q == 3)
                this.Abilities.add(d3);
        }
    }

    public String getEDetails() {
        return "Enemy " + this.currentHealth + " " + this.currentMana + " " + this.fire + " " + this.earth + " " + this.ice;
    }

        @Override
        public void receiveDamage(int damage) {
            Random rand = new Random();
            int x = rand.nextInt(2);
            if(x == 0)
                this.currentHealth -= damage;
            System.out.println(x);
            System.out.println("Enemy took " + damage + " damage");
        }
        @Override
        public int getDamage(Entity c, Spell ability) {
            Spell e = new Earth();
            Spell i = new Ice();
            Spell f = new Fire();
            Random rand = new Random();
            int j = rand.nextInt(2);
            int damage = 0;
            damage = damage + this.DefaultDamageGiven;
            if(j == 1)
                damage = damage * 2;
            if (ability == null) {
                return damage;
            }

            if (ability instanceof Earth) {
                if (c.earth == false) {
                    damage = e.damage;
                }
            }
            if (ability instanceof Ice) {
                if (c.ice == false) {
                    damage = i.damage;
                }
            }
            if (ability instanceof Fire) {
                if (c.fire == false) {
                    damage = f.damage;
                }
            }
            if (damage > 100)
                damage = 100;
            return damage;
        }



}
