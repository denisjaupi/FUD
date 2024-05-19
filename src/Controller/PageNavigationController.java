package Controller;

import View.*;

import javax.swing.*;
import java.awt.*;

public class PageNavigationController {
    private JFrame currentFrame;
    private Dimension frameSize;
    private Point frameLocation;;

    public PageNavigationController(JFrame currentFrame) {
        this.currentFrame = currentFrame;
        this.frameSize = currentFrame.getSize(); // get the size of the current frame
        this.frameLocation = currentFrame.getLocation(); // get the location of the current frame

    }

    public void navigateToProfile() {
        // Store the size and location of the current window
        frameSize = currentFrame.getSize();
        frameLocation = currentFrame.getLocation();

        // Close the current window
        currentFrame.dispose();

        // Open the Profile window
        Profile profile = new Profile();
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
        Home home = new Home();
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
        DailyTracker dailyTracker = new DailyTracker();
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
        DailyPlan dailyPlan = new DailyPlan();
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
        FoodsTable foodTable = new FoodsTable();
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
        RecipesTable recipesTable = new RecipesTable();
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
        TrainingTable trainingTable = new TrainingTable();
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
        AddFoodView addFoodView = new AddFoodView();
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
        AddRecipeView addRecipeView = new AddRecipeView();
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
        AddTrainingView addTrainingView = new AddTrainingView(name, intensity);
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
        LoginView loginView = new LoginView();
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
        RegisterView registerView = new RegisterView();
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
        TypeOfDietTable typeOfDietTable = new TypeOfDietTable();
        typeOfDietTable.setSize(frameSize); // set the size of the new window
        typeOfDietTable.setLocation(frameLocation); // set the location of the new window
    }
}