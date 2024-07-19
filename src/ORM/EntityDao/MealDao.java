package ORM.EntityDao;


import Model.Entities.NutritionalInfo;

import java.sql.*;

public class MealDao {


    public ResultSet getMealsByUserId(int userId) throws SQLException {
        String query = "SELECT * FROM meal WHERE id_user = " + userId;
        return ManagerDao.result(query);
    }

    public ResultSet getMealById(int mealId) throws SQLException {
        String query = "SELECT * FROM meal WHERE id_meal = " + mealId;
        return ManagerDao.result(query);
    }

    public ResultSet getMealsCountByUserId(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM diets WHERE id_user = " + userId;
        return ManagerDao.result(query);
    }

    public int addMeal(String type, int macroId) throws SQLException {
        Connection conn = ManagerDao.getConnection();
        String query = "INSERT INTO meal (type, macro) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, type);
        pstmt.setInt(2, macroId);
        pstmt.executeUpdate();
        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public void deleteMealById(int mealId) throws SQLException {
        String query = "DELETE FROM meal WHERE id_meal = " + mealId;
        ManagerDao.result(query);
    }

    public ResultSet getNutritionalInfoById(int macroId) throws SQLException {
        String query = "SELECT * FROM nutritionalinfo WHERE id_macro = " + macroId;
        return ManagerDao.result(query);
    }

    public ResultSet getFoodsByMealId(int mealId) throws SQLException {
        String query = "SELECT * FROM foods WHERE id_food IN (SELECT food FROM foodsmeal WHERE meal = " + mealId + ")";
        return ManagerDao.result(query);
    }

    public ResultSet getRecipesByMealId(int mealId) throws SQLException {
        String query = "SELECT * FROM recipes WHERE id_recipe IN (SELECT recipe FROM recipemeal WHERE meal = " + mealId + ")";
        return ManagerDao.result(query);
    }

    public ResultSet getRecipeIdByName(String recipeName) throws SQLException {
        String query = "SELECT id_recipe FROM recipes WHERE name = '" + recipeName + "'";
        return ManagerDao.result(query);
    }

    public ResultSet getFoodIdByName(String foodName) throws SQLException {
        String query = "SELECT id_food FROM foods WHERE name = '" + foodName + "'";
        return ManagerDao.result(query);
    }

    public void updateFoodQuantity(int mealId, int foodId, int quantity) throws SQLException {
        String query = "UPDATE foodsmeal SET quantity = ? WHERE meal = ? AND food = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, quantity);
        pstmt.setInt(2, mealId);
        pstmt.setInt(3, foodId);
        pstmt.executeUpdate();
    }
}

