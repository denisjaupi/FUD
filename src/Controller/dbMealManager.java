package Controller;

import Model.Database.Db;
import Model.Entities.*;

import java.sql.*;
import java.util.ArrayList;

public class dbMealManager {

    private User user;
    private static DailyCount dailyCount;

    public dbMealManager(User user) {
        this.user = user;
        dailyCount = user.getDailyCount();
    }

    public void addMeal_fromDb(String type, NutritionalInfo info, ArrayList<Food> foods, ArrayList<Recipe> recipes) {

        try {
            // Check if the user's meal count is less than the total number of meals linked to the user in the diets table
            String query = "SELECT COUNT(*) FROM diets WHERE id_user = " + user.getId();
            ResultSet rs = Db.result(query);
            if (rs.next()) {
                int count = rs.getInt(1);
                if (user.getPersonalData().getCount_meal() < count) {
                    int generatedKey= dbNutritionalInfoManager.addNutritionalInfo(info);
                    if(generatedKey!=0){
                        query = "INSERT INTO meal (type, macro) VALUES ('" + type + "', " + generatedKey + ")";
                        Db.result(query);

                        // Get the generated keys
                        rs = Db.result("SELECT LAST_INSERT_ID()");
                        if (rs.next()) {
                            int mealKey = rs.getInt(1);

                            dbDietsManager.addMeal(mealKey);
                            dbFoodsMealManager.addFoods(foods, mealKey);
                            dbRecipeMealManager.addRecipes(recipes, mealKey);
                            Meal m = new Meal(mealKey, type);
                            for (Food f : foods) {
                                m.addFood(f);
                            }
                            for (Recipe r : recipes) {
                                m.addRecipe(r);
                            }
                            m.setInfo(info);
                            dailyCount.addMeal(m);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento del pasto nel database");
            e.printStackTrace();
        }
    }

    public void deleteMeal_db(int id_meal){

        dbRecipeMealManager.deleteAllRecipes(id_meal);
        dbFoodsMealManager.deleteAllFoods(id_meal);
        dbDietsManager.deleteMeal(id_meal);
        try {
            String query="Select macro from meal where id_meal="+id_meal;
            ResultSet id_m=Db.result(query);
            if(id_m.next()) {
                int id_macro = id_m.getInt(1);
                query="delete from meal where id_meal="+id_meal;
                Db.result(query);

                dbNutritionalInfoManager.deleteNutritionalInfo(id_macro);

                dailyCount.deleteMeal(id_meal);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeOneRecipe(int id_meal,String name_recipe) throws SQLException {
        String query="Select id_recipe from recipes where name='"+name_recipe+"'";
        ResultSet rs=Db.result(query);
        int id_recipe=rs.getInt(0);
        dbRecipeMealManager.removeRecipe(id_meal, id_recipe);
        ArrayList<Meal> m=dailyCount.getMeals();
        for(Meal meal:m){
            if(meal.getId()==id_meal){
                meal.removeRecipe(id_recipe);
            }
        }

    }
    public static void removeOneFood(int id_meal,String name_food) throws SQLException {
        String query = "Select id_food from foods where name='" + name_food + "'";
        ResultSet rs = Db.result(query);
        int id_food = rs.getInt(0);
        dbFoodsMealManager.removeFood(id_meal, id_food);
        ArrayList<Meal> m = dailyCount.getMeals();
        for (Meal meal : m) {
            if (meal.getId() == id_meal) {
                meal.removeFood(id_food);
            }
        }
    }
}
