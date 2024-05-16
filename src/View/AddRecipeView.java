package View;

import Controller.PageNavigationController;
import Controller.dbFoodManager;

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
import java.util.jar.JarEntry;


public class AddRecipeView extends JFrame {

    // Dichiaro i campi di testo come variabili di istanza
    private JTextField nameField;
    private JTextField descriptionField;

    public AddRecipeView() {
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createMainPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel backButtonPanel = createBackButtonPanel();
        JPanel addButtonPanel = createAddButtonPanel();
        JPanel contentPanel = createContentPanel();

        mainPanel.add(backButtonPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(addButtonPanel, BorderLayout.EAST);

        return mainPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createTitlePanel());
        contentPanel.add(createAddTablePanel());

        return contentPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createAddTablePanel() {

        JPanel generalPanel = new JPanel(new BorderLayout());

        //////////////////////////////////////////////////////////////////////////

        JPanel addNamePanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        // Prima riga, prima colonna
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 0.1;  // Imposta la larghezza relativa della prima colonna
        constraints.fill = GridBagConstraints.HORIZONTAL;
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addNamePanel.add(nameLabel, constraints);

        // Prima riga, seconda colonna
        constraints.gridx = 1;
        constraints.weightx = 0.9;  // Imposta la larghezza relativa della seconda colonna
        nameField = new JTextField();
        addNamePanel.add(nameField, constraints);

        // Seconda riga, prima colonna
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 0.1;  // Imposta la larghezza relativa della prima colonna
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addNamePanel.add(descriptionLabel, constraints);

        // Seconda riga, seconda colonna
        constraints.gridx = 1;
        constraints.weightx = 0.9;  // Imposta la larghezza relativa della seconda colonna
        descriptionField = new JTextField();
        addNamePanel.add(descriptionField, constraints);

        //////////////////////////////////////////////////////////////////////////////////////////////

        JPanel chooseFoodPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints2 = new GridBagConstraints();
        constraints2.fill = GridBagConstraints.HORIZONTAL;

        // Prima colonna
        constraints2.gridx = 0;
        constraints2.weightx = 0.1;  // Imposta la larghezza relativa della prima colonna
        JLabel chooseLabel = new JLabel("Food:");
        chooseLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        chooseFoodPanel.add(chooseLabel, constraints2);

        // Seconda colonna
        constraints2.gridx = 1;
        constraints2.weightx = 0.2;  // Imposta la larghezza relativa della seconda colonna
        JComboBox<String> foodComboBox = new JComboBox<>();
        try {
            ResultSet rs = dbFoodManager.getFood();
            while (rs.next()) {
                String foodName = rs.getString("name");
                foodComboBox.addItem(foodName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        chooseFoodPanel.add(foodComboBox, constraints2);

        // Terza colonna
        constraints2.gridx = 2;
        constraints2.weightx = 0.15;  // Imposta la larghezza relativa della terza colonna
        JLabel quantityLabel = new JLabel("Quantity (g):");
        quantityLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        chooseFoodPanel.add(quantityLabel, constraints2);

        // Quarta colonna
        constraints2.gridx = 3;
        constraints2.weightx = 0.25;  // Imposta la larghezza relativa della quarta colonna
        JSpinner quantitySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1));
        chooseFoodPanel.add(quantitySpinner, constraints2);

        // Quinta colonna
        constraints2.gridx = 4;
        constraints2.weightx = 0.3;  // Imposta la larghezza relativa della quinta colonna
        JButton addFoodButton = new JButton("Add Food");
        chooseFoodPanel.add(addFoodButton, constraints2);

        //////////////////////////////////////////////////////////////////////////////////////////////

        JPanel tablePanel = new JPanel(new FlowLayout());
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));

        // Creare il modello della tabella
        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Name", "Quantity", "Total Calories", "Protiens", "Carbohydrates", "Fats"}, 0);

        // Creare la tabella
        JTable table = new JTable(tableModel);

        // Creare lo JScrollPane e aggiungere la tabella
        JScrollPane scrollPane = new JScrollPane(table);

        addFoodButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String food = (String) foodComboBox.getSelectedItem();
                int quantity = (int) quantitySpinner.getValue();


                // Aggiungi una nuova riga al modello della tabella
                tableModel.addRow(new Object[]{food, quantity});
                // dbFoodManager.add_food_Db(name, description, calories, proteins, carbohydrates, fats);

            }
        });

        tablePanel.add(scrollPane, BorderLayout.NORTH);

        //////////////////////////////////////////////////////////////////////////

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setLayout(new GridBagLayout());

        GridBagConstraints constraints4 = new GridBagConstraints();

        constraints4.fill = GridBagConstraints.BOTH;
        constraints4.weightx = 1;  // Imposta la larghezza relativa a 1

        // Aggiungi tablePanel
        constraints4.gridy = 0;
        constraints4.weighty = 0.9;  // Imposta l'altezza relativa a 90%
        southPanel.add(tablePanel, constraints4);

        JPanel totalPanel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints3 = new GridBagConstraints();
        constraints3.fill = GridBagConstraints.HORIZONTAL;

        // Prima colonna
        constraints3.gridx = 0;
        constraints3.weightx = 0.3;  // Imposta la larghezza relativa della prima colonna
        JLabel recipeLabel = new JLabel("Recipe:");
        recipeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        recipeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalPanel.add(recipeLabel, constraints3);  // Aggiungi il campo di testo al pannello

        // Seconda colonna
        constraints3.gridx = 1;
        constraints3.weightx = 0.2;  // Imposta la larghezza relativa della seconda colonna
        JTextField calorieField = new JTextField();
        calorieField.setEditable(false);
        totalPanel.add(calorieField, constraints3);  // Aggiungi il campo di testo al pannello

        // Terza colonna
        constraints3.gridx = 2;
        constraints3.weightx = 0.2;  // Imposta la larghezza relativa della terza colonna
        JTextField proteinField = new JTextField();
        proteinField.setEditable(false);
        totalPanel.add(proteinField, constraints3);  // Aggiungi il campo di testo al pannello

        // Quarta colonna
        constraints3.gridx = 3;
        constraints3.weightx = 0.2;  // Imposta la larghezza relativa della quarta colonna
        JTextField carbosField = new JTextField();
        carbosField.setEditable(false);
        totalPanel.add(carbosField, constraints3);  // Aggiungi il campo di testo al pannello

        // Quinta colonna
        constraints3.gridx = 4;
        constraints3.weightx = 0.2;  // Imposta la larghezza relativa della quinta colonna
        JTextField fatsField = new JTextField();
        fatsField.setEditable(false);
        totalPanel.add(fatsField, constraints3);  // Aggiungi il campo di testo al pannello

        // Aggiungi totalPanel
        constraints4.gridy = 1;
        constraints4.weighty = 0.1;  // Imposta l'altezza relativa a 20%
        southPanel.add(totalPanel, constraints4);

        //////////////////////////////////////////////////////////////////////////////////////////////

        generalPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;

        // Aggiungi addNamePanel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.05;  // Imposta l'altezza relativa a 1/5
        generalPanel.add(addNamePanel, gbc);

        // Aggiungi chooseFoodPanel
        gbc.gridy = 1;
        gbc.weighty = 0.05;  // Imposta l'altezza relativa a 1/5
        generalPanel.add(chooseFoodPanel, gbc);

        // Aggiungi southPanel
        gbc.gridy = 2;
        gbc.weighty = 0.9;  // Imposta l'altezza relativa a 3/5
        generalPanel.add(southPanel, gbc);

        return generalPanel;
    }

    private JPanel createTitlePanel() {

        JLabel label = new JLabel("New Recipe", SwingConstants.CENTER);
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

        JToggleButton addFoodButton = createButton("Add Recipe", buttonGroup, () -> {
            String name = nameField.getText();
            String description = descriptionField.getText();

            // dbFoodManager.add_food_Db(name, description, calories, proteins, carbohydrates, fats);

            // Dopo aver aggiunto il cibo, naviga alla pagina FoodsTable
            pageNavigationController.navigateToRecipesTable();
        });

        buttonPanel.add(addFoodButton);

        return buttonPanel;
    }

    private JPanel createBackButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);

        JToggleButton backButton = createButton("Back", buttonGroup, pageNavigationController::navigateToRecipesTable);

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
