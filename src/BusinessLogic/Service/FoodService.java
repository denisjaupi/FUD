package BusinessLogic.Service;


import Model.Entities.Food;
import Model.Entities.NutritionalInfo;
import ORM.EntityDao.FoodDao;

import java.sql.*;

public class FoodService{

    private static FoodDao foodDao;
    private NutritionalInfoService nutritionalInfoService;

    public FoodService(FoodDao foodDao, NutritionalInfoService nis) {
        this.foodDao = foodDao;
        this.nutritionalInfoService=nis;
    }

    public ResultSet getFood() {
        try {
            return foodDao.getAllFoods();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Food getFood_withId(int id) {
        try {
            ResultSet rs= foodDao.getFoodById(id);
            if(rs.next()){
                return selectFood(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public Food getFood_withName(String name) {
        try {
            ResultSet rs=foodDao.getFoodByName(name);
            if(rs.next()){
                return selectFood(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    public  void addFood_Db(String name, double calories, double proteins, double carbohydrates, double fats) {
        try {
            Food f=new Food(name, new NutritionalInfo(calories, proteins, carbohydrates, fats));
            int generatedKey = nutritionalInfoService.addNutritionalInfo(f.getNutritionalInfo());
            if (generatedKey != 0) {
                foodDao.addFood(f.getName(), generatedKey);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  int selectId(String name) {
        try {
            return foodDao.getFoodIdByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void remove_food_withId(int id) {
        try {
            ResultSet rs = foodDao.getFoodById(id);
            if (rs.next()) {
                int macroId = rs.getInt("macro");
                foodDao.removeFoodById(id);
                if (macroId != 0) {
                    nutritionalInfoService.deleteNutritionalInfo(macroId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void remove_food_withName(String name) {
        try {
            foodDao.removeFoodByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Food selectFood(ResultSet rs) throws SQLException {
        String nameFood = rs.getString("name");
        ResultSet rsMacro = foodDao.getNutritionalInfoByMacroId(rs.getInt("macro"));
        NutritionalInfo macro = new NutritionalInfo(rsMacro.getDouble("calories"), rsMacro.getDouble("proteins"), rsMacro.getDouble("fats"), rsMacro.getDouble("carbohydrates"));
        Food food = new Food(nameFood, macro);
        food.setId(rs.getInt("id_food"));
        food.setQuantity(rs.getInt("quantity"));
        return food;
    }
}

