public class Cell {
    public int ox;
    public int oy;
    public CellEntityType type;
    public int status;
    public Cell(int ox, int oy, CellEntityType type, int status) {
        this.ox = ox;
        this.oy = oy;
        this.type = type;
        this.status = status;
    }



    public void changeType(Cell cell) {
        if(cell.status == 1)
            cell.type = CellEntityType.VOID;
    }
}
