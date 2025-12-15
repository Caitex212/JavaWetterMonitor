import java.io.IOException;

public class Server extends Thread {
    private int port;
    private ServerSocket serverSocket;
    private Database db;

    public Server(int port, Database db) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    public void run() {
        try {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                ServerThread serverThread = new ServerThread(clientSocket, this, db);
                serverThread.start();
                System.out.println("Client connected");
            }
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
