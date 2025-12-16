public class Main {
    public static void main(String[] args) {
        WetterMonitor wm = new WetterMonitor();
        wm.start();
        try {
            Server server = new Server(6212, wm.db);
            server.start();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
