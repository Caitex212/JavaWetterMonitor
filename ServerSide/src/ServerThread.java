import java.io.IOException;
import java.sql.ResultSet;

public class ServerThread extends Thread {
    private Socket clientSocket;
    private Server s;
    private Database db;

    public ServerThread(Socket clientSocket, Server s, Database db) {
        this.clientSocket = clientSocket;
        this.s = s;
        this.db = db;
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (clientSocket.dataAvailable() > 0) {
                    String data = "";
                    while(true) {
                        int b = clientSocket.read();
                        if(b == 0x02) {
                            data = "";
                        } else if(b == 0x03) {
                            break;
                        }
                        data += (char) b;
                    }
                    String[] split = data.split(">");
                    String command = split[0];
                    String param = split[1];
                    System.out.println("Received Request from Client:" + command + " " + param);
                    if (command.equals("REQ")) {
                        ResultSet rs = db.readData();
                        if (rs != null) {
                            String response = "";
                            while (rs.next()) {
                                response += rs.getTimestamp(1).toString() + ";" + rs.getFloat(2) + ";" + rs.getFloat(3) + "|";
                            }
                            clientSocket.write(0x02);
                            clientSocket.write("DAT>");
                            clientSocket.write(response);
                            clientSocket.write(0x03);
                        }
                    } else if (command.equals("QUT>")) {
                        clientSocket.close();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
