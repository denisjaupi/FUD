package ORM.EntityDao;


import java.sql.*;

public class DietsDao {

    public void addMeal(int userId, int mealId) throws SQLException {
        String query = "INSERT INTO diets (id_user, meal) VALUES (?, ?)";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, mealId);
        pstmt.executeUpdate();
    }

    public void deleteMeal(int mealId) throws SQLException {
        String query = "DELETE FROM diets WHERE meal = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, mealId);
        pstmt.executeUpdate();
    }

    public void deleteAllMeals(int userId) throws SQLException {
        String query = "DELETE FROM diets WHERE id_user = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, userId);
        pstmt.executeUpdate();
    }

    public ResultSet getUserDiets(int userId) throws SQLException {
        String query = "SELECT * FROM diets WHERE id_user = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, userId);
        return pstmt.executeQuery();
    }

    public int getActivitiesCount(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM activities WHERE id_user = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
}
