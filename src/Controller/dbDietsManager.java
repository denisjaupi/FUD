package Controller;

import Model.Database.Db;
import Model.Entities.User;

public class dbDietsManager {
    private static User user;

    public dbDietsManager(User user) {
        dbDietsManager.user = user;
    }

    public static void addMeal(int id_meal){
        String query="insert into diets (id_user, meal) values("+user.getId()+", "+id_meal+")";
        Db.result(query);
    }

    public static void deleteMeal(int id_meal){
        String query="delete from  diets where meal="+id_meal;
        Db.result(query);
    }
}
