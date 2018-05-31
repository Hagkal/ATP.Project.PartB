package algorithms.mazeGenerators;
import java.util.ArrayList;

public class MyMazeGenerator extends AMazeGenerator {

    /**
     * this method will create a maze using Prim's Algorithm
     * @param rows    - the given amount of rows
     * @param columns - the given amount of columns
     * @return - a proper maze
     */
    public Maze generate(int rows, int columns) {
        // setting default values if the input is incorrect
        if (rows < 1 || columns < 1){
            rows = 10;
            columns = 10;
        }

        // making the matrix and list of positions
        int[][] matrix = new int[rows][columns];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                matrix[i][j] = 1;

        ArrayList<Position> list = new ArrayList<Position>();

        // picking a random cell and initialing the list
        int rRow = (int) (rows * Math.random());
        int rColumn = (int) (columns * Math.random());
        matrix[rRow][rColumn] = 0;
        ArrayList<Position> tmp = getNeighbors(matrix, new Position(rRow, rColumn));
        addWallsToList(matrix, list, tmp);

        // going through all walls
        while (!list.isEmpty()) {
            int rnd = (int) (list.size() * Math.random());
            Position wall = list.remove(rnd);
            //Position wall = list.remove(list.size()- 1);  // NOT random

            if (possiblePosition(matrix, wall)) {
                matrix[wall.getRowIndex()][wall.getColumnIndex()] = 0;
                addWallsToList(matrix, list, getNeighbors(matrix, wall));
            }
        }

        Position[] SnE = getStartingPos(matrix);

        return new Maze(matrix, SnE[0], SnE[1]);

    }


    /**
     * this method will return a list of all positions which are neighbors to a given position
     * @param mat  - the matrix (maze)
     * @param from - the given position
     * @return - the wanted list
     */
    private ArrayList<Position> getNeighbors(int[][] mat, Position from) {
        ArrayList<Position> l = new ArrayList<Position>();
        int tRow = from.getRowIndex();
        int tCol = from.getColumnIndex();

        if (tRow + 1 < mat.length)
            l.add(new Position(tRow + 1, tCol));

        if (tRow - 1 >= 0)
            l.add(new Position(tRow - 1, tCol));

        if (tCol + 1 < mat[0].length)
            l.add(new Position(tRow, tCol + 1));

        if (tCol - 1 >= 0)
            l.add(new Position(tRow, tCol - 1));

        return l;
    }


    /**
     * this method will add the position which is a wall
     * out of a list of possible positions
     * @param target   - list of wall positions
     * @param supplier - list of positions
     */
    private void addWallsToList(int[][] mat, ArrayList<Position> target, ArrayList<Position> supplier) {

        for (Position pos : supplier) {
            int tRow = pos.getRowIndex();
            int tCol = pos.getColumnIndex();
            if (mat[tRow][tCol] == 1)
                target.add(pos);
        }

    }


    /**
     * this method will return weather a given position is a possible path position
     * @param matrix - the given matrix (maze)
     * @param pos    - the given position
     * @return - true if possible to make a path, false otherwise
     */
    private boolean possiblePosition(int[][] matrix, Position pos) {
        ArrayList<Position> l = getNeighbors(matrix, pos);
        int couter = 0;

        for (Position tmp : l) {
            int tRow = tmp.getRowIndex();
            int tCol = tmp.getColumnIndex();
            if (matrix[tRow][tCol] == 0)
                couter++;
        }

        return couter == 1;
    }


    /**
     * this method will receive a matrix and will return a randomly chosen Start and Goal positions
     * @param mat - the given matrix (maze)
     * @return - array of 2 Positions. 0 = start, 1 = goal
     */
    private Position[] getStartingPos(int[][] mat) {
        //returning an array of possible positions
        Position[] tmp = new Position[2];

        //choosing sides and making sure that S and E will not be on the same side
        int sSide = (int) (4 * Math.random());
        int eSide = (int) (4 * Math.random());
        while (eSide == sSide) // making sure that start and end sides are different
            eSide = (int) (4 * Math.random());

        // assigning and making sure that S and E will not be the same position
        tmp[0] = getRandomOpenPosition(mat, sSide);
        Position endPos = getRandomOpenPosition(mat, eSide);
        int i = 0;
        while (endPos.equals(tmp[0])) {
            endPos = getRandomOpenPosition(mat, eSide);
            i++;
            if (i == mat.length){
                eSide = (int) (4 * Math.random());
                i=0;
            }
        }
        tmp[1] = endPos; //getCloserOpenPosition(mat);

        return tmp;
    }


    /**
     * this method will return a random open position on the edges of the matrix
     * @param mat  - the given matrix (maze)
     * @param side - the desired side of position
     * @return - an open position
     */
    private Position getRandomOpenPosition(int[][] mat, int side) {
        int pRow;
        int pCol;

        /* sides are going clockwise starting from the upper bound */
        if (side == 0) {
            pRow = 0;
            pCol = (int) (mat[0].length * Math.random());

            while (mat[pRow][pCol] == 1)
                pCol = (int) (mat[0].length * Math.random());

            return new Position(pRow, pCol);
        } else if (side == 1) {
            pRow = (int) (mat.length * Math.random());
            pCol = mat[0].length - 1;

            while (mat[pRow][pCol] == 1)
                pRow = (int) (mat.length * Math.random());

            return new Position(pRow, pCol);

        } else if (side == 2) {
            pRow = mat.length - 1;
            pCol = (int) (mat[0].length * Math.random());

            while (mat[pRow][pCol] == 1)
                pCol = (int) (mat[0].length * Math.random());

            return new Position(pRow, pCol);
        } else if (side == 3) {
            pRow = (int) (mat.length * Math.random());
            pCol = 0;

            while (mat[pRow][pCol] == 1)
                pRow = (int) (mat.length * Math.random());

            return new Position(pRow, pCol);
        }

        return null;
    }


    /**
     * this method will return a random open position on the matrix
     * @param mat  - the given matrix (maze)
     * @return - an open position
     */
    private Position getCloserOpenPosition(int[][] mat) {
        int pRow;
        int pCol;

        do{
            pRow = (int) (mat.length * Math.random());
            pCol= (int) (mat[0].length * Math.random());

            if (mat[pRow][pCol] == 0)
                return new Position(pRow, pCol);
        }while (mat[pRow][pCol] == 1);

        return null;


    }
}

