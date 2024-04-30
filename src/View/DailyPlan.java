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

    private void createOutputPanel(JPanel contentPanel) {
        JPanel outputPanel = new JPanel(new GridLayout(1, 1));

        // Imposta l'altezza massima del pannello
        outputPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1000));

        // Assegna un bordo colorato al pannello
        outputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        outputPanel.add(createEditableOutputRow("Daily Caloric Intake"));
        contentPanel.add(outputPanel, BorderLayout.NORTH);
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

    private void setupWindow() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Daily Plan", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);
        contentPanel.add(labelPanel, BorderLayout.NORTH);

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));

        createOutputPanel(centralPanel);

        // Aggiungi un componente invisibile per creare uno spazio verticale flessibile
        centralPanel.add(Box.createVerticalGlue());

        contentPanel.add(centralPanel, BorderLayout.CENTER);

        return contentPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = createButtonPanel();
        JPanel centralPanel = createContentPanel();

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(centralPanel, BorderLayout.CENTER);

        return mainPanel;
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