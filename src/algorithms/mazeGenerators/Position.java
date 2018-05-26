package algorithms.mazeGenerators;

public class Position {
    private int row;
    private int column;


    /**
     * Position class c'tor. a position is composed from row number and column number
     * @param row
     * @param column
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }


    /**
     * getter for row index
     * @return
     */
    public int getRowIndex() {
        return row;
    }


    /**
     * getter for column index
     * @return
     */
    public int getColumnIndex() {
        return column;
    }


    /**
     * override to string method
     * @return
     */
    public String toString(){
        return ("{" + getRowIndex() + "," + getColumnIndex() + "}");
    }


    /**
     * override equals method
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Position)
            return (row == ((Position)obj).getRowIndex()) && (column == ((Position)obj).getColumnIndex());

        return false;
    }
}
