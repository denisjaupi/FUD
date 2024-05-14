package Controller;

import Model.Database.Db;
import java.sql.*;

public class dbFoodManager {

    public static ResultSet getFood() {
        return Db.result("SELECT name,calories, proteins, carbohydrates, fats FROM foods F left join nutritionalinfo N on( F.macro=N.id_macro)");
    }

    public static ResultSet getFood_withId(int id) {
        return Db.result("SELECT name,calories, proteins, carbohydrates, fats FROM foods F left join nutritionalinfo N on( F.macro=N.id_macro) where id_food = " + id);
    }

    public static ResultSet getFood_withName(String name) {
        return Db.result("SELECT name,calories, proteins, carbohydrates, fats FROM foods F left join nutritionalinfo N on( F.macro=N.id_macro) where name = '" + name + "'");
    }

    public static void add_food_Db(String name, float calories, float proteins, float carbohydrates, float fats){
        try {
            // Get the database connection
            Connection conn = Db.getConnection();

            // Create a PreparedStatement for the INSERT statement
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO nutritionalinfo (calories, proteins, carbohydrates, fats) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            // Set the parameters
            pstmt.setFloat(1, calories);
            pstmt.setFloat(2, proteins);
            pstmt.setFloat(3, carbohydrates);
            pstmt.setFloat(4, fats);

            // Execute the INSERT statement
            pstmt.executeUpdate();

            // Get the generated keys
            ResultSet rs = pstmt.getGeneratedKeys();

            // Use the generated key for the next INSERT statement
            if (rs.next()) {
                int generatedKey = rs.getInt(1);
                Db.result("INSERT INTO foods (name, macro) VALUES ('" + name + "', " + generatedKey + ")");
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento del cibo nel database");
            e.printStackTrace();
        }
    }

    public static void remove_food_withId(int id) {
        try {
            // Get the database connection
            Connection conn = Db.getConnection();

            // Create a PreparedStatement for the DELETE statement
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM foods WHERE id_food = ?"
            );

            // Set the parameters
            pstmt.setInt(1, id);

            // Execute the DELETE statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'eliminazione del cibo dal database");
            e.printStackTrace();
        }
    }
    public static void remove_food_withName(String name) {
        try {
            // Get the database connection
            Connection conn = Db.getConnection();

            // Create a PreparedStatement for the DELETE statement
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM foods WHERE name= ?"
            );
            // Set the parameters
            pstmt.setString(1, name);

            // Execute the DELETE statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'eliminazione del cibo dal database");
            e.printStackTrace();
        }
    }

}
