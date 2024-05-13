package View;

import Controller.PageNavigationController;

import javax.swing.*;
import java.awt.*;

public class Home extends JFrame {

    public Home() {
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    private void setupWindow() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel labelPanel = createLabelPanel();
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(labelPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.WEST);
        return mainPanel;
    }

    private JPanel createLabelPanel() {
        JLabel label = new JLabel("Home", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);
        return labelPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);

        JToggleButton button1 = createButton("Home", buttonGroup, null);
        JToggleButton button2 = createButton("Profile", buttonGroup, pageNavigationController::navigateToProfile);
        JToggleButton button3 = createButton("Daily Plan", buttonGroup, pageNavigationController::navigateToDailyPlan);
        JToggleButton button4 = createButton("Daily Tracker", buttonGroup, pageNavigationController::navigateToDailyTracker);

        button1.setSelected(true);

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        return buttonPanel;
    }

    private JToggleButton createButton(String title, ButtonGroup buttonGroup, Runnable action) {
        JToggleButton button = new JToggleButton(title);
        buttonGroup.add(button);
        if (action != null) {
            button.addActionListener(e -> action.run());
        }
        return button;
    }
}