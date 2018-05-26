package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    /**
     * an abstract method that will generate a maze. implemented differently in a concrete class
     * @param rows - the given amount of rows
     * @param columns - the given amount of columns
     * @return - the maze that was generated
     */
    public abstract Maze generate(int rows, int columns);


    /**
     * this method will return the time to create a maze of a given size
     * @param rows - how many rows in the maze
     * @param columns - how many columns in the maze
     * @return - time needed to create the maze in milli seconds
     */
    public long measureAlgorithmTimeMillis(int rows, int columns){
        // setting default values if the input is incorrect
        if (rows < 1 || columns < 1){
            rows = 10;
            columns = 10;
        }

        long currentTime = System.currentTimeMillis();
        generate(rows, columns);
        long afterGenerateTime = System.currentTimeMillis();
        return afterGenerateTime - currentTime;
    }
}
