package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;
import sun.awt.Mutex;
import java.io.*;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class ServerStrategySolveSearchProblem implements IServerStrategy {

    private ConcurrentHashMap<Integer, Mutex> mutexMap;
    private Mutex m = new Mutex();

    public ServerStrategySolveSearchProblem() {
        mutexMap = new ConcurrentHashMap<>();
    }

  
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();

            // getting the maze from the client and solving him
            Maze toSolve = (Maze) fromClient.readObject();
            ISearchingAlgorithm searcher;
            String searchingAlgorithm = Server.Configurations.getProperty("searchingAlgorithm");
            switch (searchingAlgorithm){
                case "BreadthFirstSearch":
                    searcher = new BreadthFirstSearch();
                    break;
                case "BestFirstSearch":
                    searcher = new BestFirstSearch();
                    break;
                case "DepthFirstSearch":
                    searcher = new DepthFirstSearch();
                    break;
                default:
                    searcher = new BestFirstSearch();
            }


            byte[] byteStyle = toSolve.toByteArray();
            int hash = Arrays.hashCode(byteStyle);
            File properMaze = new File("" + hash);

            // Taking care of potential races
            m.lock();
            boolean inMap = mutexMap.containsKey(hash);
            if (inMap){
                mutexMap.get(hash).lock();
                m.unlock();
            }
            else{
                Mutex tmp = new Mutex();
                mutexMap.put(hash, tmp);
                m.unlock();
                tmp.lock();
            }

            // checking if such maze already have been solved
            boolean alreadySolved = properMaze.exists();
            Solution sol;

            // if no such maze was found
            if (alreadySolved){
                ObjectInputStream solGetter = new ObjectInputStream(new FileInputStream(properMaze));
                sol = (Solution) solGetter.readObject();
                solGetter.close();
            }
            else {
                sol = searcher.solve(new SearchableMaze(toSolve));
                ObjectOutputStream solWriter = new ObjectOutputStream(new FileOutputStream(properMaze));
                solWriter.flush();
                solWriter.writeObject(sol);
                solWriter.flush();
                solWriter.close();
            }
            mutexMap.get(hash).unlock();

            toClient.writeObject(sol);
            toClient.flush();
            toClient.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
