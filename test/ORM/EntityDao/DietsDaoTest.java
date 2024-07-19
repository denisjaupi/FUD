// File: test/ORM/EntityDao/DietsDaoTest.java

package ORM.EntityDao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class DietsDaoTest {
    private DietsDao dietsDao;

    @Before
    public void setUp() {
        dietsDao = new DietsDao();
    }

    @Test
    public void testAddMeal() throws SQLException {
        int userId = 1;
        int mealId = 1;
        dietsDao.addMeal(userId, mealId);

        String query = "SELECT * FROM diets WHERE id_user = ? AND meal = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, userId);
        pstmt.setInt(2, mealId);
        ResultSet rs = pstmt.executeQuery();
        assertTrue(rs.next());
    }

    @Test
    public void testDeleteMeal() throws SQLException {
        int mealId = 1; // assuming this mealId exists
        dietsDao.deleteMeal(mealId);

        String query = "SELECT * FROM diets WHERE meal = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, mealId);
        ResultSet rs = pstmt.executeQuery();
        assertFalse(rs.next());
    }

    @Test
    public void testDeleteAllMeals() throws SQLException {
        int userId = 1; // assuming this userId exists
        dietsDao.deleteAllMeals(userId);

        String query = "SELECT * FROM diets WHERE id_user = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, userId);
        ResultSet rs = pstmt.executeQuery();
        assertFalse(rs.next());
    }

    @Test
    public void testGetUserDiets() throws SQLException {
        int userId = 1; // assuming this userId exists
        ResultSet rs = dietsDao.getUserDiets(userId);
        assertNotNull(rs);
    }

    @Test
    public void testGetActivitiesCount() throws SQLException {
        int userId = 1; // assuming this userId exists
        int count = dietsDao.getActivitiesCount(userId);
        assertTrue(count >= 0);
    }
}
