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
import org.jfree.chart.plot.RingPlot;
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
        setSize(1000, 600); // Imposta le dimensioni iniziali della finestra
        setResizable(false); // Impedisce il ridimensionamento della finestra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = createButtonPanel();
        JPanel contentPanel = createContentPanel();

        mainPanel.add(buttonPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createContentPanel() {

        JPanel centralPanel = new JPanel();
        centralPanel.setLayout(new BoxLayout(centralPanel, BoxLayout.Y_AXIS));

        centralPanel.add(createTitlePanel());
        centralPanel.add(createOutputPanel());

        JPanel macrosDistributionPanel = createMacrosDistributionPanel();
        macrosDistributionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) (getHeight() * 0.7)));
        centralPanel.add(macrosDistributionPanel);

        JPanel mealDistributionPanel = createMealDistributionPanel();
        mealDistributionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, (int) (getHeight() * 0.3)));
        centralPanel.add(mealDistributionPanel);

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

        // Crea il pannello principale con un BoxLayout
        JPanel macrosLabelsPanel = new JPanel();
        macrosLabelsPanel.setLayout(new BoxLayout(macrosLabelsPanel, BoxLayout.X_AXIS));

        // Crea i campi di testo
        JTextField carbsOutput = new JTextField(10);
        JTextField proteinsOutput = new JTextField(10);
        JTextField fatsOutput = new JTextField(10);

        // Imposta i campi di testo come non modificabili
        carbsOutput.setEditable(false);
        proteinsOutput.setEditable(false);
        fatsOutput.setEditable(false);

        // Crea i pannelli per le etichette e i campi di testo con un FlowLayout con gap ridotto
        JPanel carbsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        JPanel proteinsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        JPanel fatsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));

        // Aggiungi le etichette e i campi di testo ai pannelli
        carbsPanel.add(new JLabel("Carbs:"));
        carbsPanel.add(carbsOutput);
        carbsPanel.add(new JLabel("g"));
        proteinsPanel.add(new JLabel("Proteins:"));
        proteinsPanel.add(proteinsOutput);
        proteinsPanel.add(new JLabel("g"));
        fatsPanel.add(new JLabel("Fats:"));
        fatsPanel.add(fatsOutput);
        fatsPanel.add(new JLabel("g"));

        // Aggiungi del filler e i pannelli al pannello principale
        macrosLabelsPanel.add(Box.createHorizontalGlue()); // Aggiungi del filler
        macrosLabelsPanel.add(carbsPanel);
        macrosLabelsPanel.add(Box.createHorizontalStrut(15)); // Crea uno spazio di 15 pixel
        macrosLabelsPanel.add(proteinsPanel);
        macrosLabelsPanel.add(Box.createHorizontalStrut(15)); // Crea uno spazio di 15 pixel
        macrosLabelsPanel.add(fatsPanel);
        macrosLabelsPanel.add(Box.createHorizontalGlue());

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
        JFreeChart pieChart = ChartFactory.createRingChart(null, dataset, false, true, false);

        // Imposta lo sfondo del grafico come trasparente
        pieChart.setBackgroundPaint(null);
        RingPlot plot = (RingPlot) pieChart.getPlot();

        // Imposta il generatore di etichette per mostrare le percentuali
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {2}"));
        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(null);
        plot.setBackgroundPaint(null);
        plot.setOutlineVisible(false);

        // Cambia i colori delle fette
        Color darkBlue = new Color(70, 130, 180); // SteelBlue
        Color mediumBlue = new Color(100, 149, 237); // CornflowerBlue
        Color lightBlue = new Color(173, 216, 230); // LightBlue
        plot.setSectionPaint("Carbs", lightBlue);
        plot.setSectionPaint("Proteins", mediumBlue);
        plot.setSectionPaint("Fats", darkBlue);

        plot.setSectionDepth(0.3);

        // Rimuovi la l'ombreggiatura
        plot.setShadowPaint(null);
        // Rimuovi le linee che sezionano il grafico
        plot.setSectionOutlinesVisible(false);

        // Modifica lo stile delle etichette
        plot.setLabelFont(new Font("Arial", Font.PLAIN, 12));
        plot.setLabelPaint(darkBlue);

        // Crea un pannello per il grafico e aggiungilo al pannello principale
        ChartPanel chartPanel = new ChartPanel(pieChart);
        chartPanel.setOpaque(false);
        chartPanel.setMaximumSize(new Dimension(200, 200)); // Imposta la dimensione massima del grafico
        singlePanel.add(chartPanel);

        macrosDistributionPanel.add(singlePanel, gbc);

        //////////////////////////////////////////////////////////////////////////

        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton macrosDistributionButton = new JButton("Edit Macros Distribution");
        macrosDistributionButton.setPreferredSize(new Dimension(macrosDistributionButton.getPreferredSize().width, 50)); // Imposta l'altezza a 50
        macrosDistributionButton.setMinimumSize(new Dimension(macrosDistributionButton.getMinimumSize().width, 50)); // Imposta l'altezza minima a 50
        buttonPanel.add(macrosDistributionButton, BorderLayout.CENTER);
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        macrosDistributionPanel.add(buttonPanel, gbc);


        return macrosDistributionPanel;
    }

    private JPanel createMealDistributionPanel() {
        JPanel mealDistributionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        mealDistributionPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));

        JLabel label = new JLabel("Meal Distribution", SwingConstants.LEFT);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mealDistributionPanel.add(label, gbc);

        JPanel mealLabelsPanel = new JPanel();
        mealLabelsPanel.setLayout(new BoxLayout(mealLabelsPanel, BoxLayout.X_AXIS));

        JTextField carbsOutput = new JTextField(10);
        JTextField proteinsOutput = new JTextField(10);
        JTextField fatsOutput = new JTextField(10);

        carbsOutput.setEditable(false);
        proteinsOutput.setEditable(false);
        fatsOutput.setEditable(false);

        JPanel carbsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        JPanel proteinsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        JPanel fatsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));

        carbsPanel.add(new JLabel("Carbs:"));
        carbsPanel.add(carbsOutput);
        carbsPanel.add(new JLabel("g"));
        proteinsPanel.add(new JLabel("Proteins:"));
        proteinsPanel.add(proteinsOutput);
        proteinsPanel.add(new JLabel("g"));
        fatsPanel.add(new JLabel("Fats:"));
        fatsPanel.add(fatsOutput);
        fatsPanel.add(new JLabel("g"));

        mealLabelsPanel.add(Box.createHorizontalGlue());
        mealLabelsPanel.add(carbsPanel);
        mealLabelsPanel.add(Box.createHorizontalStrut(15));
        mealLabelsPanel.add(proteinsPanel);
        mealLabelsPanel.add(Box.createHorizontalStrut(15));
        mealLabelsPanel.add(fatsPanel);
        mealLabelsPanel.add(Box.createHorizontalGlue());

        gbc.gridy = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mealDistributionPanel.add(mealLabelsPanel, gbc);

        JPanel emptyPanel = new JPanel();
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mealDistributionPanel.add(emptyPanel, gbc);


        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton macrosDistributionButton = new JButton("Edit Macros Distribution");
        macrosDistributionButton.setPreferredSize(new Dimension(macrosDistributionButton.getPreferredSize().width, 50)); // Imposta l'altezza a 50
        macrosDistributionButton.setMinimumSize(new Dimension(macrosDistributionButton.getMinimumSize().width, 50)); // Imposta l'altezza minima a 50
        buttonPanel.add(macrosDistributionButton, BorderLayout.CENTER);
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mealDistributionPanel.add(buttonPanel, gbc);


        return mealDistributionPanel;
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

    private JPanel createTitlePanel(){
        JLabel label = new JLabel("Daily Plan", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));
        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        return labelPanel;
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