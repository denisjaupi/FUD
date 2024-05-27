package View;

import Controller.Engine;
import Controller.PageNavigationController;
import Controller.dbExerciseManager;
import Model.Entities.Exercise;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddTrainingView extends JFrame {

    // Dichiaro i campi di testo come variabili di istanza
    private JTextField nameField;
    private JTextField intensityField;
    private JTextField caloriesField;
    private Engine engine;
    private Exercise exercise;


    public AddTrainingView(String name, String intensity, Engine e ) {
        engine=e;
        setupWindow();
        JPanel mainPanel = createMainPanel(name, intensity);
        add(mainPanel);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createMainPanel(String name, String intensity) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        try {
            exercise = new Exercise(engine.getDbExercise().selectId(name, intensity), name, engine.getDbExercise().selectMet(name, intensity));
            exercise.setIntensity(intensity);


            JPanel backButtonPanel = createBackButtonPanel();
            JPanel addButtonPanel = createAddButtonPanel();
            JPanel contentPanel = createContentPanel(name, intensity); // Passa i parametri al metodo createContentPanel

            mainPanel.add(backButtonPanel, BorderLayout.WEST);
            mainPanel.add(contentPanel, BorderLayout.CENTER);
            mainPanel.add(addButtonPanel, BorderLayout.EAST);
        }catch (SQLException e){
            System.err.println("Errore durante la creazione del pannello principale");
        }

        return mainPanel;
    }

    private JPanel createContentPanel(String name, String intensity) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createTitlePanel());
        contentPanel.add(createAddTablePanel(name, intensity)); // Passa i parametri al metodo createAddTablePanel

        return contentPanel;
    }
    ////////////////////////////////////////////////////////////////

    private JPanel createAddTablePanel(String name, String intensity) {

        JPanel fieldPanel = new JPanel(new GridLayout(12, 1));

        // Creare le etichette
        JLabel nameLabel = new JLabel("Name:");
        JLabel intensityLabel = new JLabel("Intensity:");

        // Creare i campi di testo
        nameField = new JTextField();
        nameField.setEditable(false);
        intensityField = new JTextField();
        intensityField.setEditable(false);


        // Imposta i valori dei campi di testo
        nameField.setText(name);
        intensityField.setText(intensity);

        // Aggiungere le etichette e i campi di testo al pannello
        fieldPanel.add(nameLabel);
        fieldPanel.add(nameField);
        fieldPanel.add(intensityLabel);
        fieldPanel.add(intensityField);

        //////////////////

        JPanel emptyPanel = new JPanel(new GridLayout(5,1));
        fieldPanel.add(emptyPanel);

        //////////////////

        JPanel selectPanel = new JPanel(new GridLayout(1, 4));
        JLabel durationLabel = new JLabel("Duration (minutes):");
        durationLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Creare uno spinner per la durata
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 300, 1); // Valore iniziale, minimo, massimo, passo
        JSpinner durationSpinner = new JSpinner(spinnerModel);

        ////////////////////////////////////////////////////////////////////////////////////////////

        // Imposta un JFormattedTextField come editor dello spinner
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(durationSpinner);
        durationSpinner.setEditor(editor);
        JFormattedTextField textField = editor.getTextField();

        // Imposta un InputVerifier personalizzato per limitare l'input a soli numeri <= 300
        textField.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                String text = ((JFormattedTextField) input).getText();
                try {
                    int value = Integer.parseInt(text);
                    return value >= 1 && value <= 300;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });

        // Ottieni il Document del campo di testo
        Document doc = textField.getDocument();

        ((AbstractDocument) doc).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                try {
                    int value = Integer.parseInt(string);
                    if (value >= 1 && value <= 300) {
                        super.replace(fb, offset, length, text, attrs); // Invoca il comportamento predefinito
                    }
                } catch (NumberFormatException e) {
                    if (text.isEmpty()) {
                        super.replace(fb, offset, length, text, attrs); // Invoca il comportamento predefinito
                    }
                }
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////

        selectPanel.add(durationLabel);
        selectPanel.add(durationSpinner);

        ////////////////////////////////////////////////////////////////////////////////////////////

        JLabel caloriesLabel = new JLabel("Calories:");
        caloriesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        caloriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        caloriesField = new JTextField();
        caloriesField.setEditable(false);
        caloriesField.setText(String.valueOf(exercise.getCalories()));

        // Aggiungi un ChangeListener allo spinner
        durationSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Ottieni il nuovo valore dello spinner
                int timeEx = (int)durationSpinner.getValue();

                // Aggiorna il tempo dell'esercizio
                exercise.setTime(timeEx);

                // Calcola le calorie bruciate
                if(engine.getUser().getPersonalData().getId() != 0)
                {
                    exercise.countBurnCalories(engine.getUser().getPersonalData().getWeight());
                }
                else
                {
                    System.out.println("Dati personali non inseriti");
                    JOptionPane.showMessageDialog(AddTrainingView.this, "Please insert personal data first", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Aggiorna il campo di testo delle calorie
                caloriesField.setText(String.valueOf(exercise.getCalories()));
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////

        selectPanel.add(caloriesLabel);
        selectPanel.add(caloriesField);

        fieldPanel.add(selectPanel);

        /////////////////////

        JPanel emptyPanel2 = new JPanel(new GridLayout(2,1));
        fieldPanel.add(emptyPanel2);

        /////////////////////

        return fieldPanel;
    }

    private JPanel createTitlePanel() {

        JLabel label = new JLabel("Training", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        return labelPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createAddButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);
        JToggleButton addFoodButton = createButton("Add", buttonGroup, () -> {
            try {
                engine.getDbActiv().addActivity(exercise.getId(), exercise.getCalories(), exercise.getTime());
                pageNavigationController.navigateToHome();
            } catch (SQLException e) {
                System.err.println("Errore durante l'aggiunta dell'attività: " + e.getMessage());
            }
        });

        buttonPanel.add(addFoodButton);

        return buttonPanel;
    }

    private JPanel createBackButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);
        JToggleButton backButton = createButton("Back", buttonGroup, pageNavigationController::navigateToTrainingTable);

        buttonPanel.add(backButton);

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

    private void setupWindow() {
        setSize(1000, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}