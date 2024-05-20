package Model.Entities;

import java.util.ArrayList;

public class Recipe {
    private final int id;
    private String name;
    private String description;
    private ArrayList<Food> ingredients;
    public NutritionalInfo info;
    private int num_prod_food;

    public Recipe(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public int getNum_prod_food() {
        return num_prod_food;
    }

    public void setNutritionalInfo(NutritionalInfo info) {
        this.info = info;
    }

    public void addIngredient(Food f) {
        this.ingredients.add(f);
        updateNum_food(1);
    }
    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String description) {
        this.description = description;
    }
    public String getName(){
        return name;
    }

    public void setInfo(NutritionalInfo info) {
        this.info = info;
    }

    public void countNutritionalInfo() {
        for (Food f : this.ingredients) {
            this.info.calories+= f.getNutritionalInfo().calories;
            this.info.carbohydrates+= f.getNutritionalInfo().carbohydrates;
            this.info.fats+= f.getNutritionalInfo().fats;
            this.info.proteins+= f.getNutritionalInfo().proteins;
        }
    }

    //this method must be used before to create a new recipe
    public void countNutritionalInfo_start(@org.jetbrains.annotations.NotNull ArrayList<Food> ingredients) {
        for (Food f : ingredients) {
            this.info.calories+= f.getNutritionalInfo().calories;
            this.info.carbohydrates+= f.getNutritionalInfo().carbohydrates;
            this.info.fats+= f.getNutritionalInfo().fats;
            this.info.proteins+= f.getNutritionalInfo().proteins;
        }
    }

    public String getDescription() {
        return description;
    }
    public void deleteIngredients(String name) {
        for (Food f : ingredients) {
            if (f.getName().equals(name)) {
                ingredients.remove(f);
                updateNum_food(0);
                break;
            }
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
        Recipe recipe = (Recipe) obj;
        return id == recipe.id;
    }

    public void updateQuantity(String name, int quantity) {
        for (Food f : ingredients) {
            if (f.getName().equals(name)) {
                f.setQuantity(quantity);
                break;
            }
        }
    }

    private void updateNum_food(int control) {
        if (control == 1) {
            num_prod_food = num_prod_food + 1;
        } else if (control == 0) {
            num_prod_food = num_prod_food - 1;
        }
    }
}
