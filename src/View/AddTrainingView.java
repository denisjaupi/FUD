package View;

import Controller.PageNavigationController;
import Controller.dbExerciseManager;

import javax.swing.*;
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


    public AddTrainingView(String name, String intensity) {
        setupWindow();
        JPanel mainPanel = createMainPanel(name, intensity);
        add(mainPanel);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createMainPanel(String name, String intensity) {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel backButtonPanel = createBackButtonPanel();
        JPanel addButtonPanel = createAddButtonPanel();
        JPanel contentPanel = createContentPanel(name, intensity); // Passa i parametri al metodo createContentPanel

        mainPanel.add(backButtonPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(addButtonPanel, BorderLayout.EAST);

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

        //////////////////
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
        //////////////////

        selectPanel.add(durationLabel);
        selectPanel.add(durationSpinner);

        JLabel caloriesLabel = new JLabel("Calories:");
        caloriesLabel.setFont(new Font("Arial", Font.BOLD, 18));
        caloriesLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JTextField caloriesField = new JTextField();
        caloriesField.setEditable(false);

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

        JToggleButton addFoodButton = createButton("Add", buttonGroup, () -> {
            // Aggiungi il cibo al database

            // Dopo aver aggiunto il cibo, naviga alla pagina FoodsTable
            pageNavigationController.navigateToHome();
        });

        buttonPanel.add(addFoodButton);

        return buttonPanel;
    }

    private JPanel createBackButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);

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
