package Server;
import java.io.*;
import IO.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;


public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            int [] dim = (int[])fromClient.readObject();
            Maze m = new MyMazeGenerator().generate(dim[0], dim[1]);
            byte[] toSend = m.toByteArray();
            MyCompressorOutputStream compress = new MyCompressorOutputStream(toClient);
            compress.write(toSend);
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
