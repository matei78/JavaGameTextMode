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


    public Game() {
        this.Map = null;
        this.Accounts = null;
        this.selectedAccount = null;
        this.w = null;
        this.m = null;
        this.r = null;
        this.player = null;
        this.levelReached = 0;
    }

    public String ShowOptions (Cell cell) throws Exception {
        int ox = cell.ox;
        int oy = cell.oy;
        if(ox != 0)
            System.out.println("Go north\n");
        if(ox != this.Map.length - 1)
            System.out.println("Go south\n");
        if(oy != 0)
            System.out.println("Go west\n");
        if(oy != this.Map.length - 1)
            System.out.println("Go east\n");
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        if(!line.equals("Go north") && !line.equals("Go south") && !line.equals("Go west") && !line.equals("Go east") && !line.equals("Exit"))
            throw new InvalidCommandException("Invalid Command");

        return line;
    }

    public void run() {
        Account account = null;
        //Character character = null;
        try {
            this.Accounts = JsonInput.deserializeAccounts();
        } catch (Exception e) {
            System.out.println("Account not available");
        }
        while(selectedAccount == null) {
            System.out.println("Enter credentials: ");
            Scanner sc = new Scanner(System.in);
            String email = sc.nextLine();
            String password = sc.nextLine();
            System.out.println(email.trim() + " " + password.trim());
            Credentials c = new Credentials(email.trim(), password.trim());
            for (int i = 0; i < this.Accounts.size(); i++) {
                Account a = this.Accounts.get(i);
                System.out.println(a.information.credentials.toString());
                if (a.information.credentials.equals(c)) {
                    System.out.println("You are already logged in");
                    this.selectedAccount = a;
                    System.out.println("Choose your character\n");
                    for (int j = 0; j < a.characters.size(); j++) {
                        System.out.println(a.characters.get(j).toString());
                    }
                    break;
                }
            }
            if (selectedAccount == null)
                System.out.println("Invalid Credentials");
        }
        while(this.w == null && this.r == null && this.m == null) {
            Scanner sc2 = new Scanner(System.in);
            String ChosenCharacter = sc2.nextLine(); //
            for (int k = 0; k < selectedAccount.characters.size(); k++) {
                if (selectedAccount.characters.get(k).profession.equals(ChosenCharacter))
                    if (selectedAccount.characters.get(k).profession.equals("Warrior")) {
                        this.w = new Warrior(selectedAccount.characters.get(k).name, selectedAccount.characters.get(k).level, selectedAccount.characters.get(k).XP);
                    }
                if (selectedAccount.characters.get(k).profession.equals("Mage")) {

                    this.m = new Mage(selectedAccount.characters.get(k).name, selectedAccount.characters.get(k).level, selectedAccount.characters.get(k).XP);
                }
                if (selectedAccount.characters.get(k).profession.equals("Rogue")) {
                    this.r = new Rogue(selectedAccount.characters.get(k).name, selectedAccount.characters.get(k).level, selectedAccount.characters.get(k).XP);
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
        for (int j = 1; j <= this.player.Abilities.size(); j++) {
            String ts = String.valueOf(j);
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

    public String Fight(Enemy e) throws Exception {
        String choice = null;
        int nr = -1;
        boolean usedab = false;
        while(this.player.currentHealth > 0 && e.currentHealth > 0) {
            while(choice == null) {
                try {
                    choice = showFightOptions();
                } catch (InvalidCommandException ex) {
                    System.out.println("Invalid Command");
                }
                if(choice.equals("Exit"))
                    return "Exit";
            }
            if(choice.equals("Ability attack")) {
                this.player.AbilitiesToString();
                while(nr == -1) {
                    try {
                        nr = chooseAbility();
                    }
                    catch(InvalidCommandException ex) {
                        System.out.println("Invalid Command");
                    }
                }
                if(this.player.Abilities.get(nr).name.equals("Fire")) {
                    Fire f = new Fire();
                    if(this.player.UseAbility(f,e) == 1)
                        usedab = true;

                }
                if(this.player.Abilities.get(nr).name.equals("Ice")) {
                    Ice i = new Ice();
                    if(this.player.UseAbility(i,e) == 1)
                        usedab = true;
                }
                if(this.player.Abilities.get(nr).name.equals("Earth")) {
                    Earth ea = new Earth();
                    if(this.player.UseAbility(ea,e) == 1)
                        usedab = true;
                }
            }
            if(choice.equals("Default attack") || (choice.equals("Ability attack") && usedab == false)) {
                int dmg = this.player.getDamage(e, null);
                e.receiveDamage(dmg);
            }
            //Se incheie turul caracterului incepe atacul inamicului
        }

        Random rand = new Random();
        int x = rand.nextInt(2);
        boolean valid2 = false;
        if(x == 1) {
            int y = rand.nextInt(e.Abilities.size()) + 1;
            String spe = e.Abilities.get(y).name;
            if(spe.equals("Fire")) {
                Fire f = new Fire();
                if(e.UseAbility(f,this.player) == 1)
                    valid2 = true;
            }
            else if(spe.equals("Ice")) {
                Ice i = new Ice();
                if(e.UseAbility(i,this.player) == 1)
                    valid2 = true;
            }
            else if(spe.equals("Earth")) {
                Earth ea = new Earth();
                if(e.UseAbility(ea, this.player) == 1)
                    valid2 = true;
            }
        }
        if(x == 0 || (x == 1 && valid2 == false)) {//atac normal
            int pdmg = e.getDamage(this.player, null);
            this.player.receiveDamage(pdmg);
        }
        return null;
    }
    
    


    public void Play() throws Exception {
        String option = null;
        int xx = -1;
        int yy = -1;
        boolean ver = false;
        boolean exit = false;
        Random rand = new Random();
        int px = rand.nextInt(5) + 5;
        int py = rand.nextInt(5) + 5;
        this.Map = Grid.Generation(px, py);
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
                exit = true;
                break;
            }
            while(ver == false) {

                try {
                    if (option != null) {
                        if(option.equals("Exit")) {
                            exit = true;
                            break;
                        }
                        if (option.equals("Go north")) {
                            this.nextCell = this.Map.cell.type;
                            this.Map.GoNorth();
                            ver = true;
                        }
                        if (option.equals("Go south")) {
                            this.nextCell = this.Map.cell.type;
                            this.Map.GoSouth();
                            ver = true;
                        }
                        if (option.equals("Go west")) {
                            this.nextCell = this.Map.cell.type;
                            this.Map.GoWest();
                            ver = true;
                        }
                        if (option.equals("Go east")) {
                            this.nextCell = this.Map.cell.type;
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
                        Random random = new Random();
                        int ran = random.nextInt(9) + 1;
                        this.player.RegenHealth(ran);
                        this.player.RegenMana(ran);
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
                        this.Map = Grid.Generation(this.Map.length, this.Map.width);
                        this.player.XP = this.player.XP + this.levelReached * 5;
                        this.levelReached++;
                    }
                }
            }
        }
    }


}
