package algorithms.mazeGenerators;

import java.util.ArrayList;

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
     * c'tor from byte array, with metadata fields:
     * @param dataArray - the byte array given
     */
    public Maze(byte[] dataArray){
        int startRow = getData(0, dataArray);
        int startCol = getData(1, dataArray);
        int goalRow = getData(2, dataArray);
        int goalCol = getData(3, dataArray);
        int mazeRow = getData(4, dataArray);
        int mazeCol = getData(5, dataArray);

        this.m_startPosition = new Position(startRow, startCol);
        this.m_goalPosition = new Position(goalRow, goalCol);
        this.maze = new int[mazeRow][mazeCol];

        int arrIdx = getPointer(6, dataArray);
        for (int i = 0; arrIdx < dataArray.length && i<mazeRow; i++){
            for (int j=0; j < mazeCol; j++, arrIdx++)
                maze[i][j] = dataArray[arrIdx];
        }

    }

    /**
     * returns a pointer to the proper metadata index
     * @param amount - the metadata field desired
     * @param data - the byte array
     * @return - index of the desired metadata start point
     */
    private int getPointer(int amount, byte[] data){
        int dataPointer = 0;
        while (amount > 0){
            if (data[dataPointer] == -1)
                amount--;
            dataPointer++;
        }
        return dataPointer;
    }


    /**
     * this method will get the Integer representation of the proper coded byte metadata
     * as requested by the amount field
     * @param amount - the metadata desired to decode
     * @param data - the byte array
     * @return - int representation of the metadata
     */
    private int getData(int amount, byte[] data){
        int toReturn = 0;
        int dataPointer = getPointer(amount, data);
        int numOfDigits = 0;

        //number of digits in the number
        for (int l = dataPointer; data[l] != -1; l++)
            numOfDigits++;

        while (data[dataPointer] != -1){
            toReturn += data[dataPointer]*Math.pow(10, numOfDigits-1);
            numOfDigits--;
            dataPointer++;
        }

        return toReturn;
    }

    private ArrayList<String> splitNumbers(int number){
        ArrayList<String> toReturn = new ArrayList<String>();
        String temp = number + "";
        while (!temp.isEmpty()) {
            toReturn.add(temp.charAt(0) + "");
            temp = temp.substring(1,temp.length());
        }
        toReturn.add("-1");
        return toReturn;
    }

    private ArrayList <String> arrToByte(){
        ArrayList<String> toReturn = new ArrayList<>();
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[0].length; j++) {
                toReturn.add(maze[i][j] + "");
            }
        return toReturn;
    }

    public byte[] toByteArray(){
        int rowStart = m_startPosition.getRowIndex();
        int colStart = m_startPosition.getColumnIndex();
        int rowGoal = m_goalPosition.getRowIndex();
        int colGoal = m_goalPosition.getColumnIndex();
        ArrayList<String> num = new ArrayList<String>();
        num.addAll(splitNumbers(rowStart));
        num.addAll(splitNumbers(colStart));
        num.addAll(splitNumbers(rowGoal));
        num.addAll(splitNumbers(colGoal));
        num.addAll(splitNumbers(maze.length));
        num.addAll(splitNumbers(maze[0].length));
        byte[] toReturn = new byte[num.size() + maze.length * maze[0].length];
        //insert metadata
        for (int i = 0; i < num.size(); i++) {
            int temp = Integer.parseInt(num.get(i));
            toReturn[i] = (byte)temp;
        }
        //insert array (maze)
        int numSize = num.size();
        num.addAll(arrToByte());
        for (int j = numSize; j < num.size(); j++) {
            int temp = Integer.parseInt(num.get(j));
            toReturn[j] = (byte) temp;
        }
        return toReturn;
    }

    /**
     * c'tor from byte array, with metadata fields:
     * @param dataArray - the byte array given
     */
    public Maze(byte[] dataArray){
        int startRow = getData(0, dataArray);
        int startCol = getData(1, dataArray);
        int goalRow = getData(2, dataArray);
        int goalCol = getData(3, dataArray);
        int mazeRow = getData(4, dataArray);
        int mazeCol = getData(5, dataArray);

        this.m_startPosition = new Position(startRow, startCol);
        this.m_goalPosition = new Position(goalRow, goalCol);
        this.maze = new int[mazeRow][mazeCol];

        int arrIdx = getPointer(6, dataArray);
        for (int i = 0; arrIdx < dataArray.length && i<mazeRow; i++){
            for (int j=0; j < mazeCol; j++, arrIdx++)
                maze[i][j] = dataArray[arrIdx];
        }

    }

    /**
     * returns a pointer to the proper metadata index
     * @param amount - the metadata field desired
     * @param data - the byte array
     * @return - index of the desired metadata start point
     */
    private int getPointer(int amount, byte[] data){
        int dataPointer = 0;
        while (amount > 0){
            if (data[dataPointer] == -1)
                amount--;
            dataPointer++;
        }
        return dataPointer;
    }

    /**
     * this method will get the Integer representation of the proper coded byte metadata
     * as requested by the amount field
     * @param amount - the metadata desired to decode
     * @param data - the byte array
     * @return - int representation of the metadata
     */
    private int getData(int amount, byte[] data){
        int toReturn = 0;
        int dataPointer = getPointer(amount, data);
        int numOfDigits = 0;

        //number of digits in the number
        for (int l = dataPointer; data[l] != -1; l++)
            numOfDigits++;

        while (data[dataPointer] != -1){
            toReturn += data[dataPointer]*Math.pow(10, numOfDigits-1);
            numOfDigits--;
            dataPointer++;
        }

        return toReturn;
    }


    private ArrayList<String> splitNumbers(int number, String index){
        ArrayList<String> toReturn = new ArrayList<String>();
        String temp = number + "";
        while (!temp.isEmpty()) {
            toReturn.add("" + temp.charAt(0));
            temp = temp.substring(1,temp.length());
        }
        return toReturn;
    }

    private ArrayList <String> arrToByte(){
        ArrayList<String> toReturn = new ArrayList<>();
        for (int i = 0; i < maze.length; i++)
            for (int j = 0; j < maze[0].length; j++)
                toReturn.add(maze[i][j]+"");
        return toReturn;
    }

    public byte[] toByteArray(){
        int rowStart = m_startPosition.getRowIndex();
        int colStart = m_startPosition.getColumnIndex();
        int rowGoal = m_goalPosition.getRowIndex();
        int colGoal = m_goalPosition.getColumnIndex();
        ArrayList<String> num = new ArrayList<String>();
        num.addAll(splitNumbers(rowStart, "1"));
        num.add("-1");
        num.addAll(splitNumbers(colStart, "2"));
        num.add("-1");
        num.addAll(splitNumbers(rowGoal, "3"));
        num.add("-1");
        num.addAll(splitNumbers(colGoal, "4"));
        num.add("-1");
        num.addAll(splitNumbers(maze.length, "5"));
        num.add("-1");
        num.addAll(splitNumbers(maze[0].length, "6"));
        num.add("-1");
        byte[] toReturn = new byte[num.size() + maze.length * maze[0].length];
        for (int i = 0; i < num.size(); i++) {
            int temp = Integer.parseInt(num.get(i));
            toReturn[i] = (byte)temp;
        }
        int numSize = num.size();
        num.addAll(arrToByte());
        for (int j = numSize; j < toReturn.length; j++) {
            int temp = Integer.parseInt(num.get(j));
            toReturn[j] = (byte) temp;
        }
        return toReturn;
    }

    /*
    public byte[] toByteArray(){
        int rowCount = (maze.length%254 == 0) ? maze.length/254 : (maze.length/254 + 1);
        int colCount = (maze[0].length%254 == 0) ? maze[0].length/254 : (maze[0].length/254 + 1);

        int goalRowCount = (m_goalPosition.getRowIndex()%254 == 0) ? m_goalPosition.getRowIndex()/254 : (m_goalPosition.getRowIndex()/254 + 1);
        int goalColCount = (m_goalPosition.getColumnIndex()%254 == 0) ? m_goalPosition.getColumnIndex()/254 : (m_goalPosition.getColumnIndex()/254 + 1);
        int startRowCount = (m_startPosition.getRowIndex()%254 == 0) ? m_startPosition.getRowIndex()/254 : (m_startPosition.getRowIndex()/254 + 1);
        int startColCount = (m_startPosition.getColumnIndex()%254 == 0) ? m_startPosition.getColumnIndex()/254 : (m_startPosition.getColumnIndex()/254 + 1);

        int size = 6 +
        byte [] sendMe = new byte []
    }
    */

    /**
     * this method will return the start position of this maze
     * @return - Position of start
     */
    public Position getStartPosition(){
        return m_startPosition;
    }


    /**
     * this method will return the goal position of this maze
     * @return Position of goal
     */
    public Position getGoalPosition(){
        return m_goalPosition;
    }


    /**
     * this method returns weather the index given is in the maze
     * @param row - a given row index
     * @param col - a given column index
     * @return - true if in bound, false otherwise
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
