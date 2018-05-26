package algorithms.search;

import algorithms.mazeGenerators.Position;

public class MazeState extends AState {

    private Position m_current;

    /**
     * Maze State class c'tor
     * @param current - the position of this maze state
     * @param fromState - the state from we have discovered the current state
     * @param goalState - the goal state of the problem
     */
    public MazeState(Position current, AState fromState, AState goalState, double weight) {
        this.m_current = current;
        this.m_parent = fromState;
        this.m_goalState = goalState;
        this.m_visited = false;
        this.m_weight = weight;
    }

    /**
     * override to string method
     * @return
     */
    public String toString(){
        return m_current.toString();
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(Object o) {
        if (o == null)
            throw new NullPointerException("In: compareTo at MazeState. Problem: given instance is null");
        if (!(o instanceof MazeState))
            throw new ClassCastException("In: compareTo at MazeState. Problem: instance is not MazeState");

        MazeState other = (MazeState) o;
        int x1 = m_current.getColumnIndex();
        int y1 = m_current.getRowIndex();
        int x2 = other.m_current.getColumnIndex();
        int y2 = other.m_current.getRowIndex();
        int xGoal = ((MazeState)m_goalState).m_current.getColumnIndex();
        int yGoal = ((MazeState)m_goalState).m_current.getRowIndex();

        double d1 = Math.sqrt(Math.pow(x1-xGoal, 2) + Math.pow(y1 - yGoal, 2));
        double d2 = Math.sqrt(Math.pow(x2-xGoal, 2) + Math.pow(y2 - yGoal, 2));

        double dT = d1-d2;

        if (dT > 0)
            return 1;
        if (dT < 0)
            return -1;

        return 0;

        //return (int) (d1-d2);
    }

    /**
     * overriding the Object equals method
     * @param o - the given object
     * @return - true if equal, false otherwise
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof MazeState))
            return false;

        MazeState other = (MazeState) o;

        return (m_current.getRowIndex() == other.m_current.getRowIndex()) &&
                (m_current.getColumnIndex() == other.m_current.getColumnIndex());
    }


    /**
     * function we have to implement in order to use hash set data structure (this is the hash function)
     * @return
     */
    @Override
    public int hashCode() {
        //return (m_current.getRowIndex()*7 + m_current.getColumnIndex()*m_current.getColumnIndex());
        return (m_current.getColumnIndex() + m_current.getRowIndex()*1000);
    }


    /**
     * returns the position (x,y) of the current state
     * @return
     */
    public Position getCurrentPosition() {
        return m_current;
    }

}



