package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {

    /**
     * this method returns the start state of a searching problem
     * @return
     */
    public AState getStartState();


    /**
     * this method returns the goal state of a searching problem
     * @return
     */
    public AState getGoalState();


    /**
     * this method returns a list of states which are valid to the next step in a searching algorithm
     * @return
     */
    public ArrayList<AState> getChildStates(AState curState);
}
