package Model.Database;
import java.sql.*;

public class Db{
    private static final String URL = "jdbc:postgresql://localhost:5432/FUD";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public static ResultSet result(String query) {
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            // Carica il driver JDBC per PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Effettua la connessione al database PostgreSQL
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Se la connessione ha avuto successo
            if (connection != null) {
                System.out.println("Connessione al database stabilita!");
                // Crea uno statement per eseguire la query
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //QUERY
                try {
                    resultSet = statement.executeQuery(query);
                }catch (SQLException e){}

            } else {
                System.out.println("Connessione al database non riuscita!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC non trovato!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database!");
            e.printStackTrace();
        } finally {
            // Chiudi la connessione quando hai finito
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Connessione al database chiusa!");
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura della connessione!");
                e.printStackTrace();
            }
        }
        return resultSet;
    }

    public static Connection getConnection() throws SQLException {
        try {
            // Load the JDBC driver for PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found!");
            e.printStackTrace();
        }

        // Establish and return the connection to the PostgreSQL database
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }


    public static ResultSet select(String query) {
        Connection connection = null;
        ResultSet resultSet = null;

        try {
            // Carica il driver JDBC per PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Effettua la connessione al database PostgreSQL
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Se la connessione ha avuto successo
            if (connection != null) {
                System.out.println("Connessione al database stabilita!");
                // Crea uno statement per eseguire la query
                Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                // Esegui la query
                resultSet = statement.executeQuery(query);
            } else {
                System.out.println("Connessione al database non riuscita!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC non trovato!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Errore durante la connessione al database!");
            e.printStackTrace();
        }
        return resultSet;
    }
    public static int update(String query) throws SQLException {
        Connection connection = null;
        Statement statement = null;
        int rowsAffected = 0;

        try {
            // Carica il driver JDBC per PostgreSQL
            Class.forName("org.postgresql.Driver");

            // Effettua la connessione al database PostgreSQL
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            // Se la connessione ha avuto successo
            if (connection != null) {
                System.out.println("Connessione al database stabilita!");
                // Crea uno statement per eseguire l'update
                statement = connection.createStatement();
                // Esegui l'update
                rowsAffected = statement.executeUpdate(query);
            } else {
                System.out.println("Connessione al database non riuscita!");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC non trovato!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Errore durante l'esecuzione dell'update!");
            e.printStackTrace();
        } finally {
            // Chiudi la connessione e lo statement quando hai finito
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                    System.out.println("Connessione al database chiusa!");
                }
            } catch (SQLException e) {
                System.out.println("Errore durante la chiusura della connessione o dello statement!");
                e.printStackTrace();
            }
        }
        return rowsAffected;
    }


}