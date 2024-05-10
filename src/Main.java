import Model.Database.Db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Create a new Home window
        new View.Home();
        ResultSet rs = Db.result("Select * from exercise");
        rs.absolute(1);
        System.out.println(rs.getString("name"));
    }
}