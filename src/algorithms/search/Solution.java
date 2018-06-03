package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Solution implements Serializable {

    private ArrayList<AState> m_solutionPath;

    /**
     * Solution class c'tor
     * @param goalState - path is found from the goal state
     */
    public Solution(AState goalState){
        // init the array list
        this.m_solutionPath = new ArrayList<>();

        // setting the array list with the proper states
        while (goalState != null && goalState.getParent() != null){
            m_solutionPath.add(0, goalState);
            goalState = goalState.getParent();
        }
    }

    /**
     * solution path getter
     * @return - the path of the solution (List of AStates)
     */
    public ArrayList<AState> getSolutionPath(){return m_solutionPath;}


    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Solution))
            return false;

        Solution s = (Solution) obj;
        return m_solutionPath.containsAll(s.m_solutionPath)
                && m_solutionPath.size() == s.m_solutionPath.size();

    }
}
