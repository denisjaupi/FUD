package Model.Entities;

import java.util.ArrayList;


public class Meal {
    private final int id;
    private int type;
    private ArrayList<Food> foodList;
    private ArrayList<Recipe> recipes;
    NutritionalInfo info;


    public Meal(int id, int t){
        this.id = id;
        type = t;
        foodList= new ArrayList<>();
        recipes= new ArrayList<>();
    }

    public int getId() {
        return id;
    }
    public void addFood(Food f){
        foodList.add(f);
    }
    public void addRecipe(Recipe r){
        recipes.add(r);
    }


    public void removeRecipe(int position){
        Recipe r=recipes.get(position);
        recipes.remove(position);
    }


    public void removeFood(int position){
        Food f=foodList.get(position);
        foodList.remove(f);
    }


    public ArrayList<Food> getFoodList(){  // TODO da capire se effettivamente serve
        return foodList;
    }


    public ArrayList<Recipe> getRecipes(){
        return recipes;
    }


    public void calculateTotalCalories(){
        for(Recipe r:recipes){
            this.info.calories +=r.info.calories;
            this.info.carbohydrates+= r.info.carbohydrates;
            this.info.fats+=r.info.fats;
            this.info.proteins+=r.info.proteins;
        }
        for(Food f:foodList){
            this.info.calories +=f.info.calories;
            this.info.carbohydrates+= f.info.carbohydrates;
            this.info.fats+=f.info.fats;
            this.info.proteins+=f.info.proteins;
        }
    }
}
