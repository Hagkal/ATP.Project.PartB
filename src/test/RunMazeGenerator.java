
package test;
import algorithms.mazeGenerators.*;

public class RunMazeGenerator {
        public static void main(String[] args) {
            //testMazeGenerator(new SimpleMazeGenerator());
            //testMazeGenerator(new MyMazeGenerator());

            //testMazeToByte();

            int a = 130;
            System.out.println((byte) a + "\n" + (230-256));

        }


        private static void testMazeToByte(){
            MyMazeGenerator gen = new MyMazeGenerator();
            Maze m = gen.generate(10, 10);
            m.print();

            System.out.println();

            Maze m1 = new Maze(m.toByteArray());
            m1.print();

        }




        private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
// prints the time it takes the algorithm to run
            System.out.println(String.format("Maze generation time(ms): %s",
                    mazeGenerator.measureAlgorithmTimeMillis(1000/*rows*/,1000/*columns*/)));
// generate another maze
            Maze maze = mazeGenerator.generate(50/*rows*/, 50/*columns*/);
// prints the maze
            maze.print();
// get the maze entrance
            Position startPosition = maze.getStartPosition();
// print the position
            System.out.println(String.format("Start Position: %s",
                    startPosition)); // format "{row,column}"
// prints the maze exit position
            System.out.println(String.format("Goal Position: %s",
                    maze.getGoalPosition()));
        }
    }
