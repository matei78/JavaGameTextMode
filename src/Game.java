import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public ArrayList<Account> Accounts;
    public Grid Map;
    public Account selectedAccount;
    public Warrior w;
    public Mage m;
    public Rogue r;
    public Character1 player;
    public int levelReached;
    public CellEntityType nextCell;
    public boolean gameOver;
    public boolean Logged;
    public boolean quitProgram;


    public Game() {
        this.Map = null;
        this.Accounts = null;
        this.selectedAccount = null;
        this.w = null;
        this.m = null;
        this.r = null;
        this.player = null;
        this.levelReached = 0;
        this.gameOver = false;
        this.Logged = false;
        this.quitProgram = false;
    }

    public String ShowOptions (Cell cell) throws Exception {
        int ox = cell.ox;
        int oy = cell.oy;
        if(ox != 0)
            System.out.println("Go north");
        if(ox != this.Map.length - 1)
            System.out.println("Go south");
        if(oy != 0)
            System.out.println("Go west");
        if(oy != this.Map.width - 1)
            System.out.println("Go east");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        if(!line.equals("Go north") && !line.equals("Go south") && !line.equals("Go west") && !line.equals("Go east") && !line.equals("Exit"))
            throw new InvalidCommandException("Invalid Command");
        if(ox == 0 && line.equals("Go north"))
            throw new InvalidCommandException("Impossible move");
        if(ox == this.Map.length - 1 && line.equals("Go south"))
            throw new InvalidCommandException("Impossible move");
        if(oy == 0 && line.equals("Go west"))
            throw new InvalidCommandException("Impossible move");
        if(oy == this.Map.width - 1 && line.equals("Go east"))
            throw new InvalidCommandException("Impossible move");



        return line;
    }

    public void run() throws Exception {
        if(this.Logged == false) {
            Account account = null;
            //Character character = null;

            try {
                this.Accounts = JsonInput.deserializeAccounts();
            } catch (Exception e) {
                System.out.println("Account not available");
            }
            while (selectedAccount == null && this.gameOver == false) {
                System.out.println("Enter credentials: ");
                Scanner sc = new Scanner(System.in);
                String email = sc.nextLine();
                String password = sc.nextLine();
                if (email.equals("Exit") || password.equals("Exit")) {
                    this.gameOver = true;
                }
                if (this.gameOver == true)
                    return;
                Credentials c = new Credentials(email.trim(), password.trim());
                for (int i = 0; i < this.Accounts.size(); i++) {
                    Account a = this.Accounts.get(i);
                    if (a.information.credentials.equals(c)) {
                        this.selectedAccount = a;



                        break;
                    }
                }
                if (selectedAccount == null)
                    System.out.println("Invalid Credentials");
            }
            if(gameOver == false && selectedAccount != null) {
                this.Logged = true;
            }
        }
        while(this.w == null && this.r == null && this.m == null && this.gameOver == false) {
            for (int j = 0; j < this.selectedAccount.characters.size(); j++) {
                System.out.println(this.selectedAccount.characters.get(j).toString());
            }
            System.out.println("Choose your character\n");
            String ChosenCharacter = "";
            while(!ChosenCharacter.equals("Warrior") && !ChosenCharacter.equals("Rogue") && !ChosenCharacter.equals("Mage")) {
                Scanner sc2 = new Scanner(System.in);
                ChosenCharacter = sc2.nextLine();

                if (ChosenCharacter.equals("Quit program")) {
                    this.gameOver = true;
                    this.quitProgram = true;
                    break;
                }
                if(!ChosenCharacter.equals("Warrior") && !ChosenCharacter.equals("Rogue") && !ChosenCharacter.equals("Mage"))
                    System.out.println("Invalid Character");
            }
            //if(!ChosenCharacter.equals("Warrior") && !ChosenCharacter.equals("Rogue") && !ChosenCharacter.equals("Mage"))
                //throw new InvalidCommandException("Invalid command");
            for (int k = 0; k < selectedAccount.characters.size(); k++) {
                if (selectedAccount.characters.get(k).profession.equals(ChosenCharacter)) {
                    if (selectedAccount.characters.get(k).profession.equals("Warrior")) {
                        this.w = new Warrior(selectedAccount.characters.get(k).name, selectedAccount.characters.get(k).level, selectedAccount.characters.get(k).XP);
                    }
                    if (selectedAccount.characters.get(k).profession.equals("Mage")) {

                        this.m = new Mage(selectedAccount.characters.get(k).name, selectedAccount.characters.get(k).level, selectedAccount.characters.get(k).XP);
                    }
                    if (selectedAccount.characters.get(k).profession.equals("Rogue")) {
                        this.r = new Rogue(selectedAccount.characters.get(k).name, selectedAccount.characters.get(k).level, selectedAccount.characters.get(k).XP);
                    }
                }
                //break;
            }
        }

    }
    
    public String showFightOptions() throws Exception {
        String s = null;
        System.out.println("Default attack\n");
        System.out.println("Ability attack\n");
        Scanner sc = new Scanner(System.in);
        String o = sc.nextLine();
        if(!o.equals("Default attack") && !(o.equals("Ability attack")) && !(o.equals("Exit")))
            throw new InvalidCommandException("Invalid Command");
        else if (o.equals("Ability attack")) {
            //this.player.AbilitiesToString();
            s = o;
        }
        else s = o;
        return s;
    }

    public int chooseAbility() throws Exception {
        boolean valid = false;
        Scanner sc = new Scanner(System.in);
        String number = sc.nextLine();
        if(number.equals("Exit"))
            return -2;
        for (int j = 0; j < this.player.Abilities.size(); j++) {
            String ts = String.valueOf(j + 1);
            if(number.equals(ts)) {
                valid = true;
                break;
            }
        }
        if(valid == false)
            throw new InvalidCommandException("Invalid Command");
        else
            return Integer.parseInt(number);

    }

    public void EnemyAttack(Enemy e) {
        Random rand = new Random();
        int x = rand.nextInt(2);
        boolean valid2 = false;
        boolean verifyListEmptybefore = false;
        if(x == 1 && !e.Abilities.isEmpty()) {

            int y = rand.nextInt(e.Abilities.size());
            String spe = e.Abilities.get(y).name;
            if(spe.equals("Fire")) {
                Fire f = new Fire();
                if(e.UseAbility(f,this.player,y) == 1) {
                    valid2 = true;
                    System.out.println("Enemy attacked with ability Fire!");
                    if(e.Abilities.isEmpty())
                        verifyListEmptybefore = true;
                }
            }
            else if(spe.equals("Ice")) {
                Ice i = new Ice();
                if(e.UseAbility(i,this.player,y) == 1) {
                    valid2 = true;
                    System.out.println("Enemy attacked with ability Ice!");
                    if(e.Abilities.isEmpty())
                        verifyListEmptybefore = true;
                }
            }
            else if(spe.equals("Earth")) {
                Earth ea = new Earth();
                if(e.UseAbility(ea, this.player,y) == 1) {
                    valid2 = true;
                    System.out.println("Enemy attacked with ability Earth!");
                    if(e.Abilities.isEmpty())
                        verifyListEmptybefore = true;
                }
            }
        }
        if(x == 0 || (x == 1 && valid2 == false) || (x == 1 && e.Abilities.isEmpty() && verifyListEmptybefore == false)) {//atac normal
            int pdmg = e.getDamage(this.player, null);
            this.player.receiveDamage(pdmg);
        }

    }

    public String Fight(Enemy e) throws Exception {
        String choice = "";
        int nr = -1;
        boolean usedab = false;
        while(this.player.currentHealth > 0 && e.currentHealth > 0 && !choice.equals("Exit")) {
            System.out.println("Character attacks");
            System.out.println(this.player.getDetails());
            System.out.println(e.getEDetails());

            choice = "";
            nr = -1;
            while(choice.equals("")) {
                try {
                    choice = showFightOptions();
                } catch (InvalidCommandException ex) {
                    System.out.println("Invalid Command");
                }
                if(choice.equals("Exit"))
                    return "Exit";
            }
            if(choice.equals("Ability attack") && !this.player.Abilities.isEmpty()) {
                    this.player.AbilitiesToString();
                while(nr == -1) {
                    try {
                        nr = chooseAbility();
                    }
                    catch(InvalidCommandException ex) {
                        System.out.println("Invalid Command");
                    }
                }
                if(nr == -2)
                    return "Exit";
                System.out.println(nr);
                if(this.player.Abilities.get(nr - 1).name.equals("Fire")) {
                    Fire f = new Fire();
                    if(this.player.UseAbility(f,e,nr - 1) == 1) {
                        usedab = true;
                        //System.out.println("Used Ability fire");
                    }
                    else {
                        System.out.println("No mana executin Default");
                        choice = "Default attack";
                    }

                }
                else {
                    if (this.player.Abilities.get(nr - 1).name.equals("Ice")) {
                        Ice i = new Ice();
                        if (this.player.UseAbility(i, e, nr - 1) == 1) {
                            usedab = true;
                        System.out.println("Used Ability ice");
                        }
                        else {
                            System.out.println("No mana executin Default");
                            choice = "Default attack";
                        }

                    }
                    else {
                        if (this.player.Abilities.get(nr - 1).name.equals("Earth")) {
                            Earth ea = new Earth();
                            if (this.player.UseAbility(ea, e, nr - 1) == 1) {
                                usedab = true;
                                System.out.println("Used Ability earth");
                            }
                            else {
                                System.out.println("No mana executin Default");
                                choice = "Default attack";
                            }

                        }
                    }
                }
            }
            else
                if(choice.equals("Ability attack") && this.player.Abilities.isEmpty()) {
                    System.out.println("No available ability. Executing default attack");
                    choice = "Default attack";
                }
            if(choice.equals("Default attack") || (choice.equals("Ability attack") && usedab == false)) {
                int dmg = this.player.getDamage(e, null);
                e.receiveDamage(dmg);
            }
            //Se incheie turul caracterului incepe atacul inamicului
            if(e.currentHealth <= 0) {
                System.out.println("You defetead the enemy");
                if(this.player.Abilities.isEmpty())
                    this.player.PopulateAbilities();

                break;
            }
            System.out.println("Enemy attacks");
            this.EnemyAttack(e);
        }
        return choice;
    }
    
    


    public void Play(String s) throws Exception {
        if(this.gameOver == true) {
            return;
        }
        String option = null;
        int xx = -1;
        int yy = -1;
        boolean ver = false;
        boolean exit = false;
        Random rand = new Random();
        int px = rand.nextInt(5) + 5;
        int py = rand.nextInt(5) + 5;
        px = 5;
        py = 5;
        if(s.equals("Main"))
            this.Map = Grid.Generation(px, py);
        else {
            this.Map = Grid.Generation(5, 5);
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 5; j++)
                    this.Map.get(i).get(j).type = CellEntityType.VOID;
            this.Map.get(0).get(0).type = CellEntityType.PLAYER;
            this.Map.get(0).get(3).type = CellEntityType.SANCTUARY;
            this.Map.get(3).get(0).type = CellEntityType.SANCTUARY;
            this.Map.get(1).get(3).type = CellEntityType.SANCTUARY;
            this.Map.get(4).get(3).type = CellEntityType.SANCTUARY;
            this.Map.get(3).get(4).type = CellEntityType.ENEMY;
            this.Map.get(4).get(4).type = CellEntityType.PORTAL;
        }



        for (int i = 0; i < px; i++)
            for (int j = 0; j < py; j++) {
                if(this.Map.get(i).get(j).type == CellEntityType.PLAYER) {
                    xx = i;
                    yy = j;
                }
            }

        this.Map.cell = new Cell(xx, yy, CellEntityType.PLAYER, 2);
        if(this.m != null)
            this.player = this.m;
        if(this.r != null)
            this.player = this.r;
        if(this.w != null)
            this.player = this.w;

        while(this.player.currentHealth > 0 && exit == false) {
            ver = false;
            this.Map.ShowGrid();
            while(option == null) {
                try {
                    option = this.ShowOptions(this.Map.cell);
                }
                catch(InvalidCommandException e) {
                    System.out.println("Invalid Command");
                }
            }
            if(option.equals("Exit")) {
                //exit = true;
                break;
            }
            while(ver == false) {
                try {
                    System.out.println(this.Map.cell.toString());
                    if (option != null) {
                        if(option.equals("Exit")) {
                            exit = true;
                            break;
                        }
                        if (option.equals("Go north")) {
                            System.out.println(this.nextCell);
                            this.nextCell = this.Map.get(this.Map.cell.ox - 1).get(this.Map.cell.oy).type;  //salvez tot player

                            this.Map.GoNorth();
                            ver = true;
                        }
                        if (option.equals("Go south")) {
                            this.nextCell = this.Map.get(this.Map.cell.ox + 1).get(this.Map.cell.oy).type;
                            this.Map.GoSouth();
                            ver = true;
                        }
                        if (option.equals("Go west")) {
                            this.nextCell = this.Map.get(this.Map.cell.ox).get(this.Map.cell.oy - 1).type;
                            this.Map.GoWest();
                            ver = true;
                        }
                        if (option.equals("Go east")) {
                            this.nextCell = this.Map.get(this.Map.cell.ox).get(this.Map.cell.oy + 1).type;
                            this.Map.GoEast();
                            ver = true;
                        }
                    }
                } catch (ImpossibleMoveException e) {
                    System.out.println("Impossible move");

                        try {
                            option = this.ShowOptions(this.Map.cell);
                        } catch (InvalidCommandException e1) {
                            System.out.println("Invalid Command");
                        }
                    }
                    if(option.equals("Exit")) {
                        exit = true;
                        break;

                }

            } // am ajuns pe celula urmatoare verific tipul ei
            option = null;
            System.out.println(this.nextCell);
            if(exit == false) {
                switch (this.nextCell) {
                    case CellEntityType.SANCTUARY -> {
                        System.out.println("you landed on sanctuary");
                        Random random = new Random();
                        int ran = random.nextInt(9) + 1;
                        this.player.RegenHealth(ran);
                        this.player.RegenMana(ran);
                        System.out.println("New health: " + this.player.currentHealth);
                        System.out.println(("New mana: " + this.player.currentMana));
                    }
                    case CellEntityType.ENEMY -> {
                        System.out.println("Battle begin");
                        Enemy e = new Enemy();
                        String z = Fight(e);
                        if(z.equals("Exit"))
                            exit = true;
                        if (this.player.currentHealth <= 0 && exit == false) {
                            break; // ies din toata functia
                        } else {
                            this.player.RegenHealth(this.player.currentHealth);
                            this.player.RegenMana(100);
                            Random r = new Random();
                            int rxp = r.nextInt(3) + 1;
                            this.player.XP = this.player.XP + rxp;

                        }
                    }
                    case CellEntityType.PORTAL -> {
                        System.out.println("you landed on portal");
                        this.Map = Grid.Generation(this.Map.length, this.Map.width);
                        for (int i = 0; i < px; i++)
                            for (int j = 0; j < py; j++) {
                                if(this.Map.get(i).get(j).type == CellEntityType.PLAYER) {
                                    xx = i;
                                    yy = j;
                                }
                            }

                        this.Map.cell = new Cell(xx, yy, CellEntityType.PLAYER, 2);
                        this.player.XP = this.player.XP + this.levelReached * 5;
                        this.levelReached++;
                        this.player.UpdateLevel();
                        if(this.player.level > 50)
                            this.player.UpdateAttributes(); ////Grija
                    }
                }
            }
        }
    }


}
