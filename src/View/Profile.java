package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.stream.IntStream;

import Controller.Engine;
import Controller.PageNavigationController;
import Controller.dbUserManager;
import Model.Util.CalculatedProfileData;
import Model.Entities.PersonalData;


public class Profile extends JFrame {

    private JTextField heightField;
    private JTextField weightField;
    private JComboBox<Integer> ageComboBox;
    private JComboBox<String> genderComboBox;
    private JComboBox<String> activityLevelComboBox;
    private JComboBox<String> goalComboBox;

    private JTextField bmrField;
    private JTextField bmiField;
    private JTextField waterRequirementField;
    private JTextField caloricIntakeField;

    private Engine engine;

    public Profile(Engine engine) {
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
        addDocumentListeners();
        this.engine = engine;
    }

    private void setupWindow() {
        setSize(1000, 600); // Imposta le dimensioni iniziali della finestra
        setResizable(false); // Impedisce il ridimensionamento della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel logoutButtonPanel = createLogoutButtonPanel();
        JPanel buttonPanel = createButtonPanel();
        JPanel contentPanel = createContentPanel();

        mainPanel.add(logoutButtonPanel, BorderLayout.EAST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.WEST);

        return mainPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        ButtonGroup buttonGroup = new ButtonGroup();

        JToggleButton button1 = createButton("Home", buttonGroup, () -> new PageNavigationController(this).navigateToHome());
        JToggleButton button2 = createButton("Profile", buttonGroup, () -> new PageNavigationController(this).navigateToProfile());
        JToggleButton button3 = createButton("Daily Plan", buttonGroup, () -> new PageNavigationController(this).navigateToDailyPlan());
        JToggleButton button4 = createButton("Daily Tracker", buttonGroup, () -> new PageNavigationController(this).navigateToDailyTracker());

        button2.setSelected(true);

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

    ////////////////////////////////////////////////////////////////

    private JPanel createLogoutButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);

        JToggleButton logoutButton = createButton("Logout", buttonGroup, () -> {
            // Autentifica l'utente con il database

            // Dopo aver aggiunto il cibo, naviga alla pagina FoodsTable
            pageNavigationController.navigateToLogin();
        });

        JToggleButton deleteButton = createButton("Delete Account", buttonGroup, () -> {
            try {
                dbUserManager.deleteUser(dbUserManager.getUser().getId());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            pageNavigationController.navigateToLogin();
        });

        buttonPanel.add(logoutButton);
        buttonPanel.add(deleteButton);

        return buttonPanel;
    }


    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Profile", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        contentPanel.add(labelPanel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));

        createInputPanel(centralPanel);
        createSelectionPanel(centralPanel);
        createOutputPanel(centralPanel);

        contentPanel.add(centralPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    private void addDocumentListeners() {
        // Create a DocumentListener
        DocumentListener documentListener = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                checkAndEditValues();
            }
            public void removeUpdate(DocumentEvent e) {
                checkAndEditValues();
            }
            public void insertUpdate(DocumentEvent e) {
                checkAndEditValues();
            }
        };

        // Add the DocumentListener to each JTextField
        heightField.getDocument().addDocumentListener(documentListener);
        weightField.getDocument().addDocumentListener(documentListener);

