package algorithms.search;

import java.io.Serializable;

public abstract class AState implements Comparable, Serializable {
    protected AState m_parent;
    protected AState m_goalState;
    protected boolean m_visited;
    protected double m_weight;


    public double getWeight() {
        return m_weight;
    }

    public void setWeight(double m_weight) {
        this.m_weight = m_weight;
    }

    /**
     * override to string method
     * @return
     */
    public abstract String toString();

    /**
     * this method returns the state from we have discovered the current state
     * @return
     */
    public AState getParent() {
        return m_parent;
    }


    /**
     * this method returns the goal state of the problem
     * @return
     */
    public AState getGoalState(){return m_goalState;}


    /**
     * override the equals method
     * @param o
     * @return
     */
    public abstract boolean equals(Object o);


    /**
     * function we have to implement in order to use hash set data structure (this is the hash function)
     * @return
     */
    @Override
    public abstract int hashCode();


    /**
     * will set if this state was already visited
     * @param flag - true or false
     */
    public void setVisited(Boolean flag){m_visited = flag;}


    /**
     * will return weather the state was visited already or not
     * @return - true or false
     */
    public boolean isVisited(){return m_visited;}
}


