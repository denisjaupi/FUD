package View;

import Controller.PageNavigationController;
import Controller.dbFoodManager;
import Model.Entities.Food;
import Model.Entities.NutritionalInfo;

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

public class AddFoodView extends JFrame {

    // Dichiaro i campi di testo come variabili di istanza
    private JTextField nameField;
    private JTextField caloriesField;
    private JTextField proteinsField;
    private JTextField carbohydratesField;
    private JTextField fatsField;
    private Food food;
    private NutritionalInfo nutritionalInfo;

    public AddFoodView() {
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
        JPanel addTablePanel = new JPanel(new GridLayout(15, 1));

        // Creare le etichette
        JLabel nameLabel = new JLabel("Name:");
        JLabel caloriesLabel = new JLabel("Calories:");
        JLabel proteinsLabel = new JLabel("Proteins:");
        JLabel carbohydratesLabel = new JLabel("Carbohydrates:");
        JLabel fatsLabel = new JLabel("Fats:");

        // Creare i campi di testo
        nameField = new JTextField();
        caloriesField = new JTextField();
        proteinsField = new JTextField();
        carbohydratesField = new JTextField();
        fatsField = new JTextField();

        // Aggiungere le etichette e i campi di testo al pannello
        addTablePanel.add(nameLabel);
        addTablePanel.add(nameField);
        addTablePanel.add(caloriesLabel);
        addTablePanel.add(caloriesField);
        addTablePanel.add(proteinsLabel);
        addTablePanel.add(proteinsField);
        addTablePanel.add(carbohydratesLabel);
        addTablePanel.add(carbohydratesField);
        addTablePanel.add(fatsLabel);
        addTablePanel.add(fatsField);

        //////////////////////////////////////////////////////////////////////////////////////////////

        DocumentFilter onlyNumberFilter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (newStr.matches("\\d*(\\.\\d*)?") && newStr.length() <= 8) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newStr.matches("\\d*(\\.\\d*)?") && newStr.length() <= 8) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };
        DocumentFilter onlyLetterFilter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + string;
                if (newStr.matches("[a-zA-Z]*") && newStr.length() <= 30) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newStr = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (newStr.matches("[a-zA-Z]*") && newStr.length() <= 30) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        };

        // Applica il DocumentFilter ai campi di testo
        ((AbstractDocument) caloriesField.getDocument()).setDocumentFilter(onlyNumberFilter);
        ((AbstractDocument) proteinsField.getDocument()).setDocumentFilter(onlyNumberFilter);
        ((AbstractDocument) carbohydratesField.getDocument()).setDocumentFilter(onlyNumberFilter);
        ((AbstractDocument) fatsField.getDocument()).setDocumentFilter(onlyNumberFilter);
        ((AbstractDocument) nameField.getDocument()).setDocumentFilter(onlyLetterFilter);

        //////////////////////////////////////////////////////////////////////////////////////////////

        JPanel emptyPanel = new JPanel();
        addTablePanel.add(emptyPanel);

        return addTablePanel;
    }

    private JPanel createTitlePanel() {

        JLabel label = new JLabel("New FUD", SwingConstants.CENTER);
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
            String name = nameField.getText();
            float calories = Float.parseFloat(caloriesField.getText());
            float proteins = Float.parseFloat(proteinsField.getText());
            float carbohydrates = Float.parseFloat(carbohydratesField.getText());
            float fats = Float.parseFloat(fatsField.getText());

            food = new Food(name, new NutritionalInfo(calories, proteins, carbohydrates, fats));

            dbFoodManager.addFood_Db(food);
            food.setId(dbFoodManager.selectId(name));

            // Dopo aver aggiunto il cibo, naviga alla pagina FoodsTable
            pageNavigationController.navigateToFoodTable();
        });

        buttonPanel.add(addFoodButton);

        return buttonPanel;
    }

    private JPanel createBackButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);

        JToggleButton backButton = createButton("Back", buttonGroup, pageNavigationController::navigateToFoodTable);

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
