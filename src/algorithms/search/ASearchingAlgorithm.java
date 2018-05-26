package algorithms.search;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected String m_name;
    protected AState m_startState;
    protected AState m_goalState;
    protected int m_evaluatedStates;

    /**
     * returns the searching algorithm name
     * @return
     */
    @Override
    public String getName() {
        return m_name;
    }


    /**
     * this method returns the start state of a searching problem
     * @return
     */
    public AState getStartState(){ return m_startState;}


    /**
     * this method sets the start state of a searching problem
     * @return
     */
    public void set_startState(AState m_startState) {
        this.m_startState = m_startState;
    }


    /**
     * this method returns the goal state of a searching problem
     * @return
     */
    public AState get_goalState() {
        return m_goalState;
    }


    /**
     * this method sets the goal state of a searching problem
     * @return
     */
    public void set_goalState(AState m_goalState) {
        this.m_goalState = m_goalState;
    }


    /**
     * this method returns the number of evaluated nodes during the run of the searching algorithm (casted to string)
     * @return
     */
    @Override
    public String getNumberOfNodesEvaluated() {
        return String.valueOf(m_evaluatedStates);
    }


    /**
     * this method will return a solution from a given state
     * @param goalFound - the state of the goal
     * @return - a proper solution
     */
    protected Solution getSolution(AState goalFound){
        return new Solution(goalFound);
    }
}
