package algorithms.search;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    private Maze m;


    /**
     * Searchable Maze class c'tor
     * @param m
     */
    public SearchableMaze(Maze m) {
        this.m = m;
    }


    /**
     * this method returns the start state of a maze searching problem
     * @return
     */
    public AState getStartState(){
        AState goal = new MazeState(m.getGoalPosition(), null, null, 0);
        return new MazeState(new Position(m.getStartPosition().getRowIndex(), m.getStartPosition().getColumnIndex()), null, goal, 0);
    }

    /**
     * this method returns the goal state of a maze searching problem
     * @return
     */
    public AState getGoalState(){
        AState goal = new MazeState(m.getGoalPosition(), null, null, 0);
        return new MazeState(new Position(m.getGoalPosition().getRowIndex(), m.getGoalPosition().getColumnIndex()), null, goal, 0);
    }


    /**
     * this method returns a list of states which are valid to the next step in a maze searching algorithm
     * @return
     */
    public ArrayList<AState> getChildStates(AState state){
        if (state instanceof MazeState) {
            ArrayList<AState> children = new ArrayList<AState>();
            int row = ((MazeState)state).getCurrentPosition().getRowIndex();
            int col = ((MazeState)state).getCurrentPosition().getColumnIndex();

            Position up = new Position(row - 1, col);
            Position down = new Position(row + 1, col);
            Position right = new Position(row, col + 1);
            Position left = new Position(row, col - 1);

            boolean upW = m.isPath(up.getRowIndex(), up.getColumnIndex());
            boolean downW = m.isPath(down.getRowIndex(), down.getColumnIndex());
            boolean rightW = m.isPath(right.getRowIndex(), right.getColumnIndex());
            boolean leftW = m.isPath(left.getRowIndex(), left.getColumnIndex());
            boolean up_rightW = m.isPath(row - 1, col + 1);
            boolean up_leftW = m.isPath(row - 1, col - 1);
            boolean down_rightW = m.isPath(row + 1, col + 1);
            boolean down_leftW = m.isPath(row + 1, col - 1);

            if ((upW || rightW) && up_rightW)//up-right-corner
                children.add(new MazeState(new Position(row - 1, col + 1), state, state.getGoalState(), state.getWeight() + 1.5));
            if ((upW || leftW) && up_leftW)//up-left-corner
                children.add(new MazeState(new Position(row - 1, col - 1), state, state.getGoalState(), state.getWeight() + 1.5));
            if ((downW || rightW) && down_rightW)//up-right-corner
                children.add(new MazeState(new Position(row + 1, col + 1), state, state.getGoalState(), state.getWeight() + 1.5));
            if ((downW || leftW) && down_leftW)//up-left-corner
                children.add(new MazeState(new Position(row + 1, col - 1), state, state.getGoalState(), state.getWeight() + 1.5));

            if (upW)//up
                children.add( new MazeState(up, state, state.getGoalState(), state.getWeight() + 1) );


            if (downW)//down
                children.add(new MazeState(down, state, state.getGoalState(), state.getWeight() + 1));


            if (rightW)//right
                children.add(new MazeState(right, state, state.getGoalState(), state.getWeight() + 1));


            if (leftW)//left
                children.add(new MazeState(left, state, state.getGoalState(), state.getWeight() + 1));

            return children;
        }
        return null;
    }

}
