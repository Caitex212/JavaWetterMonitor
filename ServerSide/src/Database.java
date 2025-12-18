import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/WetterMonitor";
    private static final String USER = "wettermonitor";
    private static final String PASSWORD = "12345678";

    Statement statement;

    public void DatabaseConnect(){
        try {
            Connection conn= DriverManager.getConnection(URL, USER, PASSWORD);
            statement = conn.createStatement();
        } catch (Exception e) {
            System.out.print(e.toString());
        }
    }

    public void insertData(float temperature, float humidity){
        try{
            String insertquery = "INSERT INTO Messungen VALUES (default," +  temperature + "," + humidity + ");";
            statement.executeUpdate(insertquery);
            System.out.print("Inserted");
        } catch(Exception e){
            System.out.print("Not Inserted");
        }
    }

    public ResultSet readData() {
        try {
            String selectquery = "SELECT * FROM Messungen";
            ResultSet rs = statement.executeQuery(selectquery);
            while (rs.next()) {
                System.out.println(rs.getTimestamp(1).toString() + " " + rs.getFloat(2) + " " + rs.getFloat(3));
            }
            return rs;
        } catch (Exception e) {
            System.out.println("Not Read");
            System.out.println(e.toString());
        }
        return null;
    }
}
