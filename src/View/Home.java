package View;

import Controller.Engine;
import Controller.PageNavigationController;
import Model.Entities.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private Engine engine= new Engine();


    public Home(Engine e) {
        setupWindow();
        engine=e;
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);

    }

    ////////////////////////////////////////////////////////////////

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = createButtonPanel();
        JPanel contentPanel = createContentPanel();

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createTitlePanel());
        contentPanel.add(createDataPanel());
        contentPanel.add(createProgressPanel());
        contentPanel.add(createDbButtonsPanel());
        contentPanel.add(createLogPanel());

        return contentPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createLogPanel() {
        JPanel logPanel = new JPanel(new GridLayout(1, 2));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);
        logPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crea il primo pulsante
        JToggleButton logFudButton = createButton("LOG FUD", buttonGroup, pageNavigationController::navigateToDailyTracker);
        logFudButton.setPreferredSize(new Dimension(logFudButton.getPreferredSize().width, 60));

        // Crea il secondo pulsante
        JToggleButton addTrainingButton = createButton("ADD TRAINING", buttonGroup, pageNavigationController::navigateToTrainingTable);
        addTrainingButton.setPreferredSize(new Dimension(addTrainingButton.getPreferredSize().width, 60));

        // Aggiungi i pulsanti al pannello
        logPanel.add(logFudButton);
        logPanel.add(addTrainingButton);

        return logPanel;
    }

    private JPanel createDbButtonsPanel() {
        JPanel dbButtonsPanel = new JPanel(new GridLayout(2, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);
        dbButtonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Crea il primo pulsante
        JToggleButton myFudButton = createButton("FUD", buttonGroup, pageNavigationController::navigateToFoodTable);
        myFudButton.setPreferredSize(new Dimension(myFudButton.getPreferredSize().width, 50));


        // Crea il secondo pulsante
        JToggleButton myRecipesButton = createButton("My Recipes", buttonGroup, pageNavigationController::navigateToRecipesTable);
        myRecipesButton.setPreferredSize(new Dimension(myRecipesButton.getPreferredSize().width, 50));

        // Aggiungi i pulsanti al pannello
        dbButtonsPanel.add(myFudButton);
        dbButtonsPanel.add(myRecipesButton);

        return dbButtonsPanel;
    }

    private JPanel createProgressPanel() {

        // Crea il nuovo pannello da aggiungere in basso
        JPanel progressPanel = new JPanel(new GridLayout(2, 1));
        progressPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));


        // Crea il pannello per le calorie
        JPanel progressCaloriesPanel = new JPanel(new GridLayout(2, 1));
        progressCaloriesPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));

        // Crea la progress bar
        JProgressBar progressBar = new JProgressBar();
        progressBar.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        progressBar.setValue(50); // Imposta il valore iniziale a 50

        JLabel caloriesLabel = new JLabel("CALORIES", SwingConstants.CENTER);

        progressCaloriesPanel.add(caloriesLabel);
        progressCaloriesPanel.add(progressBar);

        // Crea il pannello per i macro
        JPanel progressMacrosPanel = new JPanel(new GridLayout(2, 3));

        // Crea le progress bar
        JProgressBar progressBar1 = new JProgressBar();
        progressBar1.setValue(50); // Imposta il valore iniziale a 50
        progressBar1.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Aggiungi 5px di spazio a destra e sinistra

        JProgressBar progressBar2 = new JProgressBar();
        progressBar2.setValue(50); // Imposta il valore iniziale a 50
        progressBar2.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Aggiungi 5px di spazio a destra e sinistra

        JProgressBar progressBar3 = new JProgressBar();
        progressBar3.setValue(50); // Imposta il valore iniziale a 50
        progressBar3.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // Aggiungi 5px di spazio a destra e sinistra



        JLabel label1 = new JLabel("Carbs", SwingConstants.CENTER);
        JLabel label2 = new JLabel("Proteins", SwingConstants.CENTER);
        JLabel label3 = new JLabel("Fats", SwingConstants.CENTER);

        // Imposta i colori della progress bar
        Color lightBlue = new Color(173, 216, 230);
        Color mediumBlue = new Color(100, 149, 237);
        Color darkBlue = new Color(70, 130, 180);

        // Aggiungi la progress bar al bottomPanel
        progressMacrosPanel.add(progressBar1);
        progressMacrosPanel.add(progressBar2);
        progressMacrosPanel.add(progressBar3);
        progressMacrosPanel.add(label1);
        progressMacrosPanel.add(label2);
        progressMacrosPanel.add(label3);

        // Aggiungi i pannelli al panel
        progressPanel.add(progressCaloriesPanel);
        progressPanel.add(progressMacrosPanel);

        return progressPanel;
    }

    private JPanel createDataPanel() {
        JPanel dataPanel = new JPanel(new BorderLayout());
        // Imposta l'altezza preferita del dataPanel
        dataPanel.setPreferredSize(new Dimension(dataPanel.getPreferredSize().width, 100));

        JPanel centerPanel = new JPanel(new GridBagLayout());

        // Crea il JTextField per le calorie giornaliere
        JTextField dailyCaloricIntakeField = createCenteredNonEditableTextField();
        dailyCaloricIntakeField.setFont(new Font(dailyCaloricIntakeField.getFont().getName(), Font.BOLD, 56));

        if (engine.getUser().getPersonalData().getId()!=0) {

            int dailyCaloricIntakeInt = (int) engine.getUser().getPersonalData().getCalculatedProfileData().getCaloricIntake();
            // Impostazione del valore nel JTextField
            dailyCaloricIntakeField.setText(String.valueOf(dailyCaloricIntakeInt));

        }


        // Crea il JTextField per le calorie bruciate
        JLabel burnedCaloriesLabel = new JLabel("Burned");
        JTextField burnedCaloriesField = createCenteredNonEditableTextField();
        burnedCaloriesField.setFont(new Font(dailyCaloricIntakeField.getFont().getName(), Font.BOLD, 42));

        if (engine.getUser().getPersonalData().getId()!=0 && engine.getUser().getDailyCount()!= null) {
            double weight= engine.getUser().getPersonalData().getWeight();
            int burnedCaloriesInt = (int) engine.getUser().getDailyCount().calculteBurnedCalories(weight);
            // Impostazione del valore nel JTextField
            burnedCaloriesField.setText(String.valueOf(burnedCaloriesInt));

        }
        // Crea il JTextField per le calorie rimanenti
        JLabel remainingCaloriesLabel = new JLabel("Eaten");
        JTextField remainingCaloriesField = createCenteredNonEditableTextField();
        remainingCaloriesField.setFont(new Font(dailyCaloricIntakeField.getFont().getName(), Font.BOLD, 42));

        // Crea le restrizioni per il layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE; // Non riempire lo spazio orizzontale e verticale
        gbc.anchor = GridBagConstraints.NORTH; // Posiziona il componente in alto

        // Aggiungi il JTextField e l'etichetta per le calorie rimanenti al pannello
        gbc.gridx = 0;
        centerPanel.add(remainingCaloriesField, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // Riduci lo spazio sopra all'etichetta
        centerPanel.add(remainingCaloriesLabel, gbc);

        // Aggiungi il JTextField per le calorie giornaliere al pannello
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0); // Rimuovi lo spazio sopra al JTextField
        centerPanel.add(dailyCaloricIntakeField, gbc);

        // Aggiungi il JTextField e l'etichetta per le calorie bruciate al pannello
        gbc.gridx = 2;
        gbc.gridy = 0;
        centerPanel.add(burnedCaloriesField, gbc);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0); // Riduci lo spazio sopra all'etichetta
        centerPanel.add(burnedCaloriesLabel, gbc);

        dataPanel.add(centerPanel, BorderLayout.SOUTH);

        return dataPanel;
    }

    private JPanel createTitlePanel() {
        JLabel label = new JLabel("Home", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JPanel labelPanel = new JPanel(new FlowLayout());

        labelPanel.add(label);
        return labelPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JTextField createCenteredNonEditableTextField() {
        JTextField textField = new JTextField(5); // Imposta la larghezza a 5 colonne
        textField.setEditable(false); // Rendi il campo non editabile
        textField.setHorizontalAlignment(JTextField.CENTER); // Allinea il testo al centro
        return textField;
    }

    private void setupWindow() {
        setSize(1000, 600); // Imposta le dimensioni iniziali della finestra
        setResizable(false); // Impedisce il ridimensionamento della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);
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