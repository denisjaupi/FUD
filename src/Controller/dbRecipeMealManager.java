package Controller;

import Model.Database.Db;
import Model.Entities.Recipe;

import java.util.ArrayList;

public class dbRecipeMealManager {


    public static void addRecipes(ArrayList<Recipe> recipes, int mealKey) {
        for (Recipe r : recipes) {
            String query = "INSERT INTO recipemeal (recipe, meal) VALUES (" + r.getId() + ", " + mealKey + ")";
            Db.result(query);
        }
    }


    public static void deleteAllRecipes(int mealKey) {
        String query = "DELETE FROM recipemeal WHERE meal = " + mealKey;
        Db.result(query);
    }


    public static void removeRecipe(int mealKey, int recipeKey) {
        String query = "DELETE FROM recipemeal WHERE meal = " + mealKey + " AND recipe = " + recipeKey;
        Db.result(query);
    }
}
