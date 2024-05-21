import Controller.Engine;
import Controller.dbFoodManager;
import Model.Database.Db;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Engine engine = new Engine();
        // Create a new Home window
        new View.LoginView(engine);
        ResultSet rs = dbFoodManager.getFood();
        for (int i = 1; i <= 50; i++){
            rs.absolute(i);
            System.out.println(rs.getString("name") + "; " + rs.getFloat("calories") + "; " + rs.getFloat("proteins") + "; " + rs.getFloat("carbohydrates") + "; " + rs.getFloat("fats"));
        }
    }
}