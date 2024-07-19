// File: test/ORM/EntityDao/RecipeDaoTest.java

package ORM.EntityDao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class RecipeDaoTest {
    private RecipeDao recipeDao;

    @Before
    public void setUp() {
        recipeDao = new RecipeDao();
    }

    @Test
    public void testAddRecipe() throws SQLException {
        String name = "Pollo al latte";
        String description = "Test Description";
        int macroId = 1; // assuming this macroId exists
        int userId = 1; // assuming this userId exists
        int recipeId = recipeDao.addRecipe(name, description, macroId, userId);
        assertTrue(recipeId > 0);

        String query = "SELECT * FROM recipes WHERE id_recipe = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, recipeId);
        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
        assertEquals(name, rs.getString("name"));
        assertEquals(description, rs.getString("description"));
    }

    @Test
    public void testGetRecipesByUserId() throws SQLException {
        int userId = 1; // assuming this userId exists
        ResultSet rs = recipeDao.getRecipesByUserId(userId);
        assertNotNull(rs);
    }

    @Test
    public void testGetRecipeById() throws SQLException {
        int recipeId = 1; // assuming this recipeId exists
        ResultSet rs = recipeDao.getRecipeById(recipeId);
        assertNotNull(rs);
    }

    @Test
    public void testGetRecipeByName() throws SQLException {
        String name = "Pollo al latte"; // assuming this name exists
        ResultSet rs = recipeDao.getRecipeByName(name);
        assertNotNull(rs);
    }

    @Test
    public void testDeleteRecipeById() throws SQLException {
        int recipeId = 1; // assuming this recipeId exists
        recipeDao.deleteRecipeById(recipeId);

        String query = "SELECT * FROM recipes WHERE id_recipe = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, recipeId);
        ResultSet rs = pstmt.executeQuery();
        assertFalse(rs.next());
    }

    @Test
    public void testUpdateRecipe() throws SQLException {
        int recipeId = 1; // assuming this recipeId exists
        String newName = "Pollo al curry";
        String newDescription = "Updated Description";
        recipeDao.updateRecipe(recipeId, newName, newDescription);

        String query = "SELECT * FROM recipes WHERE id_recipe = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, recipeId);
        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
        assertEquals(newName, rs.getString("name"));
        assertEquals(newDescription, rs.getString("description"));
    }

    @Test
    public void testGetNutritionalInfoById() throws SQLException {
        int macroId = 1; // assuming this macroId exists
        ResultSet rs = recipeDao.getNutritionalInfoById(macroId);
        assertNotNull(rs);
    }

    @Test
    public void testGetIngredientsByRecipeId() throws SQLException {
        int recipeId = 1; // assuming this recipeId exists
        ResultSet rs = recipeDao.getIngredientsByRecipeId(recipeId);
        assertNotNull(rs);
    }

    @Test
    public void testGetFoodById() throws SQLException {
        int foodId = 1; // assuming this foodId exists
        ResultSet rs = recipeDao.getFoodById(foodId);
        assertNotNull(rs);
    }

    @Test
    public void testIsRecipePresentInMeals() throws SQLException {
        int recipeId = 1; // assuming this recipeId exists
        boolean isPresent = recipeDao.isRecipePresentInMeals(recipeId);
        assertTrue(isPresent);
    }
}
