import java.io.IOException;
import java.lang.reflect.Array;

public class Client {
    private Socket socket;
    private String hostname;
    private int port;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
        try {
            socket = new Socket(hostname, port);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData () {
        try {
            socket.write(0x02);
            socket.write("REQ>");
            socket.write(0x03);
            for (int i = 0; i < 10; i++) {
                if (socket.dataAvailable() > 0) {
                    String data = "";
                    while(true) {
                        int b = socket.read();
                        if(b == 0x02) {
                            data = "";
                            continue;
                        } else if(b == 0x03) {
                            break;
                        }
                        data += (char) b;
                    }
                    String[] split = data.split(">");
                    String command = split[0];
                    String param = split[1];

                    if (command.equals("DAT")) {
                        String[] elements = param.split("\\|");
                        for(String element : elements) {
                            String[] items = element.split(";");
                            if (items.length == 3) {
                                System.out.println(items[0] + " temperature:" + items[1] + " humidity:" + items[2]);
                            }
                        }
                    }
                    return;
                } else {
                    Thread.sleep(100);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void quit() {
        try {
            socket.write(0x02);
            socket.write("QUT>");
            socket.write(0x03);
            socket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
