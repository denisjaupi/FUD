package ORM.EntityDao;


import Model.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public User selectUserByEmail(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = '" + email + "'";
        ResultSet rs = ManagerDao.result(query);
        User user = null;
        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id_user"));
            user.setUserName(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("psw"));
            int idInfo = rs.getInt("id_info");
            if(idInfo!=0) {
                user.setId_persnalData(idInfo);
            }
        }
        return user;
    }

    public void deleteUser(int id) throws SQLException {
        String query = "DELETE FROM users WHERE id_user = " + id;
        ManagerDao.result(query);
    }

    public void updateUser(int id, String username, String email, String password) {
        String query = "UPDATE users SET username = '" + username + "', email = '" + email + "', psw = '" + password + "' WHERE id_user = " + id;
        ManagerDao.result(query);
    }

    public void addUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, psw) VALUES ('" + username + "', '" + email + "', '" + password + "')";
        ManagerDao.result(query);
    }

    public void addInfo(int id_user, int id_info) {
        String query = "UPDATE users SET id_info = '" + id_info + "' WHERE id_user = " + id_user;
        ManagerDao.result(query);
    }

    public boolean checkCredentials(String email, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE email = '" + email + "' AND psw = '" + password + "'";
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            return rs.getInt(1) == 1;
        }
        return false;
    }

    public int getUserInfoId(int id) throws SQLException {
        String query = "SELECT id_info FROM users WHERE id_user = " + id;
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public int countDiets(int id) throws SQLException {
        String query = "SELECT COUNT(*) FROM diets WHERE id_user = " + id;
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public ResultSet getDiets(int id) throws SQLException {
        String query = "SELECT meal FROM diets WHERE id_user = " + id;
        return ManagerDao.result(query);
    }

    public ResultSet getRecipes(int id) throws SQLException {
        String query = "SELECT id_recipe FROM recipes WHERE id_user = " + id;
        return ManagerDao.result(query);
    }


    public boolean checkUserName(String userName) throws SQLException {
        String query="SELECT COUNT(*) FROM users WHERE username = '"+userName+"'";
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            return rs.getInt(1) >= 1;
        }
        return false;
    }
}
