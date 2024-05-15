package Controller;

import Model.Database.Db;
import Model.Entities.Food;
import Model.Entities.Recipe;
import Model.Entities.RecipeList;
import Model.Entities.User;

import java.sql.*;
import java.util.ArrayList;

public class dbIngredientsManager {
    private final User user;
    private RecipeList recipeList;
    private static ArrayList<Recipe> recipes;

    public dbIngredientsManager(User u) {
        user = u;
        recipeList = user.getRecipe();
        recipes= recipeList.getRecipes();
    }

    public static void addIngredients(ArrayList<Food> foods, int id_recipe) {
        try {
            for (Food food : foods) {
                int foodId = getFood_idFromName(food.getName());
                if (foodId != -1) { // checking the validity of the id
                    insertMeal_foodRecord(id_recipe, foodId, food.getQuantity());
                } else {
                    System.out.println("Errore: impossibile trovare l'id del cibo con il nome " + food.getName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getFood_idFromName(String foodName) throws SQLException {
        String query = "SELECT id_food FROM foods WHERE name = '" + foodName + "'";
        ResultSet rs = Db.result(query);
        if (rs.next()) {
            return rs.getInt("id_food");
        }
        return -1; // if id not exist
    }

    private static void insertMeal_foodRecord(int id_recipe, int id_food, int quantity) throws SQLException {
        String query = "INSERT INTO ingredients (recipe, food, quantity) VALUES (" + id_recipe + ", " + id_food + ", " + quantity + ")";
        Db.result(query);

    }

    public static void deleteIngredients_withRecipe(int id_recipe) {
        String query = "DELETE FROM ingredients WHERE recipe = '" + id_recipe + "'";
        Db.result(query);

        System.out.println("Ingredienti eliminata con successo.");
    }
    public static ResultSet getIngredients_withRecipe(int id_recipe) {
        return Db.result("SELECT F.name, I.quantity FROM ingredients I left join foods F on( I.food=F.id_food) where I.recipe = " + id_recipe);
    }
    public static void deleteIngredients_withRecipeandName(int id_recipe, String name) {
        String query = "DELETE FROM ingredients WHERE recipe = '"+ id_recipe+"' AND food = (SELECT id_food FROM foods WHERE name = '"+name+"');";
        Db.result(query);
        for(Recipe r:recipes){
            if(r.getId()==id_recipe){
                r.deleteIngredients(name);
            }
        }

        System.out.println("Ingredienti eliminata con successo.");
    }

    public static void updateQuantity(int id_recipe, String name, int quantity) {
        String query = "UPDATE ingredients SET quantity = "+quantity+" WHERE recipe = '"+ id_recipe+"' AND food = (SELECT id_food FROM foods WHERE name = '"+name+"');";
        Db.result(query);
        for(Recipe r:recipes){
            if(r.getId()==id_recipe){
                r.updateQuantity(name,quantity);
            }
        }
        System.out.println("Quantità aggiornata con successo.");
    }


}