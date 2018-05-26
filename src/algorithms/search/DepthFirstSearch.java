package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    private Stack<AState> m_stackOpen;
    private AbstractSet<AState> m_listClose;

    /**
     * c'tor
     */
    public DepthFirstSearch(){
        this.m_stackOpen = new Stack<AState>();
        this.m_listClose = new HashSet<AState>();
        this.m_name = "Depth First Search";
        this.m_evaluatedStates = 0;
    }


    /**
     * this method will receive a searchable problem and returns a solution
     *
     * @param domain - searchable problem
     * @return - proper solution
     */
    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return getSolution(null);

        // setting the initial states to be searched
        m_startState = domain.getStartState();
        m_goalState = domain.getGoalState();

        // pushing start position
        m_stackOpen.push(m_startState);

        while (!m_stackOpen.isEmpty()){

            AState cur = m_stackOpen.pop();

            if (cur.equals(m_goalState)) // if goal state was found
                return getSolution(cur);

            // if state was not yet discovered
            if (!m_listClose.contains(cur)){
                m_listClose.add(cur);

                ArrayList<AState> childStates = domain.getChildStates(cur);
                for (AState child:
                     childStates) {
                    if (!m_listClose.contains(child) && !m_stackOpen.contains(child)){ // if child was not yet discovered
                        m_stackOpen.push(child);
                        m_evaluatedStates++;
                    }

                }
            }
        }

        return getSolution(null);
    }
}
