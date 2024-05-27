package View;

import Controller.Engine;
import Controller.PageNavigationController;
import Controller.dbExerciseManager;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrainingTable extends JFrame {

    private Engine engine;

    public TrainingTable(Engine engine) {
        this.engine = engine;

        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel backButtonPanel = createBackButtonPanel();
        JPanel contentPanel = createContentPanel();

        mainPanel.add(backButtonPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createTitlePanel());
        contentPanel.add(createTablePanel());

        return contentPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());

        // Creare un modello per la tabella
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Name", "Intensity", "Add"}, 0);

        // Creare la tabella con il modello
        JTable table = new JTable(model);

        // Impostare l'header della tabella in grassetto
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));

        ///////////////////////////////////////////////////////
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);

        // Aggiungi un TableCellRenderer e un TableCellEditor alla colonna "Add"
        TableColumn addColumn = table.getColumn("Add");
        addColumn.setCellRenderer(new TableCellRenderer() {
            JButton button = new JButton("+");
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return button;
            }
        });
        addColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                JButton button = new JButton("+");
                button.addActionListener(e -> {
                    // Ottieni il nome e l'intensità dell'esercizio selezionato
                    String name = (String) table.getValueAt(row, 0);
                    String intensity = (String) table.getValueAt(row, 1);

                    pageNavigationController.navigateToAddTraining(name, intensity);
                });
                return button;
            }
        });

        ///////////////////////////////////////////////////////

        // Ottieni il modello delle colonne
        TableColumnModel columnModel = table.getColumnModel();

        // Imposta la larghezza preferita, minima e massima per la prima colonna
        TableColumn firstColumn = columnModel.getColumn(0);
        firstColumn.setPreferredWidth(400);
        firstColumn.setMinWidth(400);
        firstColumn.setMaxWidth(400);

        // Imposta la larghezza preferita, minima e massima per la seconda colonna
        TableColumn secondColumn = columnModel.getColumn(1);
        secondColumn.setPreferredWidth(400);
        secondColumn.setMinWidth(400);
        secondColumn.setMaxWidth(400);

        // Imposta la larghezza preferita, minima e massima per la terza colonna
        TableColumn thirdColumn = columnModel.getColumn(2);
        thirdColumn.setPreferredWidth(100);
        thirdColumn.setMinWidth(100);
        thirdColumn.setMaxWidth(100);

        // Imposta la larghezza della tabella e del JScrollPane
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setFillsViewportHeight(true);

        ///////////////////////////////////////////////////////

        // Riempire il modello con i dati dal database
        ResultSet rs = dbExerciseManager.getExercise();

        try{
            while(rs.next()){

                String name = rs.getString("name");
                String intensity = rs.getString("intensity");

                Object[] row = new Object[]{name, intensity};
                model.addRow(row);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        // Aggiungere la tabella a uno JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;

    }

    private JPanel createTitlePanel() {
        JLabel label = new JLabel("Training Table", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        return labelPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createBackButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);

        JToggleButton backButton = createButton("Back", buttonGroup, pageNavigationController::navigateToHome);

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