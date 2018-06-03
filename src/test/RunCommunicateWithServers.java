package test;

import IO.MyDecompressorInputStream;
import Server.*;
import Client.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.search.*;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class RunCommunicateWithServers {
    static int count=0;
    static Maze m1, m2, m3;
    static Maze[] mazer = {m1, m2, m3};

    public static void main(String[] args) {

        //Initializing servers
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());

        MyMazeGenerator gen = new MyMazeGenerator();
        m1 = gen.generate(100, 100);
        m2 = gen.generate(90, 90);
        m3 = gen.generate(111,112);

        //Starting  servers
        solveSearchProblemServer.start();
        //mazeGeneratingServer.start();

        //Communicating with servers
        //CommunicateWithServer_MazeGenerating();
        CommunicateWithServer_SolveSearchProblem();

        Thread[] t = new Thread[50];
        for (int i = 0; i<t.length; i++){
            t[i] = new Thread(() -> CommunicateWithServer_SolveSearchProblem());
            t[i].start();
        }

        for (int j = 0; j<t.length; j++) {
            try {
                t[j].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Stopping all servers
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
    }

    private static void CommunicateWithServer_MazeGenerating() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{50, 50};

                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[1000 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        Maze maze = new Maze(decompressedMaze);
                        maze.print();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //OptionalDataException o = (OptionalDataException) e;
                        //System.out.println("len: " + o.length + "\n" + "eof: " + o.eof);

                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        MyMazeGenerator mg = new MyMazeGenerator();
                        ISearchingAlgorithm search = new BestFirstSearch();
                        /*int [][] m = {
                                {0,0,0},
                                {1,0,1},
                                {0,0,0}
                        };
                        Maze maze = new Maze(m, new Position(0,0), new Position(2,2));

                        Maze maze = mg.generate(50, 50);
                        maze.print();*/
                        Maze maze;
                        if (count%3 == 0){
                            maze = m1;
                            count++;
                        }
                        else{
                            if (count%3 == 1){
                                maze = m2;
                                count++;
                            }
                            else{
                                maze = m3;
                                count++;
                            }
                        }
                        toServer.writeObject(maze); //send maze to server
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        Solution comp = search.solve(new SearchableMaze(maze));
                        /*
                        //Print Maze Solution retrieved from the server
                        System.out.println(String.format("Solution steps: %s", mazeSolution));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();
                        for (int i = 0; i < mazeSolutionSteps.size(); i++) {
                            System.out.println(String.format("%s. %s", i, mazeSolutionSteps.get(i).toString()));
                        }
                        */
                        System.out.println(mazeSolution.equals(comp));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static void CommunicateWithServer_StringReverser() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5402, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
                        PrintWriter toServer = new PrintWriter(outToServer);

                        String message = "Client Message";
                        String serverResponse;
                        toServer.write(message + "\n");
                        toServer.flush();
                        serverResponse = fromServer.readLine();
                        System.out.println(String.format("Server response: %s", serverResponse));
                        toServer.flush();
                        fromServer.close();
                        toServer.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}