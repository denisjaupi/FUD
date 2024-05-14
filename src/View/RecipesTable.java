package View;

import Controller.PageNavigationController;
import Controller.dbFoodManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipesTable extends JFrame {

    public RecipesTable() {
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
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Name", "Calories", "Proteins", "Carbohydrates", "Fats"}, 0);

        // Creare la tabella con il modello
        JTable table = new JTable(model);

        // Impostare l'header della tabella in grassetto
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 18));

        // Riempire il modello con i dati dal database
        ResultSet rs = dbFoodManager.getFood();

        try{
            while(rs.next()){

                String name = rs.getString("name");
                String calories = rs.getString("calories");
                String proteins = rs.getString("proteins");
                String carbohydrates = rs.getString("carbohydrates");
                String fats = rs.getString("fats");

                Object[] row = new Object[]{name, calories, proteins, carbohydrates, fats};
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
        JLabel label = new JLabel("Recipes Table", SwingConstants.CENTER);
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

        JToggleButton button1 = createButton("Back", buttonGroup, pageNavigationController::navigateToHome);

        buttonPanel.add(button1);

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
