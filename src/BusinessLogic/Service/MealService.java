package BusinessLogic.Service;



import ORM.EntityDao.DietsDao;
import ORM.EntityDao.MealDao;
import Model.Entities.*;

import java.sql.*;
import java.util.ArrayList;

public class MealService {

    private User user;
    private static DailyCount dailyCount;
    private MealDao mealDao;
    private NutritionalInfoService nutritionalInfoService;
    private DietService dietService;
    private FoodsMealService foodsMealService;
    private RecipeService recipeService;
    private FoodService foodService;
    private RecipeMealService recipeMealService;
    private IngredientsService ingredientsService;

    public MealService(MealDao mealDao, NutritionalInfoService ntis, DietService ds, FoodsMealService fms, RecipeService rs, FoodService fs, RecipeMealService rms, IngredientsService is) {
        this.mealDao = mealDao;
        this.dietService=ds;
        this.foodService= fs;
        this.nutritionalInfoService=ntis;
        this.recipeService=rs;
        this.foodsMealService=fms;
        this.recipeMealService= rms;
        this.ingredientsService=is;
    }

    public void setUser(User u) {
        user = u;
        dailyCount = user.getDailyCount();
    }

    public void addMeal(String type, NutritionalInfo info, ArrayList<Food> foods, ArrayList<Recipe> recipes) {
        try {
            ResultSet rs = mealDao.getMealsCountByUserId(user.getId());
            if (rs.next()) {
                int count = rs.getInt(1);
                if (user.getPersonalData().getCount_meal() < count) {
                    int macroId = nutritionalInfoService.addNutritionalInfo(info);
                    if (macroId != 0) {
                        int mealId = mealDao.addMeal(type, macroId);
                        if (mealId != -1) {
                            dietService.addMeal(mealId);
                            foodsMealService.addFoods(foods, mealId);
                            recipeMealService.addRecipes(recipes, mealId);
                            Meal meal = new Meal(mealId, type);
                            for (Food food : foods) {
                                meal.addFood(food);
                            }
                            for (Recipe recipe : recipes) {
                                meal.addRecipe(recipe);
                            }
                            meal.setInfo(info);
                            dailyCount.addMeal(meal);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento del pasto nel database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deleteMeal(int mealId) {
        recipeMealService.deleteAllRecipes(mealId);
        foodsMealService.deleteAllFoods(mealId);
        dietService.deleteMeal(mealId);
        try {
            ResultSet rs = mealDao.getMealById(mealId);
            if (rs.next()) {
                int macroId = rs.getInt("macro");
                mealDao.deleteMealById(mealId);
                nutritionalInfoService.deleteNutritionalInfo(macroId);
                dailyCount.deleteMeal(mealId);
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'eliminazione del pasto dal database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeOneRecipe(int mealId, String recipeName) {
        try {
            ResultSet rs = mealDao.getRecipeIdByName(recipeName);
            if (rs.next()) {
                int recipeId = rs.getInt("id_recipe");
                recipeMealService.removeRecipe(mealId, recipeId);
                dailyCount.getMeals().forEach(meal -> {
                    if (meal.getId() == mealId) {
                        meal.removeRecipe(recipeId);
                    }
                });
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la rimozione della ricetta dal pasto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void removeOneFood(int mealId, String foodName) {
        try {
            ResultSet rs = mealDao.getFoodIdByName(foodName);
            if (rs.next()) {
                int foodId = rs.getInt("id_food");
                foodsMealService.removeFood(mealId, foodId);
                dailyCount.getMeals().forEach(meal -> {
                    if (meal.getId() == mealId) {
                        meal.removeFood(foodId);
                    }
                });
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la rimozione del cibo dal pasto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Meal selectMeal(int mealId, String type) {
        try {
            ResultSet rsMeal = mealDao.getMealById(mealId);
            if (rsMeal.next()) {
                int macroId = rsMeal.getInt("macro");
                ResultSet rsMacro = mealDao.getNutritionalInfoById(macroId);
                NutritionalInfo macro = new NutritionalInfo(rsMacro.getDouble("calories"), rsMacro.getDouble("proteins"), rsMacro.getDouble("fats"), rsMacro.getDouble("carbohydrates"));
                Meal meal = new Meal(mealId, type);
                ResultSet rsFoods = mealDao.getFoodsByMealId(mealId);
                while (rsFoods.next()) {
                    Food food = foodService.selectFood(rsFoods);
                    meal.addFood(food);
                }
                ResultSet rsRecipes = mealDao.getRecipesByMealId(mealId);
                while (rsRecipes.next()) {
                    Recipe recipe = recipeService.selectRecipe(rsRecipes);
                    ResultSet rsIngredients = ingredientsService.getIngredients_withRecipe(recipe.getId());
                    while (rsIngredients.next()) {
                        int id_food= rsIngredients.getInt("food");
                        Food food = foodService.getFood_withId(id_food);
                        recipe.addIngredient(food);
                    }
                    meal.addRecipe(recipe);
                }
                meal.setInfo(macro);
                return meal;
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la selezione del pasto: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void updateFoodQuantity(int mealId, int foodId, int quantity) {
        try {
            mealDao.updateFoodQuantity(mealId, foodId, quantity);
            dailyCount.getMeals().forEach(meal -> {
                if (meal.getId() == mealId) {
                    meal.updateQuantity_f(foodId, quantity);
                }
            });
        } catch (SQLException e) {
            System.out.println("Errore durante l'aggiornamento della quantità di cibo: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

