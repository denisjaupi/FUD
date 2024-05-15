package Controller;
import View.Home;
import View.Profile;
import View.DailyTracker;
import View.DailyPlan;

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
}