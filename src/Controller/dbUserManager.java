package Controller;

import Model.Database.Db;
import Model.Entities.PersonalData;
import Model.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dbUserManager {
    private static User user = new User();
    private PersonalData pd;
    private dbPersonalDataManager dbPM = new dbPersonalDataManager();

    public dbUserManager() {
       dbPM.setUser(user);
    }

    public void setUser(User u){
        user = u;
    }

    public void selectData(String email)  {
        try {
            String query = "SELECT * FROM users WHERE email = '" + email + "'";
            ResultSet rs = Db.result(query);
            if (rs.next()) {
                user.setId(rs.getInt("id_user"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("psw"));
                int idInfo = rs.getInt("id_info");
                if(idInfo!=0) {
                    dbPM.selectPersonalData(idInfo);
                }
                user.getDailyCount().setUsername(user.getUserName());
            }
        }catch(SQLException e){
            System.err.println("Errore durante l'esecuzione della query nella select_user: " + e.getMessage());
        }
    }

    public static void deleteUser(int id) throws SQLException {
        int id_info=0;
        dbActivitiesManager.deleteActivities_withUser(id);
        String query="select id_info from users where id_user=" +id;
        ResultSet id_i=Db.result(query);
        if(id_i.next()) {
            id_info = id_i.getInt(1);
            if (!id_i.wasNull()) {
                dbCalculateProfileDataManager.deleteCalculateProfileData(id_info);
            }
            query = "SELECT COUNT (*) FROM diets WHERE id_user = " + id;
            ResultSet ris = Db.result(query);
            if (ris.next()) {
                int count = ris.getInt(1);
                if (count > 0) {
                    query = "select meal from diets where id_user=" + id;
                    while (Db.result(query).next()) {
                        dbMealManager.deleteMeal_db(Db.result(query).getInt(0));
                    }
                    dbDietsManager.deleteAllMeals(id);

                    query = "Select id_recipe from recipes where id_user=" + id;
                    while (Db.result(query).next()) {
                        dbRecipeManager.removeRecipe_withId(Db.result(query).getInt(0));
                    }
                }
            }

        }

        query = "DELETE FROM users WHERE id_user = " + id;
        Db.result(query);
        if (!id_i.wasNull()) {
            dbPersonalDataManager.deletePersonalData(id_info);
        }
    }


    public static void updateUser(int id, String username, String email, String password) {
        String query = "UPDATE users SET  username = '" + username + "', email = '" + email + "', psw = '" + password + "' WHERE id_user = " + id;
        Db.result(query);
        user.setEmail(email);
        user.setPassword(password);
        user.setUserName(username);
    }


    public static void addUser(String username, String email, String password) {
        String query = "INSERT INTO users (username, email, psw) VALUES ('" + username + "', '" + email + "', '" + password + "')";
        Db.result(query);
    }


    public static void addinfo(int id_user, int id_info) {
        String query = "update users SET  id_info='"+id_info+"' WHERE id_user = " + id_user;
        Db.result(query);
    }


    public static boolean checkCredentials(String  email, String password) throws SQLException {
        String query = "SELECT Count(*) FROM users WHERE email = '" + email + "' AND psw = '" + password + "'";
        ResultSet rs= Db.result(query);
        if(rs.next()){
            int count=rs.getInt(1);
            if(count==1){
                return true;
            }else{
                return false;
            }
        } else {
            return false;
        }
    }

    public static User getUser() {
        return user;
    }

}
