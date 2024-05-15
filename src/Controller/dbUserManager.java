package Controller;

import Model.Database.Db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dbUserManager {

    public static void deleteUser(int id) {
        String query = "DELETE FROM users WHERE id_user = " + id;
        Db.result(query);
    }
    public static void updateUser(int id, String username, String email, String password) {
        String query = "UPDATE users SET  username = '" + username + "', email = '" + email + "', psw = '" + password + "' WHERE id_user = " + id;
        Db.result(query);
    }
    public static void addUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, psw) VALUES ('" + username + "', '" + email + "', '" + password + "')";
        Db.result(query);
    }
    public static void addinfo(int id_user,String username, String email, String password, int id_info) {
        String query = "update users SET  username = '" + username + "', email = '" + email + "', psw = '" + password + "', id_info='"+id_info+"' WHERE id_user = " + id_user;
        Db.result(query);
    }
    public static void updateInfo(int id_user, int id_info) {
        String query = "UPDATE users SET id_info = " + id_info + " WHERE id_user = " + id_user;
        Db.result(query);
    }
    public static boolean checkCredentials(String  email, String password) throws SQLException {
        String query = "SELECT Count(*) FROM users WHERE email = '" + email + "' AND psw = '" + password + "'";
        ResultSet rs= Db.result(query) ;
        int count=rs.getInt(0);
        if(count==1){
            return true;
        }else{
            return false;
        }
    }

}
