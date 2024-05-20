package Controller;

import Model.Database.Db;
import Model.Entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dbDietsManager {
    private static User user;
    private dbMealManager dbM;


    public void setUser(User u){
        user = u;
        dbM= new dbMealManager();
    }

    public static void addMeal(int id_meal){
        String query="insert into diets (id_user, meal) values("+user.getId()+", "+id_meal+")";
        Db.result(query);
    }

    public static void deleteMeal(int id_meal){
        String query="delete from  diets where meal="+id_meal;
        Db.result(query);
    }

    public static void deleteAllMeals(int id_user){
        String query="delete from  diets where id_user="+id_user;
        Db.result(query);
    }

    public void select() {
        try {
            String query = "SELECT * FROM diets WHERE id_user = " + user.getId();
            ResultSet rs = Db.result(query);
            if (rs.next()) {
                do {
                    int id_meal = rs.getInt("meal");
                    Meal m = dbM.select(id_meal, rs.getString("type"));
                    user.getDailyCount().addMeal(m);
                } while (rs.next());
            }
        }catch (SQLException e){
            System.err.println("Errore durante l'esecuzione della query nella select_diets: " + e.getMessage());
        }
    }
}
