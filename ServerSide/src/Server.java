import java.io.IOException;

public class Server extends Thread {
    private int port;
    private ServerSocket serverSocket;
    private Database db;

    public Server(int port, Database db) {
        this.port = port;
        this.db = db;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
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
