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

public class RegisterView extends JFrame {

    // Dichiaro i campi di testo come variabili di istanza
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField validatePasswordField;


    public RegisterView() {
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createMainPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel backButtonPanel = createBackButtonPanel();
        JPanel signInButtonPanel = createSignInButtonPanel();
        JPanel contentPanel = createContentPanel();

        mainPanel.add(backButtonPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(signInButtonPanel, BorderLayout.EAST);

        return mainPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createTitlePanel());
        contentPanel.add(createAddUserPanel());

        return contentPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createAddUserPanel() {
        JPanel addTablePanel = new JPanel(new GridLayout(12, 1));

        // Creare le etichette
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel validatePasswordLabel = new JLabel("Confirm Password:");


        // Creare i campi di testo
        usernameField = new JTextField();
        passwordField = new JTextField();
        validatePasswordField = new JTextField();


        // Aggiungere le etichette e i campi di testo al pannello
        addTablePanel.add(usernameLabel);
        addTablePanel.add(usernameField);
        addTablePanel.add(passwordLabel);
        addTablePanel.add(passwordField);
        addTablePanel.add(validatePasswordLabel);
        addTablePanel.add(validatePasswordField);

        //////////////////////////////////////////////////////////////////////////////////////////////

        JPanel emptyPanel = new JPanel();
        addTablePanel.add(emptyPanel);

        return addTablePanel;
    }

    private JPanel createTitlePanel() {

        JLabel label = new JLabel("Sign In", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        return labelPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createSignInButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);

        JToggleButton addFoodButton = createButton("Sign In", buttonGroup, () -> {
            // Aggiungi l'utente al database

            // Dopo aver aggiunto il cibo, naviga alla pagina FoodsTable
            pageNavigationController.navigateToLogin();
        });

        buttonPanel.add(addFoodButton);

        return buttonPanel;
    }

    private JPanel createBackButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);

        JToggleButton backButton = createButton("Back", buttonGroup, pageNavigationController::navigateToLogin);

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
