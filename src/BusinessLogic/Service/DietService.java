package BusinessLogic.Service;

import ORM.EntityDao.DietsDao;
import Model.Entities.Meal;
import Model.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DietService{
    private static User user;
    private final MealService mealService;
    private DietsDao dietsDao;

    public DietService(DietsDao dietsDao, MealService mls) {
        this.dietsDao = dietsDao;
        this.mealService=mls;
    }
    public void setUser(User u) {
        user = u;
    }

    public void addMeal(int id_meal) {
        try {
            dietsDao.addMeal(user.getId(), id_meal);
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiunta del pasto: " + e.getMessage());
        }
    }

    public  void deleteMeal(int id_meal) {
        try {
            dietsDao.deleteMeal(id_meal);
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione del pasto: " + e.getMessage());
        }
    }

    public void deleteAllMeals(int id_user) {
        try {
            dietsDao.deleteAllMeals(id_user);
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione di tutti i pasti: " + e.getMessage());
        }
    }

    public void select() {
        try {
            int countAzz = dietsDao.getActivitiesCount(user.getId());
            if (countAzz > 0) {
                ResultSet rs = dietsDao.getUserDiets(user.getId());
                while (rs.next()) {
                    int id_meal = rs.getInt("meal");
                    Meal m = mealService.selectMeal(id_meal, rs.getString("type"));
                    user.getDailyCount().addMeal(m);
                }
            }
        } catch (SQLException e) {
            System.err.println("Errore durante l'esecuzione della query nella select_diets: " + e.getMessage());
        }
    }
}
