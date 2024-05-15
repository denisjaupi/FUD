package Model.Entities;


import Model.Database.Db;

import java.util.ArrayList;

public class DailyCount {
    private ArrayList<Meal> meals;
    private ArrayList<Exercise> exercises;
    NutritionalInfo info;
    String username;

    public DailyCount(String userName) {
        this.username=userName;
        meals= new ArrayList<>();
        exercises= new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public ArrayList<Meal> getMeals() {
        return meals;
    }
    public ArrayList<Exercise> getExercises() {
        return exercises;
    }
    public  void addMeal(Meal meal) {
        meals.add(meal);
    }
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }
    public double calculateTotalCalories(int weight) {
        double totalCalories = 0.0;
        for (Meal meal : meals) {
            meal.calculateTotalCalories();
            this.info.calories += meal.info.calories;
            this.info.carbohydrates= meal.info.carbohydrates;
            this.info.proteins= meal.info.proteins;
            this.info.fats= meal.info.fats;
        }
        for(Exercise exercise : exercises) {
            exercise.countBurnCalories(weight);
            this.info.calories-= exercise.countBurnCalories(weight);
        }
        return totalCalories;
    }

    public void deleteMeal(int id_meal) {
        // Get the meal to be deleted
        int i = 0;
        Meal m = null;
        while (i < meals.size()) {
            if (meals.get(i).getId() == id_meal) {
                m= meals.get(i);
            }
            i++;
        }

        // Delete the foods and recipes within the meal
        assert m != null;
        m.getFoodList().clear();
        m.getRecipeList().clear();

        // Delete the meal from the daily count
        meals.remove(m);
    }
}


