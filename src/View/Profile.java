package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.stream.IntStream;
import java.text.DecimalFormat;

import Controller.Engine;
import Controller.PageNavigationController;
import Controller.dbUserManager;
import Model.Entities.User;
import Model.Util.CalculatedProfileData;
import Model.Entities.PersonalData;


public class Profile extends JFrame {

    private final JFormattedTextField heightField = new JFormattedTextField(NumberFormat.getNumberInstance());
    private final JFormattedTextField weightField = new JFormattedTextField(NumberFormat.getNumberInstance());
    private JComboBox<Integer> ageComboBox = new JComboBox<>();
    private JComboBox<String> genderComboBox = new JComboBox<>();
    private JComboBox<String> activityLevelComboBox = new JComboBox<>();
    private JComboBox<String> goalComboBox = new JComboBox<>();
    private final JTextField mealCountField = new JTextField(4);

    private JTextField bmrField;
    private JTextField bmiField;
    private JTextField waterRequirementField;
    private JTextField caloricIntakeField;

    private Engine engine = new Engine();
    private User currentUser = new User();

    public Profile(Engine engine) {
        this.engine = engine;

        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);

        currentUser = this.engine.getUser();
        // Recupera i dati personali dell'utente
        PersonalData personalData = currentUser.getPersonalData();
        if (personalData != null) {
            // Imposta i valori nei campi del profilo
            ageComboBox.setSelectedItem(personalData.getAge());
            weightField.setText(String.valueOf(personalData.getWeight()));
            heightField.setText(String.valueOf(personalData.getHeight()));
            genderComboBox.setSelectedItem(personalData.getGender());
            activityLevelComboBox.setSelectedItem(personalData.getActivity());
            goalComboBox.setSelectedItem(personalData.getGoal());
            if (personalData.getCount_meal() != 0)
                mealCountField.setText(String.valueOf(personalData.getMealCount()));
        }


