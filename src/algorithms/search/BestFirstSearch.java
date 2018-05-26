package algorithms.search;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class BestFirstSearch  extends BreadthFirstSearch {

    public BestFirstSearch() {
        this.m_queueOpen = new PriorityQueue<AState>(idComparator);
        this.m_queueClosed = new HashSet<AState>();
        this.m_evaluatedStates = 0;
        this.m_name = "Best First Search";

    }

    //Comparator anonymous class implementation
    private static Comparator<AState> idComparator = new Comparator<AState>() {
        public int compare(AState c1, AState c2) {
            if (c1.getWeight() - c2.getWeight() > 0)
                return 1;
            else if (c1.getWeight() - c2.getWeight() < 0)
                return -1;
            else
                return 0;
        }
    };
}
