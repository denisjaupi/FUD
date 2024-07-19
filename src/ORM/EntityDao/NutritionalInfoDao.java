package ORM.EntityDao;


import Model.Entities.NutritionalInfo;

import java.sql.*;

public class NutritionalInfoDao {

    public int addNutritionalInfo(NutritionalInfo info) throws SQLException {
        int generatedKey = 0;
        String query = "INSERT INTO nutritionalinfo (calories, proteins, carbohydrates, fats) VALUES (?, ?, ?, ?)";
        try (Connection conn = ManagerDao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, info.getCalories());
            pstmt.setDouble(2, info.getProteins());
            pstmt.setDouble(3, info.getCarbohydrates());
            pstmt.setDouble(4, info.getFats());
            pstmt.executeUpdate();

            // Get the generated keys
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }
        }
        return generatedKey;
    }

    public void deleteNutritionalInfo(int id) throws SQLException {
        String query = "DELETE FROM nutritionalinfo WHERE id_macro = ?";
        try (Connection conn = ManagerDao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

