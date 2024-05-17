package Model.Entities;

import java.util.ArrayList;



public class RecipeList {

    private String username;
    private ArrayList<Recipe> recipes;
    int num_prod;

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


    private void update_num(int control) {
        if (control == 1) {
            num_prod = num_prod + 1;
        } else if (control == 0) {
            num_prod = num_prod - 1;
        }
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        update_num(1);
    }

    public void removeRecipe(int id) {
        // Cerca il Recipe con l'ID specificato
        Recipe recipeToRemove = null;
        for (Recipe recipe : recipes) {
            if (recipe.getId() == id) {
                recipeToRemove = recipe;
                break; // Esci dal ciclo una volta trovato l'elemento
            }
        }
        // Rimuovi il Recipe dalla lista
        if (recipeToRemove != null) {
            recipes.remove(recipeToRemove);
            update_num(0);
        } else {
            System.out.println("Recipe non trovato con l'ID specificato.");
        }
    }

    public int getNum_prod() {
        return num_prod;
    }

    public void updateRecipe_list(int id_recipe, String name, String desc){
        for(Recipe r:recipes){
            if(r.getId()==id_recipe){
                r.setName(name);
                r.setDesc(desc);
            }
        }
    }

}


