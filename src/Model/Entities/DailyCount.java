package Model.Entities;


import Model.Database.Db;

import java.util.ArrayList;

public class DailyCount {
    private ArrayList<Meal> meals;
    private ArrayList<Exercise> exercises;
    NutritionalInfo info;
    String username;
    private  int num_exe;
    private  int num_meal;

    public DailyCount(String userName) {
        this.username=userName;
        meals= new ArrayList<>();
        exercises= new ArrayList<>();
        num_exe=0;
        num_meal=0;
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
        updateNum_meal(1);
    }
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        updateNum_exe(1);

    }

    private void updateNum_exe(int control) {
        if (control == 1) {
            num_exe = num_exe+ 1;
        } else if (control == 0) {
            num_exe = num_exe - 1;
        }
    }
    private void updateNum_meal(int control) {
        if (control == 1) {
            num_meal= num_meal + 1;
        } else if (control == 0) {
            num_meal = num_meal - 1;
        }
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
        meals.removeIf(meal -> {
            if (meal.getId() == id_meal) {
                // Delete the foods and recipes within the meal
                meal.getFoodList().clear();
                meal.getRecipeList().clear();
                // Update the meal count
                updateNum_meal(0);
                return true;
            }
            return false;
        });
    }

    public void deleteExercise(int id) {
        exercises.removeIf(exercise -> exercise.getId() == id);
        updateNum_exe(0);
    }
}


