package algorithms.mazeGenerators;

public class SimpleMazeGenerator extends AMazeGenerator {

    /**
     * this method will generate the most simple maze possible
     * @param rows - the given amount of rows
     * @param columns - the given amount of columns
     * @return - simple maze
     */
    public Maze generate(int rows, int columns){
        // setting default values if the input is incorrect
        if (rows < 1 || columns < 1){
            rows = 10;
            columns = 10;
        }


        int [][] maze = new int [rows][columns]; // making a matrix

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                maze[i][j] = 1;

        int rand = (int)(rows*Math.random());

        for (int i = 0; i < columns; i++)
            maze[rand][i] = 0;


        Position start = new Position(rand, 0);
        Position end = new Position(rand, columns-1);

        return new Maze(maze, start, end);
    }
}
