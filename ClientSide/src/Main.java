public class Main {
    public static void main(String[] args) {
        Client c = new Client("localhost", 6212);
        c.readData();
        c.quit();
    }
}