package BusinessLogic.Service;


import ORM.EntityDao.IngredientsDao;
import Model.Entities.Food;
import Model.Entities.Recipe;
import Model.Entities.RecipeList;
import Model.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IngredientsService {

    private User user;
    private RecipeList recipeList;
    private static ArrayList<Recipe> recipes;
    private IngredientsDao ingredientsDao;

    public IngredientsService(IngredientsDao ingredientsDao) {
        this.ingredientsDao = ingredientsDao;
    }

    public void setUser(User u) {
        user = u;
        recipeList = user.getRecipe();
        recipes = recipeList.getRecipes();
    }

    public void addIngredients(ArrayList<Food> foods, int id_recipe) {
        try {
            ingredientsDao.addIngredients(foods, id_recipe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteIngredients_withRecipe(int id_recipe) {
        try {
            ingredientsDao.deleteIngredients_withRecipe(id_recipe);
            System.out.println("Ingredienti eliminata con successo.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getIngredients_withRecipe(int id_recipe) {
        try {
            return ingredientsDao.getIngredients_withRecipe(id_recipe);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteIngredients_withRecipeandName(int id_recipe, String name) {
        try {
            ingredientsDao.deleteIngredients_withRecipeandName(id_recipe, name);
            for (Recipe r : recipes) {
                if (r.getId() == id_recipe) {
                    r.deleteIngredients(name);
                }
            }
            System.out.println("Ingredienti eliminata con successo.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateQuantity(int id_recipe, String name, int quantity) {
        try {
            ingredientsDao.updateQuantity(id_recipe, name, quantity);
            for (Recipe r : recipes) {
                if (r.getId() == id_recipe) {
                    r.updateQuantity(name, quantity);
                }
            }
            System.out.println("Quantità aggiornata con successo.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

