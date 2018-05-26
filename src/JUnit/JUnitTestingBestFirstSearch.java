import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JUnitTestingBestFirstSearch {

    // the object which we will be testing
    BestFirstSearch test_BestFS = new BestFirstSearch();

    //testing name of the solver
    @Test
    void testName() {
        assertEquals("Best First Search", test_BestFS.getName());
    }

    // testing null domain case
    @Test
    void testNullDomain() {
        ISearchable tmp = null;
        Solution s = test_BestFS.solve(tmp);
        assertEquals(s.getSolutionPath().size(), 0);

    }

    // testing solution
    @Test
    void testSolve() {
        int [][] fixedMatrix = {
                {1,0,1,0,1,1,0,0,1,0},
                {0,0,1,0,0,0,1,0,0,0},
                {1,0,0,0,1,1,0,0,1,0},
                {1,0,1,0,0,0,0,1,1,0},
                {0,0,1,1,0,1,0,1,0,1},
                {1,0,0,0,1,0,0,0,0,0},
                {0,1,1,0,0,0,1,0,1,1},
                {1,0,0,1,1,0,1,1,0,0},
                {0,0,1,0,0,0,0,0,0,1},
                {1,0,0,0,1,0,1,0,1,1}
        };
        Position start = new Position(9,3);
        AState startState = new MazeState(start, null, null, 0);
        Position end = new Position(3, 9);
        AState endState = new MazeState(end, null, null, 0);

        Maze testMaze = new Maze(fixedMatrix, start, end);
        ISearchable testSearchable = new SearchableMaze(testMaze);

        Solution s = test_BestFS.solve(testSearchable);
        assertEquals(s.getSolutionPath().get(s.getSolutionPath().size() - 1), endState );
        assertEquals(s.getSolutionPath().get(0).getParent(), startState);
    }

    // testing no solution case
    @Test
    void noSolution() {
        int [][] fixedMatrix = {
                {1,0,1,0,1,1,0,0,1,0},
                {0,0,1,0,0,0,1,0,1,0},
                {1,0,0,0,1,1,0,0,1,0},
                {1,0,1,0,0,0,0,1,1,0},
                {0,0,1,1,0,1,0,1,0,1},
                {1,0,0,0,1,0,0,0,0,0},
                {0,1,1,0,0,0,1,0,1,1},
                {1,0,0,1,1,0,1,1,0,0},
                {0,0,1,0,0,0,0,0,0,1},
                {1,0,0,0,1,0,1,0,1,1}
        };
        Position start = new Position(9,3);
        AState startState = new MazeState(start, null, null, 0);
        Position end = new Position(3, 9);
        AState endState = new MazeState(end, null, null, 0);

        Maze testMaze = new Maze(fixedMatrix, start, end);
        SearchableMaze testSearchable = new SearchableMaze(testMaze);

        Solution s = test_BestFS.solve(testSearchable);
        assertEquals(s.getSolutionPath().size(), 0);
    }
}