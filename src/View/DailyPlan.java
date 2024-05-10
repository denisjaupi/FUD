package View;

import Controller.PageNavigationController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


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
        JPanel macrosDistributionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        macrosDistributionPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel label = new JLabel("Macros Distribution", SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        macrosDistributionPanel.add(label, gbc);

        //////////////////////////////////////////////////////////////////////////

        JPanel macrosLabelsPanel = new JPanel(new GridLayout(1, 3));
        macrosLabelsPanel.setPreferredSize(new Dimension(macrosDistributionPanel.getPreferredSize().width, 40));

        // Crea i campi di testo
        JTextField carbsOutput = new JTextField();
        JTextField proteinsOutput = new JTextField();
        JTextField fatsOutput = new JTextField();

        // Imposta i campi di testo come non modificabili
        carbsOutput.setEditable(false);
        proteinsOutput.setEditable(false);
        fatsOutput.setEditable(false);

        // Crea i pannelli per le etichette e i campi di testo
        JPanel carbsPanel = new JPanel(new BorderLayout());
        JPanel proteinsPanel = new JPanel(new BorderLayout());
        JPanel fatsPanel = new JPanel(new BorderLayout());

        // Aggiungi un bordo vuoto a ciascun pannello per creare più spazio tra di loro
        carbsPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 120));
        proteinsPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 100));
        fatsPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 0, 120));


        // Aggiungi le etichette e i campi di testo ai pannelli
        carbsPanel.add(new JLabel("Carbs:", SwingConstants.CENTER), BorderLayout.WEST);
        carbsPanel.add(carbsOutput, BorderLayout.CENTER);
        carbsPanel.add(new JLabel("g", SwingConstants.CENTER), BorderLayout.EAST);
        proteinsPanel.add(new JLabel("Proteins:", SwingConstants.CENTER), BorderLayout.WEST);
        proteinsPanel.add(proteinsOutput, BorderLayout.CENTER);
        proteinsPanel.add(new JLabel("g", SwingConstants.CENTER), BorderLayout.EAST);
        fatsPanel.add(new JLabel("Fats:", SwingConstants.CENTER), BorderLayout.WEST);
        fatsPanel.add(fatsOutput, BorderLayout.CENTER);
        fatsPanel.add(new JLabel("g", SwingConstants.CENTER), BorderLayout.EAST);


        // Aggiungi i pannelli al pannello delle etichette
        macrosLabelsPanel.add(carbsPanel);
        macrosLabelsPanel.add(proteinsPanel);
        macrosLabelsPanel.add(fatsPanel);

        gbc.gridy = 1;
        macrosDistributionPanel.add(macrosLabelsPanel, gbc);

        //////////////////////////////////////////////////////////////////////////

        JPanel singlePanel = new JPanel(new GridLayout(1, 1));
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        // Crea un dataset per il grafico a torta
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Carbs", 50); // sostituisci con i valori reali
        dataset.setValue("Proteins", 30); // sostituisci con i valori reali
        dataset.setValue("Fats", 20); // sostituisci con i valori reali

        // Crea il grafico a torta
        JFreeChart pieChart = ChartFactory.createPieChart(null, dataset, false, true, false);

        // Imposta lo sfondo del grafico come trasparente
        pieChart.setBackgroundPaint(null);
        PiePlot plot = (PiePlot) pieChart.getPlot();
        // Imposta il generatore di etichette per mostrare le percentuali
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {2}"));
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);
        // Cambia i colori delle fette
        Color darkBlue = new Color(0, 0, 122);
        Color mediumBlue = new Color(0, 0, 255);
        Color lightBlue = new Color(173, 216, 230);
        plot.setSectionPaint("Carbs", lightBlue);
        plot.setSectionPaint("Proteins", mediumBlue);
        plot.setSectionPaint("Fats", darkBlue);

        // Crea un pannello per il grafico e aggiungilo al pannello principale
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setOpaque(false);
        singlePanel.add(chartPanel);

        macrosDistributionPanel.add(singlePanel, gbc);

        //////////////////////////////////////////////////////////////////////////

        JButton macrosDistributionButton = new JButton("Edit Macros Distribution");
        macrosDistributionButton.setPreferredSize(new Dimension(macrosDistributionButton.getPreferredSize().width, 100));
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        macrosDistributionPanel.add(macrosDistributionButton, gbc);

        return macrosDistributionPanel;
    }

    private JPanel createMealDistributionPanel() {
        JPanel mealDistributionPanel = new JPanel(new GridLayout(4, 1));
        mealDistributionPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(0, 20, 10, 20)
        ));

        JLabel label = new JLabel("Meal Distribution", SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        mealDistributionPanel.add(label);


        JPanel macrosLabelsPanel = new JPanel(new GridLayout(1, 3));
        macrosLabelsPanel.setPreferredSize(new Dimension(mealDistributionPanel.getPreferredSize().width, 40));
        JLabel carbsLabel = new JLabel("Carbs", SwingConstants.CENTER);
        JLabel proteinsLabel = new JLabel("Proteins", SwingConstants.CENTER);
        JLabel fatsLabel = new JLabel("Fats", SwingConstants.CENTER);
        macrosLabelsPanel.add(carbsLabel);
        macrosLabelsPanel.add(proteinsLabel);
        macrosLabelsPanel.add(fatsLabel);
        macrosLabelsPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        mealDistributionPanel.add(macrosLabelsPanel);


        JPanel macrosGraphPanel = new JPanel(new GridLayout(1, 1));
        macrosGraphPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        mealDistributionPanel.add(macrosGraphPanel);


        JButton mealDistributionButton = new JButton("Edit Macros Distribution");
        mealDistributionButton.setPreferredSize(new Dimension(mealDistributionButton.getPreferredSize().width, 40));
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
        // Crea il pannello principale con un GridBagLayout
        JPanel outputPanel = new JPanel(new GridBagLayout());
        outputPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Crea il GridBagConstraints per gestire il layout
        GridBagConstraints gbc = new GridBagConstraints();

        // Crea e configura l'etichetta "Daily Caloric Intake"
        JLabel dailyIntakeLabel = new JLabel("Daily Caloric Intake");
        dailyIntakeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        dailyIntakeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Aggiungi l'etichetta al pannello principale
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.02; // Imposta il peso per la prima colonna
        gbc.fill = GridBagConstraints.BOTH;
        outputPanel.add(dailyIntakeLabel, gbc);

        // Crea e configura il pannello contenente il JTextField e l'etichetta "kcal"
        JPanel rowPanel = new JPanel(new BorderLayout());
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(true);
        textField.setFont(new Font(textField.getFont().getName(), Font.BOLD, textField.getFont().getSize()));
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 30)); // Imposta l'altezza a 30
        JLabel kcalLabel = new JLabel("kcal");
        kcalLabel.setFont(new Font(kcalLabel.getFont().getName(), Font.PLAIN, 20));
        kcalLabel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        textField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        rowPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 50));
        rowPanel.add(textField, BorderLayout.CENTER);
        rowPanel.add(kcalLabel, BorderLayout.EAST);

        // Aggiungi il pannello al pannello principale
        gbc.gridx = 1;
        gbc.weightx = 0.98; // Imposta il peso per la seconda colonna
        outputPanel.add(rowPanel, gbc);

        // Assegna il campo di testo alla variabile di istanza
        caloricIntakeField = textField;

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

        return outputPanel;
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