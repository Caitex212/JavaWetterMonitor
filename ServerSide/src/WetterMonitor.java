public class WetterMonitor extends Thread {
    private static final int BAUD_RATE = 9600;
    private Serial serial;
    public Database db;

    public WetterMonitor(String port) {
        db = new Database();
        db.DatabaseConnect();
        db.readData();
        serial = new Serial(port, BAUD_RATE, 8, 1, 0);
        serial.open();
    }

    @Override
    public void run() {
        while (true) {
            if (serial.dataAvailable() > 0) {
                String data = serial.readLine();

                if(data.startsWith("h")) {
                    String[] data_a = data.split(";");
                    float humidity = Float.parseFloat(data_a[0].substring(1));
                    float temperature = Float.parseFloat(data_a[1].substring(1));
                    float index = Float.parseFloat(data_a[2].substring(1));

                    System.out.println("Humidity: " + humidity);
                    System.out.println("Temperature: " + temperature);
                    System.out.println("Index: " + index);
                    db.insertData(temperature, humidity);
                }
            }
        }
    }
}