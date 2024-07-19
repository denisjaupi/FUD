package BusinessLogic.Service;



import Model.Entities.Food;
import ORM.EntityDao.FoodsMealDao;

import java.sql.SQLException;
import java.util.ArrayList;

public class FoodsMealService {

    private FoodsMealDao foodsMealDao ;

    public FoodsMealService(FoodsMealDao foodsMealDao) {
        this.foodsMealDao = foodsMealDao;
    }
    public void addFoods(ArrayList<Food> foods, int mealKey) {
        try {
            foodsMealDao.addFoods(foods, mealKey);
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiunta dei cibi al pasto: " + e.getMessage());
        }
    }

    public void deleteAllFoods(int mealKey) {
        try {
            foodsMealDao.deleteAllFoods(mealKey);
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione di tutti i cibi dal pasto: " + e.getMessage());
        }
    }

    public void removeFood(int mealKey, int foodKey) {
        try {
            foodsMealDao.removeFood(mealKey, foodKey);
        } catch (SQLException e) {
            System.err.println("Errore durante l'eliminazione del cibo dal pasto: " + e.getMessage());
        }
    }

    public void updateQuantity(int mealKey, int foodKey, int quantity) {
        try {
            foodsMealDao.updateQuantity(mealKey, foodKey, quantity);
        } catch (SQLException e) {
            System.err.println("Errore durante l'aggiornamento della quantità del cibo nel pasto: " + e.getMessage());
        }
    }
}
