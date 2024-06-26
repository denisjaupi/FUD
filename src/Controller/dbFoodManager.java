package Controller;

import Model.Database.Db;
import Model.Entities.Food;
import Model.Entities.NutritionalInfo;

import java.sql.*;

public class dbFoodManager {


    public static ResultSet getFood() {
        return Db.result("SELECT name,calories, proteins, carbohydrates, fats FROM foods F left join nutritionalinfo N on( F.macro=N.id_macro) ORDER BY name ASC");
    }

    public static ResultSet getFood_withId(int id) {
        return Db.result("SELECT name,calories, proteins, carbohydrates, fats FROM foods F left join nutritionalinfo N on( F.macro=N.id_macro) where id_food = " + id);
    }

    public static ResultSet getFood_withName(String name) {
        return Db.result("SELECT name,calories, proteins, carbohydrates, fats FROM foods F left join nutritionalinfo N on( F.macro=N.id_macro) where name = '" + name + "'");
    }

    public static void addFood_Db(Food food) {
        int generatedKey = dbNutritionalInfoManager.addNutritionalInfo(food.getNutritionalInfo());
        if (generatedKey != 0)
            Db.result("INSERT INTO foods (name, macro) VALUES ('" + food.getName() + "', " + generatedKey + ")");

    }

    public static int selectId(String name) {
        try {
            String query = "select id_food from foods where name='" + name + "'";
            ResultSet rs = Db.result(query);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la selezione dell'id del cibo");
            e.printStackTrace();
        }
        return 0;
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


    public static  Food selectFood(ResultSet rs) throws SQLException {
        String name_food = rs.getString("name");
        String query1 = "select * from nutritionalinfo where id_macro=" + rs.getInt("macro");
        ResultSet rs3 = Db.result(query1);
        NutritionalInfo macro= new NutritionalInfo(rs3.getDouble("calories"), rs3.getDouble("proteins"), rs3.getDouble("fats"), rs3.getDouble("carbohydrates"));
        ;
        Food f= new Food(name_food, macro);
        f.setId(rs.getInt("id_food"));
        f.setQuantity(rs.getInt("quantity"));
        return f;
    }


}
