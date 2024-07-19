// File: test/ORM/EntityDao/MealDaoTest.java

package ORM.EntityDao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MealDaoTest {
    private MealDao mealDao;

    @Before
    public void setUp() {
        mealDao = new MealDao();
    }

    @Test
    public void testGetMealsByUserId() throws SQLException {
        int userId = 1;
        ResultSet rs = mealDao.getMealsByUserId(userId);
        assertNotNull(rs);
    }

    @Test
    public void testGetMealById() throws SQLException {
        int mealId = 1; // assuming this mealId exists
        ResultSet rs = mealDao.getMealById(mealId);
        assertNotNull(rs);
    }

    @Test
    public void testGetMealsCountByUserId() throws SQLException {
        int userId = 1;
        ResultSet rs = mealDao.getMealsCountByUserId(userId);
        assertNotNull(rs);
        if (rs.next()) {
            assertTrue(rs.getInt(1) >= 0);
        }
    }

    @Test
    public void testAddMeal() throws SQLException {
        String type = "Colazione";
        int macroId = 1;
        int newMealId = mealDao.addMeal(type, macroId);
        assertTrue(newMealId > 0);
    }

    @Test
    public void testDeleteMealById() throws SQLException {
        int mealId = 1; // assuming this mealId exists
        mealDao.deleteMealById(mealId);

        ResultSet rs = mealDao.getMealById(mealId);
        assertFalse(rs.next());
    }

    @Test
    public void testGetNutritionalInfoById() throws SQLException {
        int macroId = 1; // assuming this macroId exists
        ResultSet rs = mealDao.getNutritionalInfoById(macroId);
        assertNotNull(rs);
    }

    @Test
    public void testGetFoodsByMealId() throws SQLException {
        int mealId = 1; // assuming this mealId exists
        ResultSet rs = mealDao.getFoodsByMealId(mealId);
        assertNotNull(rs);
    }

    @Test
    public void testGetRecipesByMealId() throws SQLException {
        int mealId = 1; // assuming this mealId exists
        ResultSet rs = mealDao.getRecipesByMealId(mealId);
        assertNotNull(rs);
    }

    @Test
    public void testGetRecipeIdByName() throws SQLException {
        String recipeName = "Pasta"; // assuming this recipeName exists
        ResultSet rs = mealDao.getRecipeIdByName(recipeName);
        assertNotNull(rs);
    }

    @Test
    public void testGetFoodIdByName() throws SQLException {
        String foodName = "Mela"; // assuming this foodName exists
        ResultSet rs = mealDao.getFoodIdByName(foodName);
        assertNotNull(rs);
    }

}
