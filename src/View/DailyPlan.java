package View;

import Controller.PageNavigationController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class DailyPlan extends JFrame {

    private JTextField caloricIntakeField;

    public DailyPlan() {
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
        JPanel buttonPanel = createButtonPanel();
        JPanel centralPanel = createContentPanel();

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createContentPanel() {

        JPanel centralPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centralPanel.add(createTitlePanel(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 0;
        centralPanel.add(createOutputPanel(), gbc);

        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        centralPanel.add(createMacrosDistributionPanel(), gbc);

        gbc.gridy = 3;
        centralPanel.add(createMealDistributionPanel(), gbc);

        return centralPanel;

    }

    private JPanel createMacrosDistributionPanel() {
        JPanel macrosDistributionPanel = new JPanel(new GridLayout(4, 1));
        macrosDistributionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(0, 20, 10, 20)  // aggiunge uno spazio vuoto di 10 pixel
        ));

        JLabel label = new JLabel("Macros Distribution", SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        macrosDistributionPanel.add(label);


        JPanel macrosLabelsPanel = new JPanel(new GridLayout(1, 3));
        JLabel carbsLabel = new JLabel("Carbs", SwingConstants.CENTER);
        JLabel proteinsLabel = new JLabel("Proteins", SwingConstants.CENTER);
        JLabel fatsLabel = new JLabel("Fats", SwingConstants.CENTER);
        macrosLabelsPanel.add(carbsLabel);
        macrosLabelsPanel.add(proteinsLabel);
        macrosLabelsPanel.add(fatsLabel);
        macrosLabelsPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        macrosDistributionPanel.add(macrosLabelsPanel);



        JPanel macrosGraphPanel = new JPanel(new GridLayout(1, 1));
        macrosGraphPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        macrosDistributionPanel.add(macrosGraphPanel);


        JButton macrosDistributionButton = new JButton("Edit Macros Distribution");
        macrosDistributionButton.setPreferredSize(new Dimension(macrosDistributionButton.getPreferredSize().width, 10));
        macrosDistributionPanel.add(macrosDistributionButton, BorderLayout.SOUTH);

        return macrosDistributionPanel;
    }

    private JPanel createMealDistributionPanel() {
        JPanel mealDistributionPanel = new JPanel(new GridLayout(4, 1));
        mealDistributionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(0, 20, 10, 20)  // aggiunge uno spazio vuoto di 10 pixel
        ));

        JLabel label = new JLabel("Macros Distribution", SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mealDistributionPanel.add(label);


        JPanel macrosLabelsPanel = new JPanel(new GridLayout(1, 3));
        JLabel carbsLabel = new JLabel("Carbs", SwingConstants.CENTER);
        JLabel proteinsLabel = new JLabel("Proteins", SwingConstants.CENTER);
        JLabel fatsLabel = new JLabel("Fats", SwingConstants.CENTER);
        macrosLabelsPanel.add(carbsLabel);
        macrosLabelsPanel.add(proteinsLabel);
        macrosLabelsPanel.add(fatsLabel);
        macrosLabelsPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        mealDistributionPanel.add(macrosLabelsPanel);



        JPanel macrosGraphPanel = new JPanel(new GridLayout(1, 1));
        macrosGraphPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        mealDistributionPanel.add(macrosGraphPanel);


        JButton mealDistributionButton = new JButton("Edit Macros Distribution");
        mealDistributionButton.setPreferredSize(new Dimension(mealDistributionButton.getPreferredSize().width, 10));
        mealDistributionPanel.add(mealDistributionButton, BorderLayout.SOUTH);
        return mealDistributionPanel;
    }

    private JPanel createTitlePanel(){
        JLabel label = new JLabel("Daily Plan", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        return labelPanel;
    }




    private JPanel createOutputPanel() {
        JPanel outputPanel = new JPanel(new GridLayout(1, 1));

        // Imposta l'altezza massima del pannello
        outputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1000));

        // Assegna un bordo colorato al pannello
        outputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        outputPanel.add(createEditableOutputRow("Daily Caloric Intake"));

        return outputPanel;
    }

    private JPanel createEditableOutputRow(String labelText) {
        JPanel rowPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField();
        textField.setEditable(true);
        textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        JLabel kcalLabel = new JLabel("kcal");

        // Imposta la dimensione del font delle label
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20));
        kcalLabel.setFont(new Font(kcalLabel.getFont().getName(), Font.PLAIN, 20));

        // Aggiungi un bordo vuoto alla label "kcal" per distanziarla dalla row
        kcalLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        rowPanel.add(label, BorderLayout.NORTH);
        rowPanel.add(textField, BorderLayout.CENTER);
        rowPanel.add(kcalLabel, BorderLayout.EAST);

        // Assegna il campo di testo alla variabile di istanza
        if (labelText.equals("Daily Caloric Intake")) {
            caloricIntakeField = textField;
        }

        // Crea un DocumentFilter che accetta solo numeri fino a 5 cifre
        DocumentFilter onlyNumberFilter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (newStr.matches("\\d*") && newStr.length() <= 5) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newStr.matches("\\d*") && newStr.length() <= 5) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        // Ottieni il Document del campo di testo
        Document textFieldDoc = textField.getDocument();

        // Se il Document è un AbstractDocument, applica il filtro
        if (textFieldDoc instanceof AbstractDocument) {
            ((AbstractDocument) textFieldDoc).setDocumentFilter(onlyNumberFilter);
        }

        return rowPanel;
    }


    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);

        JToggleButton button1 = createButton("Home", buttonGroup, pageNavigationController::navigateToHome);
        JToggleButton button2 = createButton("Profile", buttonGroup, pageNavigationController::navigateToProfile);
        JToggleButton button3 = createButton("Daily Plan", buttonGroup, null);
        JToggleButton button4 = createButton("Daily Tracker", buttonGroup, pageNavigationController::navigateToDailyTracker);

        button3.setSelected(true);

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