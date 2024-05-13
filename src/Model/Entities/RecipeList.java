package Model.Entities;

import java.util.ArrayList;

public class RecipeList {

    private String username;
    private ArrayList<Recipe> recipes;

    public RecipeList(String username) {
        this.username = username;
        recipes = new ArrayList<>();
    }
    public String getUsername() {
        return username;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }
    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }

    public void removeRecipe(int position) {
        Recipe r = recipes.get(position);
        recipes.remove(position);
    }
}
