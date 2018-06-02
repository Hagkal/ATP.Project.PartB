package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.BestFirstSearch;
import algorithms.search.ISearchingAlgorithm;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            // getting the maze from the client and solving him
            Maze toSolve = (Maze) fromClient.readObject();
            ISearchingAlgorithm searcher = new BestFirstSearch();

            /*
            here should be the part where we search for the maze in a file and seek the solution
             */

            // if no such maze was found
            Solution sol = searcher.solve(new SearchableMaze(toSolve));

            /*
            here should be the part that we make a new file for the maze and keep the solution
             */

            toClient.writeObject(sol);
            toClient.flush();
            toClient.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }



    }
}
