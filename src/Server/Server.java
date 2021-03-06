package Server;

import java.io.*;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class Server {
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    //private static final Logger LOG = LogManager.getLogger(); //Log4j2
    private ThreadPoolExecutor threadPoolExecutor;


    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;

        //getting number of threads to run
        String numOfThreads = Server.Configurations.getProperty("numOfThreads");
        int threads = 8; //Integer.parseInt(numOfThreads);  // DOES NOT WORK WITH SUBMISSION SYSTEM
        threadPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        if (threads != 0)
            threadPoolExecutor.setCorePoolSize(threads);
    }

    public void start() {

        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            //LOG.info(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    //LOG.info(String.format("Client excepted: %s", clientSocket.toString()));
                    threadPoolExecutor.execute(new Thread(() -> handleClient(clientSocket)));
                } catch (SocketTimeoutException e) {
                    //LOG.debug("SocketTimeout - No clients pending!");
                }
            }

            threadPoolExecutor.shutdown();
            server.close();

        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            //LOG.debug("Client excepted!");
            //LOG.debug(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            clientSocket.getInputStream().close();
            clientSocket.getOutputStream().close();
            clientSocket.close();
        } catch (IOException e) {
            //LOG.error("IOException", e);
        }
    }

    public void stop() {
        //LOG.info("Stopping server..");
        stop = true;
    }

    public static class Configurations {
        private static String workingDirectory = System.getProperty("user.dir");
        private static String propertyFileDirectory = "\\Resources\\";
        private static InputStream inFromFile;
        private static OutputStream outToFile;
        private static Properties property = new Properties();
        private static final Object o = new Object();

        public static void setProperty(String key, String value) {
            try {
                outToFile = new FileOutputStream(workingDirectory + propertyFileDirectory + "config.properties");
                outToFile.flush();
                property.setProperty(key, value);
                property.store(outToFile, "");
                outToFile.flush();
                outToFile.close();
            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public static String getProperty(String key) {
            String toReturn = "";
            try {
                synchronized (o) {
                    inFromFile = new FileInputStream(workingDirectory + propertyFileDirectory + "config.properties");
                    property.load(inFromFile);
                    toReturn = property.getProperty(key, "");
                    inFromFile.close();
                }

            } catch (FileNotFoundException e) {
            } catch (IOException e) {
                e.printStackTrace();
            }
            return toReturn;
        }

    }
}
