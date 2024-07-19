package ORM.EntityDao;

import Model.Entities.Food;

import java.sql.*;
import java.util.ArrayList;

public class IngredientsDao {

    public void addIngredients(ArrayList<Food> foods, int id_recipe) throws SQLException {
        for (Food food : foods) {
            int foodId = getFood_idFromName(food.getName());
            if (foodId != -1) {
                insertMeal_foodRecord(id_recipe, foodId, food.getQuantity());
            } else {
                System.out.println("Errore: impossibile trovare l'id del cibo con il nome " + food.getName());
            }
        }
    }

    private int getFood_idFromName(String foodName) throws SQLException {
        String query = "SELECT id_food FROM foods WHERE name = '" + foodName + "'";
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            return rs.getInt("id_food");
        }
        return -1;
    }

    private void insertMeal_foodRecord(int id_recipe, int id_food, int quantity) throws SQLException {
        String query = "INSERT INTO ingredients (recipe, food, quantity) VALUES (" + id_recipe + ", " + id_food + ", " + quantity + ")";
        ManagerDao.result(query);
    }

    public void deleteIngredients_withRecipe(int id_recipe) throws SQLException {
        String query = "DELETE FROM ingredients WHERE recipe = " + id_recipe;
        ManagerDao.result(query);
    }

    public ResultSet getIngredients_withRecipe(int id_recipe) throws SQLException {
        String query = "SELECT F.name, I.quantity FROM ingredients I LEFT JOIN foods F ON I.food = F.id_food WHERE I.recipe = " + id_recipe;
        return ManagerDao.select(query);
    }

    public void deleteIngredients_withRecipeandName(int id_recipe, String name) throws SQLException {
        String query = "DELETE FROM ingredients WHERE recipe = " + id_recipe + " AND food = (SELECT id_food FROM foods WHERE name = '" + name + "')";
        ManagerDao.result(query);
    }

    public void updateQuantity(int id_recipe, String name, int quantity) throws SQLException {
        String query = "UPDATE ingredients SET quantity = " + quantity + " WHERE recipe = " + id_recipe + " AND food = (SELECT id_food FROM foods WHERE name = '" + name + "')";
        ManagerDao.update(query);
    }
}
