package algorithms.search;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    protected Queue<AState> m_queueOpen;
    protected AbstractSet<AState> m_queueClosed;

    /**
     * c'tor for the BFS
     */
    public BreadthFirstSearch() {
        this.m_queueOpen = new LinkedList<AState>();
        this.m_queueClosed = new HashSet<AState>();
        this.m_evaluatedStates = 0;
        this.m_name = "Breadth First Search";
    }

    @Override
    public Solution solve(ISearchable domain) {
        if (domain == null)
            return getSolution(null);

        // setting the initial states to be searched
        m_startState = domain.getStartState();
        m_goalState = domain.getGoalState();

        // adding start state to the queue
        m_queueOpen.add(getStartState());

        while (!m_queueOpen.isEmpty()){
            AState cur = m_queueOpen.poll();
             // needs to be changed

            if (cur.equals(m_goalState))
                return getSolution(cur); // method that will retrieve solution from a given state

            ArrayList<AState> childStates = domain.getChildStates(cur);
            for (AState child :
                    childStates) {
                if (!m_queueOpen.contains(child) && !m_queueClosed.contains(child)){
                    m_queueOpen.add(child);
                    m_evaluatedStates++;
                }

            }

            m_queueOpen.remove(cur);
            m_queueClosed.add(cur);
        }

        // if solution was not found
        return getSolution(null);
    }


}
