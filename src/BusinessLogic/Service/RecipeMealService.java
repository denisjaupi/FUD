package BusinessLogic.Service;


import ORM.EntityDao.RecipeDao;
import ORM.EntityDao.RecipeMealDao;
import Model.Entities.Recipe;

import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeMealService {

    private RecipeMealDao recipeMealDao;

    public RecipeMealService(RecipeMealDao recipeMealDao){
        this.recipeMealDao=recipeMealDao;
    }

    public void addRecipes(ArrayList<Recipe> recipes, int mealKey) {
        try {
            recipeMealDao.addRecipes(recipes, mealKey);
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento delle ricette nel database");
            e.printStackTrace();
        }
    }

    public void deleteAllRecipes(int mealKey) {
        try {
            recipeMealDao.deleteAllRecipes(mealKey);
        } catch (SQLException e) {
            System.out.println("Errore durante l'eliminazione delle ricette dal database");
            e.printStackTrace();
        }
    }

    public void removeRecipe(int mealKey, int recipeKey) {
        try {
            recipeMealDao.removeRecipe(mealKey, recipeKey);
        } catch (SQLException e) {
            System.out.println("Errore durante la rimozione della ricetta dal database");
            e.printStackTrace();
        }
    }
}
