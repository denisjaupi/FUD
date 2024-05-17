package Controller;

import Model.Database.Db;
import Model.Entities.*;

import java.sql.*;
import java.util.ArrayList;

public class dbRecipeManager {

    private static User user;
    static RecipeList recipeList;
    static ArrayList<Meal> meal;
    static DailyCount dailyCount;



    public dbRecipeManager() {
        recipeList = user.getRecipe();
        dailyCount= user.getDailyCount();
        meal= dailyCount.getMeals();
    }

    public void setUser(User u) {
        user=u;
    }


    public User getUser() {
        return user;
    }

    public static ResultSet getRecipe() {
        return Db.result("SELECT name,description FROM recipes where id_user="+ (user.getId()));
    }

    public static ResultSet getRecipe_withId(int id) {
        return Db.result("SELECT name,description FROM recipes where id_recipe = " + id);
    }

    public static ResultSet getRecipe_withName(String name) {
        return Db.result("SELECT name,description FROM recipes where name = '" + name + "'");
    }

    public static void addRecipe_fromDb(String name, String desc, NutritionalInfo info, ArrayList<Food> foods){
        try {
            // Get the database connection
            Connection conn = Db.getConnection();

            int generatedKey = dbNutritionalInfoManager.addNutritionalInfo(info);
            if(generatedKey!=0) {

                PreparedStatement pstmt = conn.prepareStatement(
                        "INSERT INTO recipes (name, description, macro, id_user) VALUES (?,?,?,?)",
                        Statement.RETURN_GENERATED_KEYS
                );

                pstmt.setString(1, name);
                pstmt.setString(2, desc);
                pstmt.setLong(3, generatedKey);
                pstmt.setLong(4, user.getId());

                pstmt.executeUpdate();

                // Get the generated keys
                ResultSet rs1 = pstmt.getGeneratedKeys();
                if (rs1.next()) {
                    int recipeKey = rs1.getInt(1);

                    //insert ingredients in the db
                    dbIngredientsManager.addIngredients(foods, recipeKey);
                    Recipe r = new Recipe(recipeKey, name, desc);
                    for (Food f : foods) {
                        r.addIngredient(f);
                    }
                    r.setInfo(info);
                    recipeList.addRecipe(r);
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento del cibo nel database");
            e.printStackTrace();
        }
    }

    public static void removeRecipe_withId(int id) {

        try {
            // Get the database connection
            Connection conn = Db.getConnection();

            // Check if it is taken in other tables
            boolean present = false;
            String query = "SELECT COUNT(*) FROM recipemeal WHERE meal = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        present = count > 0;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (present) {
                System.out.println("Impossibile eliminare la ricetta. L'ID è presente in un pasto.");
            } else {


               dbIngredientsManager.deleteIngredients_withRecipe(id);


                // DELETE RECIPE'S MACRO
                ResultSet id_macro= Db.result("Select macro from recipes where id_recipe=" +id);
                int macroId = -1; // Valore predefinito nel caso in cui non sia trovato alcun risultato
                if (id_macro.next()) {
                    macroId = id_macro.getInt("macro");
                }

                dbNutritionalInfoManager.deleteNutritionalInfo(macroId);


                // DELETE RECIPE
                try (PreparedStatement deleteStmt = conn.prepareStatement(
                        "DELETE FROM recipes WHERE id_recipe = ?")) {

                    deleteStmt.setInt(1, id);

                    deleteStmt.executeUpdate();
                    recipeList.removeRecipe(id);
                    for (Meal m:meal){
                        m.removeRecipe(id);
                    }

                    System.out.println("Ricetta eliminata con successo.");

                } catch (SQLException e) {
                    System.out.println("Errore durante l'eliminazione della ricetta dal database");
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database");
            e.printStackTrace();
        }
    }

    public void removeOneFood(int id_recipe, String name_food) {
        dbIngredientsManager.deleteIngredients_withRecipeandName(id_recipe, name_food);
        for(Recipe r:recipeList.getRecipes()){
            if(r.getId()==id_recipe){
                r.deleteIngredients(name_food);
            }
        }
    }

    public void select()  {
        try {
            String query = "select * from recipes where id_user=" + user.getId();
            ResultSet rs = Db.result(query);
            if (rs.next()) {
                do {
                    Recipe r = selectRecipe(rs);
                    String query2 = "select * from ingredients where recipe=" + r.getId();
                    ResultSet rs_ingr = Db.result(query2);
                    String query3 = "select * from foods where id_food=" + rs_ingr.getInt("food");
                    ResultSet rs_food = Db.result(query3);
                    if (rs_food.next()) {
                        do {
                            Food f = dbFoodManager.selectFood(rs_food);
                            r.addIngredient(f);
                        } while (rs_food.next());
                    }
                    user.getRecipe().addRecipe(r);
                } while (rs.next());
            }
        }catch (SQLException e){
            System.err.println("Errore durante l'esecuzione della query nella select_recipe: " + e.getMessage());
        }
    }

    public static Recipe selectRecipe(ResultSet rs) throws SQLException {
        String name_recipe = rs.getString("name");
        String desc= rs.getString("description");
        int id_recipe= rs.getInt("id_recipe");
        String query1 = "select * from nutritionalinfo where id_macro=" + rs.getInt("macro");
        ResultSet rs3 = Db.result(query1);
        NutritionalInfo macro = new NutritionalInfo(rs3.getDouble("calories"), rs3.getDouble("proteins"), rs3.getDouble("fats"), rs3.getDouble("carbohydrates"));
        Recipe r= new Recipe(id_recipe, name_recipe, desc);
        r.setNutritionalInfo(macro);
        return r;
    }

    public void updateRecipe(int id, String name, String desc){
        String query="update recipes set name='"+name+"', description='"+desc+"' where id_recipe="+id;
        Db.result(query);
        user.getRecipe().updateRecipe_list(id, name, desc);
        user.getDailyCount().getMeals().forEach(m->m.updateRecipe_meal(id, name, desc));
    }

}


