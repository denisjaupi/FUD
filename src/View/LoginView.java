package View;

import Controller.Engine;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LoginView extends JFrame {

    // Dichiaro i campi di testo come variabili di istanza
    private JTextField emailField;
    private JTextField passwordField;
    private Engine engine = new Engine();


    public LoginView(Engine engine) {
        setupWindow();
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        setVisible(true);
        this.engine = engine;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createMainPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel signInButtonPanel = createSignInButtonPanel();
        JPanel loginButtonPanel = createLoginButtonPanel();
        JPanel contentPanel = createContentPanel();

        mainPanel.add(signInButtonPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(loginButtonPanel, BorderLayout.EAST);

        return mainPanel;
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.add(createTitlePanel());
        contentPanel.add(createLogUserPanel());

        return contentPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createLogUserPanel() {
        JPanel addTablePanel = new JPanel(new GridLayout(12, 1));

        // Creare le etichette
        JLabel emailLabel = new JLabel("Email:");
        JLabel passwordLabel = new JLabel("Password:");

        ///////////////////////////////////////////////////////

        // Creare un'etichetta per il link "Forgot password?"
        JLabel forgotPasswordLabel = new JLabel("<html><a href=''>Forgot password?</a></html>");
        forgotPasswordLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // inserisci qui il codice da eseguire quando l'utente clicca sulla label
                System.out.println("Forgot password clicked");
            }
        });

        ///////////////////////////////////////////////////////


        // Creare i campi di testo
        emailField = new JTextField();
        passwordField = new JTextField();

        // Aggiungere le etichette e i campi di testo al pannello
        addTablePanel.add(emailLabel);
        addTablePanel.add(emailField);
        addTablePanel.add(passwordLabel);
        addTablePanel.add(passwordField);
        addTablePanel.add(forgotPasswordLabel);

        //////////////////////////////////////////////////////////////////////////////////////////////

        JPanel emptyPanel = new JPanel();
        addTablePanel.add(emptyPanel);

        return addTablePanel;
    }

    private JPanel createTitlePanel() {

        JLabel label = new JLabel("Access", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 36));

        JPanel labelPanel = new JPanel(new FlowLayout());
        labelPanel.add(label);

        return labelPanel;
    }

    ////////////////////////////////////////////////////////////////

    private JPanel createLoginButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);

        JToggleButton loginButton = createButton("Login", buttonGroup, () -> {
            // Esegui il login
            String email = emailField.getText();
            String password = passwordField.getText();

            if (engine.login(email, password)) {
                System.out.println("Login effettuato con successo");
            } else {
                System.out.println("Credenziali non valide");
            }

            // Dopo aver aggiunto il cibo, naviga alla pagina FoodsTable
            pageNavigationController.navigateToHome();
        });

        buttonPanel.add(loginButton);

        return buttonPanel;
    }

    private JPanel createSignInButtonPanel() {

        JPanel buttonPanel = new JPanel(new GridLayout(11, 1));
        ButtonGroup buttonGroup = new ButtonGroup();
        PageNavigationController pageNavigationController = new PageNavigationController(this);
        pageNavigationController.setEngine(engine);

        JToggleButton signInButton = createButton("Sign In", buttonGroup, pageNavigationController::navigateToRegister);

        buttonPanel.add(signInButton);

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
