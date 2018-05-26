package algorithms.search;

public interface ISearchingAlgorithm {

    /**
     * this method will receive a searchable problem and returns a solution
     * @param domain - searchable problem
     * @return - proper solution
     */
    public Solution solve(ISearchable domain);


    /**
     * returns the name of the searching algorithm
     * @return - the name of the searching algorithm
     */
    public String getName();


    /**
     * returns the amount of evaluated nodes that the algorithm has developed
     * @return - as said
     */
    public String getNumberOfNodesEvaluated();
}
