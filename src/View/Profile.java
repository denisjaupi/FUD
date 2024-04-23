package View;

import Controller.PageNavigationController;
import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;
import java.awt.*;
import java.util.stream.IntStream;

public class Profile extends JFrame {
    public Profile() {
        // Window settings
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));

        // Main panel with a box layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));

        // Button panel on the west
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel);

        // Panel for the rest of the content
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Label "Profile" at the top
        JLabel label = new JLabel("Profile", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);
        contentPanel.add(labelPanel, BorderLayout.NORTH);

        // Central panel with BoxLayout for input and selection panels
        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS)); // Change GridLayout to BoxLayout

        // Add input and selection panels to centralPanel
        createInputPanel(centralPanel);
        createSelectionPanel(centralPanel);
        createOutputPanel(centralPanel);

        // Add the central panel to the content panel
        contentPanel.add(centralPanel, BorderLayout.CENTER);

        // Add the content panel to the main panel
        mainPanel.add(contentPanel);

        // Add the main panel to the frame and make it visible
        add(mainPanel);
        setVisible(true);
    }

    private void createOutputPanel(JPanel contentPanel) {
        JPanel outputPanel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column

        // Add your components to outputPanel
        // For example, let's add four labels
        outputPanel.add(createOutputRow("Basal Metabolic Rate (BMR)"));
        outputPanel.add(createOutputRow("Body Mass Index (BMI)"));
        outputPanel.add(createOutputRow("Water Requirement "));
        outputPanel.add(createOutputRow("Daily Caloric Intake"));

        contentPanel.add(outputPanel, BorderLayout.SOUTH);
    }

    private JPanel createOutputRow(String labelText) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        textField.setEditable(false); // The text field should not be editable
        rowPanel.add(label, BorderLayout.NORTH);
        rowPanel.add(textField, BorderLayout.CENTER);
        return rowPanel;
    }

    private void createInputPanel(JPanel contentPanel) {
        JPanel inputPanel = new JPanel(new GridLayout(1, 4));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel heightPanel = new JPanel();
        JLabel heightLabel = new JLabel("Height: ");
        JTextField heightField = new JTextField(10);
        JLabel cmLabel = new JLabel("cm");
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);
        heightPanel.add(cmLabel);
        inputPanel.add(heightPanel);

        JPanel weightPanel = new JPanel();
        JLabel weightLabel = new JLabel("Weight: ");
        JTextField weightField = new JTextField(10);
        JLabel kgLabel = new JLabel("kg");
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        weightPanel.add(kgLabel);
        inputPanel.add(weightPanel);

        JPanel agePanel = new JPanel();
        JLabel ageLabel = new JLabel("Age: ");
        Integer[] ages = IntStream.rangeClosed(13, 99).boxed().toArray(Integer[]::new);
        JComboBox<Integer> ageComboBox = new JComboBox<>(ages);
        agePanel.add(ageLabel);
        agePanel.add(ageComboBox);
        inputPanel.add(agePanel);

        JPanel genderPanel = new JPanel();
        JLabel genderLabel = new JLabel("Gender: ");
        JComboBox<String> genderComboBox = new JComboBox<>(new String[] {"M", "F"});
        genderPanel.add(genderLabel);
        genderPanel.add(genderComboBox);
        inputPanel.add(genderPanel);

        contentPanel.add(inputPanel, BorderLayout.CENTER);
    }

    private void createSelectionPanel(JPanel contentPanel) {
        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
        selectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel activityLevelPanel = new JPanel();
        JLabel activityLevelLabel = new JLabel("Activity Level: ");
        JComboBox<String> activityLevelComboBox = new JComboBox<>(new String[] {"Low", "Moderate", "High", "Very High", "Hyper Active"});
        activityLevelPanel.add(activityLevelLabel);
        activityLevelPanel.add(activityLevelComboBox);
        selectionPanel.add(activityLevelPanel);

        JPanel goalPanel = new JPanel();
        JLabel goalLabel = new JLabel("Goal: ");
        JComboBox<String> goalComboBox = new JComboBox<>(new String[] {"Weight Loss", "Slight Weight Loss", "Maintain Weight", "Slight Weight Gain", "Weight Gain"});
        goalPanel.add(goalLabel);
        goalPanel.add(goalComboBox);
        selectionPanel.add(goalPanel);

        contentPanel.add(selectionPanel, BorderLayout.CENTER);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        ButtonGroup buttonGroup = new ButtonGroup();

        JToggleButton button1 = createButton("Home", buttonGroup);
        JToggleButton button2 = createButton("Profile", buttonGroup);
        JToggleButton button3 = createButton("Daily Plan", buttonGroup);
        JToggleButton button4 = createButton("Daily Tracker", buttonGroup);

        button2.setSelected(true);

        buttonPanel.add(button1);
        buttonPanel.add(button2);
        buttonPanel.add(button3);
        buttonPanel.add(button4);

        return buttonPanel;
    }

    private JToggleButton createButton(String title, ButtonGroup buttonGroup) {
        JToggleButton button = new JToggleButton(title);
        buttonGroup.add(button);
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        button.addActionListener(e -> {
            switch (title) {
                case "Home":
                    pageNavigationController.navigateToHome();
                    break;
                case "Profile":
                    pageNavigationController.navigateToProfile();
                    break;
                case "Daily Plan":
                    pageNavigationController.navigateToDailyPlan();
                    break;
                case "Daily Tracker":
                    pageNavigationController.navigateToDailyTracker();
                    break;
            }
        });
        return button;
    }
}