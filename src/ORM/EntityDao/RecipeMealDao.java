package ORM.EntityDao;


import java.sql.*;
import java.util.ArrayList;
import Model.Entities.Recipe;

public class RecipeMealDao {

    public void addRecipes(ArrayList<Recipe> recipes, int mealKey) throws SQLException {
        String query = "INSERT INTO recipemeal (recipe, meal) VALUES (?, ?)";
        try (Connection conn = ManagerDao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            for (Recipe r : recipes) {
                pstmt.setInt(1, r.getId());
                pstmt.setInt(2, mealKey);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        }
    }

    public void deleteAllRecipes(int mealKey) throws SQLException {
        String query = "DELETE FROM recipemeal WHERE meal = ?";
        try (Connection conn = ManagerDao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, mealKey);
            pstmt.executeUpdate();
        }
    }

    public void removeRecipe(int mealKey, int recipeKey) throws SQLException {
        String query = "DELETE FROM recipemeal WHERE meal = ? AND recipe = ?";
        try (Connection conn = ManagerDao.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, mealKey);
            pstmt.setInt(2, recipeKey);
            pstmt.executeUpdate();
        }
    }
}
