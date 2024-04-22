package View;

import Controller.PageNavigationController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DailyTracker extends JFrame {
    public DailyTracker() {
        // Set the title of the window
        setTitle("FUD - Daily Tracker");

        // Set the window to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel with a grid layout
        JPanel panel = new JPanel(new GridLayout(4, 1));

        // Create the buttons
        JToggleButton button1 = new JToggleButton("Home");
        JToggleButton button2 = new JToggleButton("Profile");
        JToggleButton button3 = new JToggleButton("Daily Plan");
        JToggleButton button4 = new JToggleButton("Daily Tracker");

        // Create a button group
        ButtonGroup buttonGroup = new ButtonGroup();

        // Add the buttons to the button group
        buttonGroup.add(button1);
        buttonGroup.add(button2);
        buttonGroup.add(button3);
        buttonGroup.add(button4);

        // Set the Daily Tracker button as selected
        button4.setSelected(true);

        // Create a page navigation controller
        PageNavigationController pageNavigationController = new PageNavigationController(this);

        // Add action listeners to the buttons
        button1.addActionListener(e -> pageNavigationController.navigateToHome());
        button2.addActionListener(e -> pageNavigationController.navigateToProfile());
        button3.addActionListener(e -> pageNavigationController.navigateToDailyPlan());

        // Add the buttons to the panel
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(button4);

        // Add the panel to the frame
        add(panel, BorderLayout.WEST);

        // Make the window visible
        setVisible(true);
    }
}