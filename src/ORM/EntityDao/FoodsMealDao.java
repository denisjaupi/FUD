package ORM.EntityDao;

import java.sql.SQLException;
import java.util.ArrayList;
import Model.Entities.Food;

public class FoodsMealDao {

    public void addFoods(ArrayList<Food> foods, int mealKey) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder("INSERT INTO foodsmeal (food, meal, quantity_food) VALUES ");
        for (int i = 0; i < foods.size(); i++) {
            queryBuilder.append("(?, ?, ?)");
            if (i < foods.size() - 1) {
                queryBuilder.append(", ");
            }
        }

        String query = queryBuilder.toString();

        ArrayList<Object[]> paramsList = new ArrayList<>();
        for (Food f : foods) {
            paramsList.add(new Object[]{f.getId(), mealKey, f.getQuantity()});
        }

        ManagerDao.executeBatch(query, paramsList);
    }

    public void deleteAllFoods(int mealKey) throws SQLException {
        String query = "DELETE FROM foodsmeal WHERE meal = " + mealKey;
        ManagerDao.update(query);
    }

    public void removeFood(int mealKey, int foodKey) throws SQLException {
        String query = "DELETE FROM foodsmeal WHERE meal = " + mealKey + " AND food = " + foodKey;
        ManagerDao.update(query);
    }

    public void updateQuantity(int mealKey, int foodKey, int quantity) throws SQLException {
        String query = "UPDATE foodsmeal SET quantity_food = " + quantity + " WHERE meal = " + mealKey + " AND food = " + foodKey;
        ManagerDao.update(query);
    }
}

