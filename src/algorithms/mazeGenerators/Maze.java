package algorithms.mazeGenerators;

public class Maze {
    private int [][] maze;
    private Position m_startPosition;
    private Position m_goalPosition;

    /**
     * Maze class c'tor
     * @param maze - a 2d array
     * @param start - start position (x,y)
     * @param goal - destination position (x,y)
     */
    public Maze(int[][] maze, Position start, Position goal) {
        this.maze = maze;
        this.m_startPosition = start;
        this.m_goalPosition = goal;
    }


    /**
     * this method will return the start position of this maze
     * @return
     */
    public Position getStartPosition(){
        return m_startPosition;
    }


    /**
     * this method will return the goal position of this maze
     * @return
     */
    public Position getGoalPosition(){
        return m_goalPosition;
    }


    /**
     * this method returns weather the index given is in the maze
     * @param row
     * @param col
     * @return
     */
    private boolean isInBound(int row, int col){
        return (row < maze.length && col < maze[0].length && row >= 0 && col >= 0);
    }


    /**
     * this method returns if the given index is a wall or not
     * @param row - the given row index
     * @param col - the given column index
     * @return - true or false
     */
    public boolean isPath(int row, int col){
        if (isInBound(row, col))
            return (maze[row][col] == 0);
        return false;
    }


    @Override
    public String toString() {
        return "";
    }

    /**
     * this method will print the maze as is
     */
    public void print(){
        //String s = "";

        for (int i = 0; i<maze.length; i++){
            for (int j = 0; j<maze[0].length; j++){
                if (j == maze[0].length-1){
                    if (i == m_startPosition.getRowIndex() && j==m_startPosition.getColumnIndex())
                        //s += "S";
                        System.out.print("S");
                    else if (i == m_goalPosition.getRowIndex() && j==m_goalPosition.getColumnIndex())
                        //s += "E";
                        System.out.print("E");
                    else if (maze[i][j] == 0)
                        //s += maze[i][j];
                        System.out.print(maze[i][j]);
                    else
                        System.out.print(1);
                }

                else{
                    if (i == m_startPosition.getRowIndex() && j==m_startPosition.getColumnIndex())
                        //s += "S ";
                        System.out.print("S ");
                    else if (i == m_goalPosition.getRowIndex() && j==m_goalPosition.getColumnIndex())
                        //s += "E ";
                        System.out.print("E ");
                    else if (maze[i][j] == 0)
                        //s = s + maze[i][j] + " ";
                        System.out.print(maze[i][j] + " ");
                    else
                        System.out.print('1' + " ");

                }
            }
            //s += "\n";
            System.out.println();
        }

        //return s;
    }


}
