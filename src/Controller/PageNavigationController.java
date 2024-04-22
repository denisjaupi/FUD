package Controller;

import View.Home;
import View.Profile;
import View.DailyTracker;
import View.DailyPlan;

import javax.swing.*;

public class PageNavigationController {
    private JFrame currentFrame;

    public PageNavigationController(JFrame currentFrame) {
        this.currentFrame = currentFrame;
    }

    public void navigateToProfile() {
        // Close the current window
        currentFrame.dispose();

        // Open the Profile window
        new Profile();
    }

    public void navigateToHome() {
        // Close the current window
        currentFrame.dispose();

        // Open the Home window
        new Home();
    }

    public void navigateToDailyTracker() {
        // Close the current window
        currentFrame.dispose();

        // Open the DailyTracker window
        new DailyTracker();
    }

    public void navigateToDailyPlan() {
        // Close the current window
        currentFrame.dispose();

        // Open the DailyPlan window
        new DailyPlan();
    }
}