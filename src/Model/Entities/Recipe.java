package Model.Entities;

import java.util.ArrayList;

public class Recipe {
    private final int id;
    private String name;
    private String description;
    private ArrayList<Food> ingredients;
    NutritionalInfo info;

    public Recipe(int id, String name, String description, ArrayList<Food> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = new ArrayList<>();
    }

    public void addIngredient(Food f) {
        this.ingredients.add(f);
    }
    public int getId() {
        return id;
    }
    public void countNutritionalInfo() {
        for (Food f : this.ingredients) {
            this.info.calories+= f.info.calories;
            this.info.carbohydrates+= f.info.carbohydrates;
            this.info.fats+= f.info.fats;
            this.info.proteins+= f.info.proteins;
        }
    }
}