        addDocumentListeners();
        elaborateInputValues();

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
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);
        JToggleButton button1 = createButton("Home", buttonGroup, pageNavigationController::navigateToHome);
        JToggleButton button2 = createButton("Profile", buttonGroup, pageNavigationController::navigateToProfile);
        JToggleButton button3 = createButton("Daily Plan", buttonGroup, pageNavigationController::navigateToDailyPlan);
        JToggleButton button4 = createButton("Daily Tracker", buttonGroup, pageNavigationController::navigateToDailyTracker);

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
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                // Autentifica l'utente con il database
                engine.logout();
                // Dopo aver aggiunto il cibo, naviga alla pagina FoodsTable
                pageNavigationController.navigateToLogin();
            }
        });

        JToggleButton deleteButton = createButton("Delete Account", buttonGroup, () -> {
            int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete your account?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.YES_OPTION) {
                try {
                    dbUserManager.deleteUser(dbUserManager.getUser().getId());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                pageNavigationController.navigateToLogin();
            }
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

        JPanel centralPanel = new JPanel(new GridLayout(2, 1)); // 2 rows, 1 column

        createInputPanel(centralPanel);
        createSelectionPanel(centralPanel);

        contentPanel.add(centralPanel, BorderLayout.CENTER);

        createOutputPanel(contentPanel);

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
        ItemListener itemListener = new ItemListener() {
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
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            int age = (int) ageComboBox.getSelectedItem();
            String gender = genderComboBox.getSelectedItem().toString();
            String activityLevel = activityLevelComboBox.getSelectedItem().toString();
            String goal = goalComboBox.getSelectedItem().toString();

            // Crea un'istanza di ProfileData
            PersonalData personalData = new PersonalData(height, weight, age, gender, activityLevel, goal);

            // Ottieni i valori calcolati
            CalculatedProfileData calculatedProfileData = personalData.getCalculatedProfileData();

            //Numero di valori decimali dopo la virgola
            DecimalFormat df = new DecimalFormat("#.###");

            // Assegna i valori calcolati ai campi di testo corrispondenti
            bmrField.setText(df.format(calculatedProfileData.getBmr()));
            bmiField.setText(df.format(calculatedProfileData.getBmi()));
            waterRequirementField.setText(df.format(calculatedProfileData.getWaterRequirement()));
            caloricIntakeField.setText(df.format(calculatedProfileData.getCaloricIntake()));

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
        textField.setFont(new Font("Arial", Font.PLAIN, 30));
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
        JPanel inputPanel = new JPanel();
        inputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Aggiungi i pannelli esistenti
        JPanel heightPanel = new JPanel();
        heightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        JLabel heightLabel = new JLabel("Height: ");
        JLabel cmLabel = new JLabel("cm");
        heightField.setPreferredSize(new Dimension(80, 20)); // Imposta la dimensione preferita del campo di testo
        heightPanel.add(heightLabel);
        heightPanel.add(heightField);
        heightPanel.add(cmLabel);
        inputPanel.add(heightPanel);

        JPanel weightPanel = new JPanel();
        weightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        JLabel weightLabel = new JLabel("Weight: ");
        JLabel kgLabel = new JLabel("kg");
        weightField.setPreferredSize(new Dimension(80, 20)); // Imposta la dimensione preferita del campo di testo
        weightPanel.add(weightLabel);
        weightPanel.add(weightField);
        weightPanel.add(kgLabel);
        inputPanel.add(weightPanel);

        // Aggiungi i pannelli per age e gender
        JPanel agePanel = new JPanel();
        agePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        JLabel ageLabel = new JLabel("Age: ");
        Integer[] ages = IntStream.rangeClosed(13, 99).boxed().toArray(Integer[]::new);
        ageComboBox = new JComboBox<>(ages);
        agePanel.add(ageLabel);
        agePanel.add(ageComboBox);
        inputPanel.add(agePanel);

        JPanel genderPanel = new JPanel();
        genderPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        JLabel genderLabel = new JLabel("Gender: ");
        genderComboBox = new JComboBox<>(new String[]{"M", "F"});
        genderPanel.add(genderLabel);
        genderPanel.add(genderComboBox);
        inputPanel.add(genderPanel);

        contentPanel.add(inputPanel, BorderLayout.NORTH);


        // Crea un DocumentFilter che accetta solo numeri per i campi di alezza e peso
        DocumentFilter onlyNumberFilter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("\\d*") || string.isEmpty() || string.matches("\\d+\\.\\d*")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("\\d*") || text.isEmpty() || text.matches("\\d+\\.\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
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
        JPanel selectionPanel = new JPanel(new BorderLayout());
        selectionPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel inputPanel = new JPanel();

        JPanel activityLevelPanel = new JPanel();
        JLabel activityLevelLabel = new JLabel("Activity Level: ");
        activityLevelComboBox = new JComboBox<>(new String[]{"Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Super Active"});
        activityLevelPanel.add(activityLevelLabel);
        activityLevelPanel.add(activityLevelComboBox);
        inputPanel.add(activityLevelPanel);

        JPanel goalPanel = new JPanel();
        JLabel goalLabel = new JLabel("Goal: ");
        goalComboBox = new JComboBox<>(new String[]{"Weight Loss", "Slight Weight Loss", "Maintain Weight", "Slight Weight Gain", "Weight Gain"});
        goalPanel.add(goalLabel);
        goalPanel.add(goalComboBox);
        inputPanel.add(goalPanel);

        JPanel mealCountPanel = new JPanel();
        JLabel mealCountLabel = new JLabel("Meal Count: ");
        mealCountPanel.add(mealCountLabel);
        mealCountPanel.add(mealCountField);
        inputPanel.add(mealCountPanel);

        selectionPanel.add(inputPanel, BorderLayout.CENTER);

        // Crea un nuovo pannello per il pulsante "Save"
        JPanel saveButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            // Recupera i valori dai campi di input
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            int age = (int) ageComboBox.getSelectedItem();
            String gender = genderComboBox.getSelectedItem().toString();
            String activityLevel = activityLevelComboBox.getSelectedItem().toString();
            String goal = goalComboBox.getSelectedItem().toString();
            int mealCount = Integer.parseInt(mealCountField.getText());

            // Crea un nuovo oggetto PersonalData con i valori recuperati
            PersonalData personalData = new PersonalData(height, weight, age, gender, activityLevel, goal);
            personalData.setMealCount(mealCount);

            // Salva l'oggetto PersonalData
            engine.addPersonalData(personalData);
        });
        saveButtonPanel.add(saveButton);
        selectionPanel.add(saveButtonPanel, BorderLayout.SOUTH);

        contentPanel.add(selectionPanel, BorderLayout.CENTER);
    }
}