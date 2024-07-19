package ORM.EntityDao;


import java.sql.*;

public class FoodDao {

    public ResultSet getAllFoods() throws SQLException {
        String query = "SELECT name, calories, proteins, carbohydrates, fats " +
                "FROM foods F " +
                "LEFT JOIN nutritionalinfo N ON F.macro = N.id_macro " +
                "ORDER BY name ASC";
        return ManagerDao.result(query);
    }

    public ResultSet getFoodById(int id) throws SQLException {
        String query = "SELECT name, calories, proteins, carbohydrates, fats " +
                "FROM foods F " +
                "LEFT JOIN nutritionalinfo N ON F.macro = N.id_macro " +
                "WHERE id_food = " + id;
        return ManagerDao.result(query);
    }

    public ResultSet getFoodByName(String name) throws SQLException {
        String query = "SELECT name, calories, proteins, carbohydrates, fats " +
                "FROM foods F " +
                "LEFT JOIN nutritionalinfo N ON F.macro = N.id_macro " +
                "WHERE name = '" + name + "'";
        return ManagerDao.result(query);
    }

    public void addFood(String name, int macroId) throws SQLException {
        String query = "INSERT INTO foods (name, macro) VALUES (?, ?)";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setInt(2, macroId);
        pstmt.executeUpdate();
    }

    public int getFoodIdByName(String name) throws SQLException {
        String query = "SELECT id_food FROM foods WHERE name = '" + name + "'";
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public void removeFoodById(int id) throws SQLException {
        String query = "DELETE FROM foods WHERE id_food = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public void removeFoodByName(String name) throws SQLException {
        String query = "DELETE FROM foods WHERE name = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.executeUpdate();
    }

    public ResultSet getNutritionalInfoByMacroId(int macroId) throws SQLException {
        String query = "SELECT * FROM nutritionalinfo WHERE id_macro = " + macroId;
        return ManagerDao.result(query);
    }
}

