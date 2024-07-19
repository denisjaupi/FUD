package ORM.EntityDao;


import Model.Entities.Food;
import Model.Entities.NutritionalInfo;
import Model.Entities.Recipe;

import java.sql.*;
import java.util.ArrayList;

public class RecipeDao {

    public ResultSet getRecipesByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM recipes WHERE id_user = " + userId;
        return ManagerDao.result(query);
    }

    public ResultSet getRecipeById(int recipeId) throws SQLException {
        String query = "SELECT * FROM recipes WHERE id_recipe = " + recipeId;
        return ManagerDao.result(query);
    }

    public ResultSet getRecipeByName(String name) throws SQLException {
        String query = "SELECT * FROM recipes WHERE name = '" + name + "'";
        return ManagerDao.result(query);
    }

    public int addRecipe(String name, String description, int macroId, int userId) throws SQLException {
        Connection conn = ManagerDao.getConnection();
        String query = "INSERT INTO recipes (name, description, macro, id_user) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, name);
        pstmt.setString(2, description);
        pstmt.setInt(3, macroId);
        pstmt.setInt(4, userId);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public void deleteRecipeById(int recipeId) throws SQLException {
        String query = "DELETE FROM recipes WHERE id_recipe = " + recipeId;
        ManagerDao.result(query);
    }

    public void updateRecipe(int id, String name, String description) throws SQLException {
        String query = "UPDATE recipes SET name = ?, description = ? WHERE id_recipe = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, description);
        pstmt.setInt(3, id);
        pstmt.executeUpdate();
    }

    public ResultSet getNutritionalInfoById(int macroId) throws SQLException {
        String query = "SELECT * FROM nutritionalinfo WHERE id_macro = " + macroId;
        return ManagerDao.result(query);
    }

    public ResultSet getIngredientsByRecipeId(int recipeId) throws SQLException {
        String query = "SELECT * FROM ingredients WHERE recipe = " + recipeId;
        return ManagerDao.result(query);
    }

    public ResultSet getFoodById(int foodId) throws SQLException {
        String query = "SELECT * FROM foods WHERE id_food = " + foodId;
        return ManagerDao.result(query);
    }

    public boolean isRecipePresentInMeals(int recipeId) throws SQLException {
        String query = "SELECT COUNT(*) FROM recipemeal WHERE meal = " + recipeId;
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }
}
