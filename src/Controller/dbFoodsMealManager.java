package Controller;

import Model.Database.Db;
import Model.Entities.Food;

import java.util.ArrayList;

public class dbFoodsMealManager {


    public static void addFoods(ArrayList<Food> foods, int mealKey) {
        for (Food f : foods) {
            String query = "INSERT INTO foodsmeal (food, meal, quantity_food) VALUES (" + f.getId() + ", " + mealKey + ", "+f.getQuantity()+")";
            Db.result(query);
        }
    }

    public static void deleteAllFoods(int mealKey) {
        String query = "DELETE FROM foodsmeal WHERE meal = " + mealKey;
        Db.result(query);
    }


    public static void removeFood(int mealKey, int foodKey) {
        String query = "DELETE FROM foodsmeal WHERE meal = " + mealKey + " AND food = " + foodKey;
        Db.result(query);
    }

    public static void updateQuantity(int mealKey, int foodKey, int quantity) {
        String query = "UPDATE foodsmeal SET quantity_food = " + quantity + " WHERE meal = " + mealKey + " AND food = " + foodKey;
        Db.result(query);
    }
}
