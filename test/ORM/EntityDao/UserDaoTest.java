// File: test/ORM/EntityDao/UserDaoTest.java

package ORM.EntityDao;

import Model.Entities.User;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;

public class UserDaoTest {
    private UserDao userDao;

    @Before
    public void setUp() {
        userDao = new UserDao();
    }

    @Test
    public void testSelectUserByEmail() throws SQLException {
        String email = "dario"; // assuming this email exists
        User user = userDao.selectUserByEmail(email);
        assertNotNull(user);
        assertEquals(email, user.getEmail());
    }

    @Test
    public void testDeleteUser() throws SQLException {
        int userId = 1; // assuming this userId exists
        userDao.deleteUser(userId);

        User user = userDao.selectUserByEmail("dario"); // replace with actual email
        assertNull(user);
    }

    @Test
    public void testUpdateUser() throws SQLException {
        int userId = 1; // assuming this userId exists
        String newUsername = "Filippo";
        String newEmail = "updated@example.com";
        String newPassword = "newPassword";
        userDao.updateUser(userId, newUsername, newEmail, newPassword);

        User user = userDao.selectUserByEmail(newEmail);
        assertNotNull(user);
        assertEquals(newUsername, user.getUserName());
        assertEquals(newEmail, user.getEmail());
        assertEquals(newPassword, user.getPassword());
    }

    @Test
    public void testAddUser() throws SQLException {
        String username = "newUser";
        String email = "newuser@example.com";
        String password = "password";
        userDao.addUser(username, email, password);

        User user = userDao.selectUserByEmail(email);
        assertNotNull(user);
        assertEquals(username, user.getUserName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testAddInfo() throws SQLException {
        int userId = 1; // assuming this userId exists
        int infoId = 2; // assuming this infoId exists
        userDao.addInfo(userId, infoId);

        int retrievedInfoId = userDao.getUserInfoId(userId);
        assertEquals(infoId, retrievedInfoId);
    }

    @Test
    public void testCheckCredentials() throws SQLException {
        String email = "dario"; // assuming this email exists
        String password = "dario"; // assuming this password is correct
        assertTrue(userDao.checkCredentials(email, password));

        String wrongPassword = "wrongPassword";
        assertFalse(userDao.checkCredentials(email, wrongPassword));
    }

    @Test
    public void testGetUserInfoId() throws SQLException {
        int userId = 1; // assuming this userId exists
        int infoId = userDao.getUserInfoId(userId);
        assertTrue(infoId > 0);
    }

    @Test
    public void testCountDiets() throws SQLException {
        int userId = 1; // assuming this userId exists
        int count = userDao.countDiets(userId);
        assertTrue(count >= 0);
    }

    @Test
    public void testGetDiets() throws SQLException {
        int userId = 1; // assuming this userId exists
        ResultSet rs = userDao.getDiets(userId);
        assertNotNull(rs);
    }

    @Test
    public void testGetRecipes() throws SQLException {
        int userId = 1; // assuming this userId exists
        ResultSet rs = userDao.getRecipes(userId);
        assertNotNull(rs);
    }

    @Test
    public void testCheckUserName() throws SQLException {
        String userName = "Dario"; // assuming this username exists
        assertTrue(userDao.checkUserName(userName));

        String nonExistingUserName = "nonExistingUser";
        assertFalse(userDao.checkUserName(nonExistingUserName));
    }
}
