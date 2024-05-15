package Controller;

import Model.Database.Db;
import Model.Entities.Food;

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

    public static void addFood_Db(Food food){
        int generatedKey = dbNutritionalInfoManager.addNutritionalInfo(food.getNutritionalInfo());
        if(generatedKey!=0)
            Db.result("INSERT INTO foods (name, macro) VALUES ('" + food.getName() + "', " + generatedKey + ")");
        }

    public static void remove_food_withId(int id) {
        int macro=0;
        try {
            String query="select macro from foods where id_food="+id;
            ResultSet rs=Db.result(query);
            if(rs.next()){
               macro=rs.getInt(1);
            }
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
            if(macro!=0)
                dbNutritionalInfoManager.deleteNutritionalInfo(macro);
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
