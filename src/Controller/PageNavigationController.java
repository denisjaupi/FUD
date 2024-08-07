package Controller;
import View.*;


import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class PageNavigationController {
    static PageNavigationController istance;
    private JFrame currentFrame;
    private Dimension frameSize;
    private Point frameLocation;
    private Engine engine;

    public void setEngine(Engine engine){
        this.engine = engine;
    }

    public Engine getEngine(){
        return engine;
    }

    private PageNavigationController(JFrame currentFrame) {
        this.currentFrame = currentFrame;
        this.frameSize = currentFrame.getSize(); // get the size of the current frame
        this.frameLocation = currentFrame.getLocation();
        this.engine=Engine.getInstance();
    }

    public static PageNavigationController getIstance(JFrame currentFrame){
        if(istance==null){
            istance =new PageNavigationController(currentFrame);
        }
        return istance;
    }

    public void navigateToProfile() {
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the Profile window
        Profile profile = new Profile(engine);
        profile.setSize(frameSize); // set the size of the new window
        profile.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToHome() {
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the Home window
        Home home = new Home(engine);
        home.setSize(frameSize); // set the size of the new window
        home.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToDailyTracker() {
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyTracker window
        DailyTracker dailyTracker = new DailyTracker(engine);
        dailyTracker.setSize(frameSize); // set the size of the new window
        dailyTracker.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToDailyPlan() {
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        DailyPlan dailyPlan = new DailyPlan(engine);
        dailyPlan.setSize(frameSize); // set the size of the new window
        dailyPlan.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToFoodTable(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        FoodsTable foodTable = new FoodsTable(engine);
        foodTable.setSize(frameSize); // set the size of the new window
        foodTable.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToRecipesTable(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        RecipesTable recipesTable = new RecipesTable(engine);
        recipesTable.setSize(frameSize); // set the size of the new window
        recipesTable.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToTrainingTable(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        TrainingTable trainingTable = new TrainingTable(engine);
        trainingTable.setSize(frameSize); // set the size of the new window
        trainingTable.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToAddFood(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        AddFoodView addFoodView = new AddFoodView(engine);
        addFoodView.setSize(frameSize); // set the size of the new window
        addFoodView.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToAddRecipe(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        AddRecipeView addRecipeView = new AddRecipeView(engine);
        addRecipeView.setSize(frameSize); // set the size of the new window
        addRecipeView.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToAddTraining(String name, String intensity){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        AddTrainingView addTrainingView = null;
        try {
            addTrainingView = new AddTrainingView(name, intensity, engine);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        addTrainingView.setSize(frameSize); // set the size of the new window
        addTrainingView.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToLogin(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        LoginView loginView = new LoginView(engine);
        loginView.setSize(frameSize); // set the size of the new window
        loginView.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToRegister(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        RegisterView registerView = new RegisterView(engine);
        registerView.setSize(frameSize); // set the size of the new window
        registerView.setLocation(frameLocation); // set the location of the new window
    }

    public void navigateToTypeOfDietTable(){
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        TypeOfDietTable typeOfDietTable = new TypeOfDietTable(engine);
        typeOfDietTable.setSize(frameSize); // set the size of the new window
        typeOfDietTable.setLocation(frameLocation); // set the location of the new window
    }
}