package Controller;

import Model.Database.Db;
import Model.Entities.NutritionalInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dbNutritionalInfoManager {

    public static int addNutritionalInfo(NutritionalInfo info) {
        int generatedKey = 0;
        try {
            String query = "INSERT INTO nutritionalinfo (calories, proteins, carbohydrates, fats) VALUES (" + info.getCalories() + ", " + info.getProteins() + ", " + info.getCarbohydrates() + ", " + info.getFats() + ")";
            Db.result(query);

            // Get the generated keys
            ResultSet rs = Db.result("SELECT LAST_INSERT_ID()");
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento del pasto nel database");
            e.printStackTrace();
        }
        return generatedKey;
    }

    public static void deleteNutritionalInfo(int id) {
        String query = "DELETE FROM nutritionalinfo WHERE id_macro = " + id;
        Db.result(query);
    }
}
