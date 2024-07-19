import Controller.Engine;

import java.awt.*;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        Engine engine = Engine.getInstance();

        new View.LoginView(engine);
        ResultSet rs = engine.getAll_food();
        for (int i = 1; i <= 50; i++){
            rs.absolute(i);
            System.out.println(rs.getString("name") + "; " + rs.getFloat("calories") + "; " + rs.getFloat("proteins") + "; " + rs.getFloat("carbohydrates") + "; " + rs.getFloat("fats"));
        }

    }
}
