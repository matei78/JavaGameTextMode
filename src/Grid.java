import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>> {
    public int length;
    public int width;
    public Character1 character;
    public Cell cell;
    private Grid(int length, int width) {
        this.length = length;
        this.width = width;
        this.character = null;
        this.cell = null;
    }

    public static Grid Generation(int length, int width) {
        if(length > 10)
            length = 10;
        if(width > 10)
            width = 10;

        Random rand = new Random();
        int r;
        int randx_portal = rand.nextInt(length);
        int randy_portal = rand.nextInt(width);
        int randx_player = rand.nextInt(length);
        int randy_player = rand.nextInt(width);
        int rand_no_sanctuary = rand.nextInt(8) + 2;
        int rand_no_enemy = rand.nextInt(8) + 4;
        if(randx_portal == randx_player)
            randx_portal = 1;
        int cntS = 0;
        int cntE = 0;
        Grid grid = new Grid(length, width);
        Cell c;

        for (int i = 0; i < length; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                if(randx_portal == i && randy_portal == j)
                    c = new Cell(i, j, CellEntityType.PORTAL, 0);
                else
                    if(randx_player == i && randy_player == j) {
                        c = new Cell(i, j, CellEntityType.PLAYER, 0);
                        //Grid.cell = c;
                    }
                    else {
                        r = rand.nextInt(3);
                        if(r == 1 && cntS < rand_no_sanctuary) {
                            c = new Cell(i, j, CellEntityType.SANCTUARY, 0);
                            cntS++;
                        }
                        else
                            if(r == 2 && cntE < rand_no_enemy) {
                                c = new Cell(i, j, CellEntityType.ENEMY, 0);
                                cntE++;
                        }
                        else
                            c = new Cell(i, j, CellEntityType.VOID, 0);
                    }
                row.add(c);
            }
            grid.add(row);
        }
        return grid;
    }

    public void ShowGrid() {
        for(int i = 0; i < length; i++) {
            for(int j = 0; j < width; j++) {
                if(this.get(i).get(j).type == CellEntityType.PLAYER)
                    System.out.print("P  ");
                if(this.get(i).get(j).type == CellEntityType.VOID && this.get(i).get(j).status == 1)
                    System.out.print("V  ");
                if(!(this.get(i).get(j).type == CellEntityType.PLAYER) && !(this.get(i).get(j).type == CellEntityType.VOID && this.get(i).get(j).status == 1))
                    System.out.print("N  ");
            }
            System.out.println("");
        }
    }

    public void GoNorth() throws Exception {
        if(this.cell.oy == 0)
            throw new ImpossibleMoveException("Forbidden move");
        else {
            int x = this.cell.ox;
            int y = this.cell.oy;
            this.cell.oy--;
            this.get(x).set(this.cell.oy, this.cell);
            this.get(x).set(y, new Cell(x, y, CellEntityType.VOID, 1));
        }

    }
    public void GoSouth() throws Exception {
        if(this.cell.oy == this.width - 1)
            throw new ImpossibleMoveException("Forbidden move");
        else {
            int x = this.cell.ox;
            int y = this.cell.oy;
            this.cell.oy++;
            this.get(x).set(this.cell.oy, this.cell);
            this.get(x).set(y, new Cell(x, y, CellEntityType.VOID, 1));
        }
    }
    public void GoWest() throws Exception {
        if(this.cell.ox == 0)
            throw new ImpossibleMoveException("Forbidden move");
        else {
            int x = this.cell.ox;
            int y = this.cell.oy;
            this.cell.ox--;
            this.get(this.cell.ox).set(this.cell.oy, this.cell);
            this.get(x).set(y, new Cell(x, y, CellEntityType.VOID, 1));
        }
    }
    public void GoEast() throws Exception {
        if(this.cell.ox == this.length - 1)
            throw new ImpossibleMoveException("Forbidden move");
        else {
            int x = this.cell.ox;
            int y = this.cell.oy;
            this.cell.ox++;
            this.get(this.cell.ox).set(this.cell.oy, this.cell);
            this.get(x).set(y, new Cell(x, y, CellEntityType.VOID, 1));
        }
    }
}