        // Create an ItemListener
        ItemListener itemListener = new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    checkAndEditValues();
                }
            }
        };

        // Add the ItemListener to each JComboBox
        ageComboBox.addItemListener(itemListener);
        genderComboBox.addItemListener(itemListener);
        activityLevelComboBox.addItemListener(itemListener);
        goalComboBox.addItemListener(itemListener);

    }

    private void checkAndEditValues() {
        if (!heightField.getText().isEmpty() && !weightField.getText().isEmpty() &&
                ageComboBox.getSelectedItem() != null && genderComboBox.getSelectedItem() != null &&
                activityLevelComboBox.getSelectedItem() != null && goalComboBox.getSelectedItem() != null) {
            elaborateInputValues();
        }
    }

    private void elaborateInputValues() {
        if (!heightField.getText().isEmpty() && !weightField.getText().isEmpty() &&
                ageComboBox.getSelectedItem() != null && genderComboBox.getSelectedItem() != null &&
                activityLevelComboBox.getSelectedItem() != null && goalComboBox.getSelectedItem() != null) {

            // Ottieni i valori dai campi di input
            int height = Integer.parseInt(heightField.getText());
            int weight = Integer.parseInt(weightField.getText());
            int age = (int) ageComboBox.getSelectedItem();
            String gender = genderComboBox.getSelectedItem().toString();
            String activityLevel = activityLevelComboBox.getSelectedItem().toString();
            String goal = goalComboBox.getSelectedItem().toString();

            // Crea un'istanza di ProfileData
            PersonalData personalData = new PersonalData(height, weight, age, gender, activityLevel, goal);

            // Ottieni i valori calcolati
            CalculatedProfileData calculatedProfileData = personalData.getCalculatedProfileData();

            // Assegna i valori calcolati ai campi di testo corrispondenti
            bmrField.setText(String.valueOf(calculatedProfileData.getBmr()));
            bmiField.setText(String.valueOf(calculatedProfileData.getBmi()));
            waterRequirementField.setText(String.valueOf(calculatedProfileData.getWaterRequirement()));
            caloricIntakeField.setText(String.valueOf(calculatedProfileData.getCaloricIntake()));

        }
    }

    private void createOutputPanel(JPanel contentPanel) {
        JPanel outputPanel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column

        // For example, let's add four labels
        outputPanel.add(createOutputRow("Basal Metabolic Rate (BMR)"));
        outputPanel.add(createOutputRow("Body Mass Index (BMI)"));
        outputPanel.add(createOutputRow("Water Requirement (liters)")); // Ensure the label text matches exactly
        outputPanel.add(createOutputRow("Daily Caloric Intake (kcal)")); // Ensure the label text matches exactly

        contentPanel.add(outputPanel, BorderLayout.SOUTH);
    }

    private JPanel createOutputRow(String labelText) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        textField.setEditable(false);
        rowPanel.add(label, BorderLayout.NORTH);
        rowPanel.add(textField, BorderLayout.CENTER);

        // Assegna i campi di testo alle variabili di istanza (Ensure the label text matches exactly)
        switch (labelText) {
            case "Basal Metabolic Rate (BMR)":
                bmrField = textField;
                break;
            case "Body Mass Index (BMI)":
                bmiField = textField;
                break;
            case "Water Requirement (liters)":
                waterRequirementField = textField;
                break;
            case "Daily Caloric Intake (kcal)":
                caloricIntakeField = textField;
                break;
        }

        return rowPanel;
    }

    private void createInputPanel(JPanel contentPanel) {

        JPanel inputPanel = new JPanel(new GridLayout(1, 4));
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel heightPanel = new JPanel();
        JLabel heightLabel = new JLabel("Height: ");
        heightField = new JTextField(6);
        JLabel cmLabel = new JLabel("cm");
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);
        heightPanel.add(cmLabel);
        inputPanel.add(heightPanel);

        JPanel weightPanel = new JPanel();
        JLabel weightLabel = new JLabel("Weight: ");
        weightField = new JTextField(6);
        JLabel kgLabel = new JLabel("kg");
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        weightPanel.add(kgLabel);
        inputPanel.add(weightPanel);

        JPanel agePanel = new JPanel();
        JLabel ageLabel = new JLabel("Age: ");
        Integer[] ages = IntStream.rangeClosed(13, 99).boxed().toArray(Integer[]::new);
        ageComboBox = new JComboBox<>(ages);
        agePanel.add(ageLabel);
        agePanel.add(ageComboBox);
        inputPanel.add(agePanel);

        JPanel genderPanel = new JPanel();
        JLabel genderLabel = new JLabel("Gender: ");
        genderComboBox = new JComboBox<>(new String[] {"M", "F"});
        genderPanel.add(genderLabel);
        genderPanel.add(genderComboBox);
        inputPanel.add(genderPanel);

        contentPanel.add(inputPanel, BorderLayout.CENTER);

        // Crea un DocumentFilter che accetta solo numeri per i campi di alezza e peso
        DocumentFilter onlyNumberFilter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) {
                if (string.matches("\\d*")) {
                    try {
                        super.insertString(fb, offset, string, attr);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) {
                if (text.matches("\\d*")) {
                    try {
                        super.replace(fb, offset, length, text, attrs);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // Ottieni il Document dei campi di testo
        Document heightFieldDoc = heightField.getDocument();
        Document weightFieldDoc = weightField.getDocument();

        // Se il Document è un AbstractDocument, applica il filtro
        if (heightFieldDoc instanceof AbstractDocument) {
            ((AbstractDocument) heightFieldDoc).setDocumentFilter(onlyNumberFilter);
        }
        if (weightFieldDoc instanceof AbstractDocument) {
            ((AbstractDocument) weightFieldDoc).setDocumentFilter(onlyNumberFilter);
        }
    }

    private void createSelectionPanel(JPanel contentPanel) {
        JPanel selectionPanel = new JPanel(new GridLayout(1, 2));
        selectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel activityLevelPanel = new JPanel();
        JLabel activityLevelLabel = new JLabel("Activity Level: ");
        activityLevelComboBox = new JComboBox<>(new String[] {"Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Super Active"});
        activityLevelPanel.add(activityLevelLabel);
        activityLevelPanel.add(activityLevelComboBox);
        selectionPanel.add(activityLevelPanel);

        JPanel goalPanel = new JPanel();
        JLabel goalLabel = new JLabel("Goal: ");
        goalComboBox = new JComboBox<>(new String[]{"Weight Loss", "Slight Weight Loss", "Maintain Weight", "Slight Weight Gain", "Weight Gain"});
        goalPanel.add(goalLabel);
        goalPanel.add(goalComboBox);
        selectionPanel.add(goalPanel);

        contentPanel.add(selectionPanel, BorderLayout.CENTER);
    }

}