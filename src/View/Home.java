package View;

import Controller.PageNavigationController;
import Model.ProfileData;
import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {

    public Home() {

        // Set the window to full screen
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the minimum size of the window
        setMinimumSize(new Dimension(800, 600));

        // Create a main panel with a border layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a label with the text "Home"
        JLabel label = new JLabel("Home", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));

        // Create a panel with a flow layout for the label
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        // Create a panel with a grid layout for the buttons
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));

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

        // Set the Home button as selected
        button1.setSelected(true);

        // Create a new PageNavigationController
        PageNavigationController pageNavigationController = new PageNavigationController(this);

        // Add action listeners to the buttons
        button2.addActionListener(e -> pageNavigationController.navigateToProfile());
        button3.addActionListener(e -> pageNavigationController.navigateToDailyPlan());
        button4.addActionListener(e -> pageNavigationController.navigateToDailyTracker());

        // Add the buttons to the button panel
        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        // Add the label panel and the button panel to the main panel
        mainPanel.add(labelPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.WEST);

        // Add the main panel to the frame
        add(mainPanel);

        // Make the window visible
        setVisible(true);
    }
}