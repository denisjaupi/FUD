package BusinessLogic.Service;


import Model.Entities.*;
import ORM.EntityDao.RecipeDao;

import java.sql.*;
import java.util.ArrayList;

public class RecipeService {

    private static User user;
    private static RecipeList recipeList;
    private static ArrayList<Meal> meals;
    private static DailyCount dailyCount;
    private RecipeDao recipeDao;
    private NutritionalInfoService nutritionalInfoService;
    private IngredientsService ingredientsService;
    private FoodService foodService;

    public RecipeService(RecipeDao recipeDao, NutritionalInfoService nis, IngredientsService is, FoodService fs){
        this.recipeDao = recipeDao;
        this.foodService=fs;
        this.ingredientsService=is;
        this.nutritionalInfoService=nis;
    }

    public void setUser(User u) {
        user = u;
        recipeList = user.getRecipe();
        dailyCount = user.getDailyCount();
        if (dailyCount != null) {
            meals = dailyCount.getMeals();
        }
    }

    public User getUser() {
        return user;
    }

    public void loadUserRecipes() {
        try {
            ResultSet rs = recipeDao.getRecipesByUserId(user.getId());
            while (rs.next()) {
                Recipe recipe = mapRecipe(rs);
                ResultSet rsIngredients = recipeDao.getIngredientsByRecipeId(recipe.getId());
                while (rsIngredients.next()) {
                    ResultSet rsFood = recipeDao.getFoodById(rsIngredients.getInt("food"));
                    if (rsFood.next()) {
                        Food food = foodService.selectFood(rsFood);
                        recipe.addIngredient(food);
                    }
                }
                user.getRecipe().addRecipe(recipe);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il caricamento delle ricette: " + e.getMessage());
        }
    }

    private Recipe mapRecipe(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String description = rs.getString("description");
        int id = rs.getInt("id_recipe");
        int macroId = rs.getInt("macro");
        ResultSet rsMacro = recipeDao.getNutritionalInfoById(macroId);
        NutritionalInfo macro = new NutritionalInfo(rsMacro.getDouble("calories"), rsMacro.getDouble("proteins"), rsMacro.getDouble("fats"), rsMacro.getDouble("carbohydrates"));
        Recipe recipe = new Recipe(id, name, description);
        recipe.setNutritionalInfo(macro);
        return recipe;
    }

    public void addRecipe(String name, String description, NutritionalInfo info, ArrayList<Food> foods) {
        try {
            int macroId = nutritionalInfoService.addNutritionalInfo(info);
            if (macroId != 0) {
                int recipeId = recipeDao.addRecipe(name, description, macroId, user.getId());
                if (recipeId != -1) {
                    ingredientsService.addIngredients(foods, recipeId);
                    Recipe recipe = new Recipe(recipeId, name, description);
                    for (Food food : foods) {
                        recipe.addIngredient(food);
                    }
                    recipe.setNutritionalInfo(info);
                    recipeList.addRecipe(recipe);
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento della ricetta: " + e.getMessage());
        }
    }

    public void removeRecipeById(int id) {
        try {
            if (!recipeDao.isRecipePresentInMeals(id)) {
                ingredientsService.deleteIngredients_withRecipe(id);

                ResultSet rsMacro = recipeDao.getRecipeById(id);
                if (rsMacro.next()) {
                    int macroId = rsMacro.getInt("macro");
                    nutritionalInfoService.deleteNutritionalInfo(macroId);
                }

                recipeDao.deleteRecipeById(id);
                recipeList.removeRecipe(id);
                for (Meal meal : meals) {
                    meal.removeRecipe(id);
                }
            } else {
                System.out.println("Impossibile eliminare la ricetta. L'ID è presente in un pasto.");
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'eliminazione della ricetta: " + e.getMessage());
        }
    }

    public void removeOneFood(int recipeId, String foodName) {
        ingredientsService.deleteIngredients_withRecipeandName(recipeId, foodName);
        for (Recipe recipe : recipeList.getRecipes()) {
            if (recipe.getId() == recipeId) {
                recipe.deleteIngredients(foodName);
            }
        }
    }

    public void updateRecipe(int id, String name, String description) {
        try {
            recipeDao.updateRecipe(id, name, description);
            recipeList.updateRecipe_list(id, name, description);
            dailyCount.getMeals().forEach(meal -> meal.updateRecipe_meal(id, name, description));
        } catch (SQLException e) {
            System.out.println("Errore durante l'aggiornamento della ricetta: " + e.getMessage());
        }
    }


    public Recipe selectRecipe(ResultSet rs) throws SQLException {
        String name_recipe = rs.getString("name");
        String desc= rs.getString("description");
        int id_recipe= rs.getInt("id_recipe");
        ResultSet rs3 = recipeDao.getNutritionalInfoById(rs.getInt("macro"));
        NutritionalInfo macro = new NutritionalInfo(rs3.getDouble("calories"), rs3.getDouble("proteins"), rs3.getDouble("fats"), rs3.getDouble("carbohydrates"));
        Recipe r= new Recipe(id_recipe, name_recipe, desc);
        r.setNutritionalInfo(macro);
        return r;
    }
}
