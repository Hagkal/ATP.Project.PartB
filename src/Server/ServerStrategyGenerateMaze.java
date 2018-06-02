package Server;
import java.io.*;
import IO.*;
import algorithms.mazeGenerators.*;


public class ServerStrategyGenerateMaze implements IServerStrategy {
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            int [] dim = (int[])fromClient.readObject();
            Maze m;
            if (Server.Configurations.getProperty("mazeType").equals("simpleMaze")) {
                m = new SimpleMazeGenerator().generate(dim[0], dim[1]);
            }
            else {
                m = new MyMazeGenerator().generate(dim[0], dim[1]);
            }
            byte[] toSend = m.toByteArray();
            ByteArrayOutputStream arr = new ByteArrayOutputStream();
            MyCompressorOutputStream compress = new MyCompressorOutputStream(arr);
            compress.write(toSend);
            compress.flush();
            compress.close();

            toClient.writeObject(arr.toByteArray());
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
