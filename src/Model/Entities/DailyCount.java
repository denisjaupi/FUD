package Model.Entities;


import java.util.ArrayList;

public class DailyCount {
    private ArrayList<Meal> meals;
    private ArrayList<Exercise> exercises;
    NutritionalInfo info;

    public DailyCount() {
        meals= new ArrayList<>();
        exercises= new ArrayList<>();
    }
    public void addMeal(Meal meal) {
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
}


