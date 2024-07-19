
package ORM.EntityDao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityDaoTest {
    private ActivityDao activityDao;

    @Before
    public void setUp() {
        activityDao = new ActivityDao();
    }

    @Test
    public void testCountActivitiesForUser() throws SQLException {
        int userId = 1;
        int count = activityDao.countActivitiesForUser(userId);
        assertEquals(0, count); // assuming initially there are no activities for the user
    }

    @Test
    public void testAddActivity() throws SQLException {
        int exerciseId = 1;
        double calories = 100.0;
        float time = 30.0f;
        int userId = 1;

        activityDao.addActivity(exerciseId, calories, time, userId);

        ResultSet rs = activityDao.getActivitiesForUser(userId);
        assertTrue(rs.next());
    }

    @Test
    public void testDeleteActivity() throws SQLException {
        int activityId = 1; // assuming this activityId exists

        activityDao.deleteActivity(activityId);

        ResultSet rs = activityDao.getActivitiesForUser(activityId);
        assertFalse(rs.next());
    }

    // Additional tests for other methods can be added similarly
}
