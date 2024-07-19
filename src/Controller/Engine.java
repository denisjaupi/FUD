package Controller;

import BusinessLogic.Service.*;
import Model.Entities.PersonalData;
import Model.Entities.User;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Engine {
    private static Engine istance;
    private User user;
    private ServiceFactory sf;
    private PersonalData personalData;

    private Engine() {
        sf =  ServiceFactory.getInstance();
    }
    public User getUser() {
        return user;
    }
    public static Engine getInstance() {
        if(istance==null)
            istance = new Engine();
        return istance;
    }
    public boolean login(String email, String password) {
        try {
            UserService us = (UserService) sf.getService("UserService");
            PersonalDataService personalDataService=(PersonalDataService) sf.getService("PersonalDataService");
            ActivityService acS=(ActivityService) sf.getService("ActivityService");
            RecipeService recipeService= (RecipeService) sf.getService("RecipeService");
            MealService mealService= (MealService) sf.getService("MealService");
            DietService dietService=(DietService) sf.getService("DietService");
            if(us.checkCredentials(email, password)){
                us.login(email, password);

                user = us.getCurrentUser();
                acS.setUser(user);
                acS.selectActivities();
                recipeService.setUser(user);
                mealService.setUser(user);
                dietService.setUser(user);

                dietService.select();
                recipeService.loadUserRecipes();
                personalDataService.setUser();

                return true;
            }else {
                System.out.println("Errore: Credenziali non valide.");
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean register(String email, String psw, String userName){
        try{
            UserService us = (UserService) sf.getService("UserService");
            if(!us.checkCredentials(email, psw)){
                if(!us.checkUserName(userName)) {
                    us.register(email, psw, userName);
                    return true;
                }
                else
                    return false;
            }else{
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int selectId_exercise(String name, String intensity){
        ExerciseService es = (ExerciseService) sf.getService("ExerciseService");
        return es.selectId(name, intensity);
    }

    public double selectMet_exercise(String name, String intensity){
        ExerciseService es = (ExerciseService) sf.getService("ExerciseService");
        return es.selectMet(name, intensity);
    }

    public boolean addActivity(int id, double calories, float time) throws SQLException {
        ActivityService as = (ActivityService) sf.getService("ActivityService");
        return as.addActivity(id, calories, time);
    }

    public void addFood_db(String name, double calories, double proteins, double carbohydrates, double fats){
        FoodService fs = (FoodService) sf.getService("FoodService");
        fs.addFood_Db(name, calories, proteins, carbohydrates, fats);

    }
    public int selectId_food(String name){
        FoodService fs = (FoodService) sf.getService("FoodService");
        return fs.selectId(name);
    }
    public ResultSet getAll_food(){
        FoodService fs = (FoodService) sf.getService("FoodService");
        return fs.getFood();
    }

    public void deleteUser(int id_user) throws SQLException {
        UserService us = (UserService) sf.getService("UserService");
        us.deleteUser(id_user);
    }

    public void addPersonalData(float height, float weight, int age, String gender, String activityLevel, String goal, int mealCount) throws SQLException {
        PersonalDataService ps=(PersonalDataService) sf.getService("PersonalDataService");
        ps.addOrUpdatePersonalData(age, weight, height, gender, activityLevel, goal, mealCount);
    }

    public void logout() {
        user=null;
    }
    public ResultSet getAll_exercise(){
        ExerciseService es= (ExerciseService)  sf.getService("ExerciseService");
        return es.getExercise();
    }
}
