package Model.Entities;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class Meal {
    private final int id;
    private String type;
    private ArrayList<Food> foodList;
    private ArrayList<Recipe> recipeList;
    NutritionalInfo info;
    private  int num_prod_recipe;
    private  int num_prod_food;


    public Meal(int id, String t){
        this.id = id;
        type = t;
        foodList= new ArrayList<>();
        recipeList = new ArrayList<>();
    }

    public int getId() {
        return id;
    }


    public void addFood(Food f){
        foodList.add(f);
        updateNum_food(1);
    }


    public void addRecipe(Recipe r){
        recipeList.add(r);
        updateNum_recipe(1);
    }

    public void removeFood(int id){
        Food foodToRemove = null;
        for (Food food : foodList) {
            if (food.getId() == id) {
                foodToRemove = food;
                break; // Esci dal ciclo una volta trovato l'elemento
            }
        }
        // Rimuovi il Recipe dalla lista
        if (foodToRemove != null) {
            recipeList.remove(foodToRemove);
            updateNum_food(0);
        } else {
            System.out.println("Food non trovato con l'ID specificato.");
        }
    }


    private void updateNum_food(int control) {
        if (control == 1) {
            num_prod_food = num_prod_food + 1;
        } else if (control == 0) {
            num_prod_food = num_prod_food - 1;
        }
    }
    private void updateNum_recipe(int control) {
        if (control == 1) {
            num_prod_recipe= num_prod_recipe + 1;
        } else if (control == 0) {
            num_prod_recipe = num_prod_recipe - 1;
        }
    }

    public void removeRecipe(int id) {
        // Cerca il Recipe con l'ID specificato
        Recipe recipeToRemove = null;
        for (Recipe recipe : recipeList) {
            if (recipe.getId() == id) {
                recipeToRemove = recipe;
                break; // Esci dal ciclo una volta trovato l'elemento
            }
        }
        // Rimuovi il Recipe dalla lista
        if (recipeToRemove != null) {
            recipeList.remove(recipeToRemove);
            updateNum_recipe(0);
        } else {
            System.out.println("Recipe non trovato con l'ID specificato.");
        }
    }

    public int getNum_prodFood() {
        return num_prod_food;
    }
    public int getNum_prodRecipe() {
        return num_prod_recipe;
    }


    public ArrayList<Food> getFoodList(){  // TODO da capire se effettivamente serve
        return foodList;
    }


    public ArrayList<Recipe> getRecipeList(){
        return recipeList;
    }

    public void setInfo(NutritionalInfo info) {
        this.info = info;
    }

    public void calculateTotalCalories(){
        for(Recipe r: recipeList){
            this.info.calories +=r.info.calories;
            this.info.carbohydrates+= r.info.carbohydrates;
            this.info.fats+=r.info.fats;
            this.info.proteins+=r.info.proteins;
        }
        for(Food f:foodList){
            this.info.calories +=f.getNutritionalInfo().calories;
            this.info.carbohydrates+= f.getNutritionalInfo().carbohydrates;
            this.info.fats+=f.getNutritionalInfo().fats;
            this.info.proteins+=f.getNutritionalInfo().proteins;
        }
    }


    //this method must be used before to create a new meal

    public void calculateTotalCalories_start(ArrayList<Food>  foods, @NotNull ArrayList<Recipe> recipes){
        for(Recipe r: recipes){
            this.info.calories +=r.info.calories;
            this.info.carbohydrates+= r.info.carbohydrates;
            this.info.fats+=r.info.fats;
            this.info.proteins+=r.info.proteins;
        }
        for(Food f:foods){
            this.info.calories +=f.getNutritionalInfo().calories;
            this.info.carbohydrates+= f.getNutritionalInfo().carbohydrates;
            this.info.fats+=f.getNutritionalInfo().fats;
            this.info.proteins+=f.getNutritionalInfo().proteins;
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Meal meal = (Meal) obj;
        return id == meal.id;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipeList;
    }
    public ArrayList<Food> getFoods() {
        return foodList;
    }
}
