package Controller;

import Model.Database.Db;
import java.sql.*;

public class dbExerciseManager {

    public static ResultSet getExercise() {
        return Db.result("SELECT name,met, intensity FROM exercise Order by name ASC");
    }

    public static ResultSet getExercise_withId(int id) {
        return Db.result("SELECT name,met, intensity FROM exercise where id_exercise = " + id);
    }

    public static ResultSet getExercise_withName(String name) {
        return Db.result("SELECT name, met, intensity FROM exercise where name = '" + name + "'");
    }

    public static void add_exercise_fromDb(String name, float met, String intensity){
        try {
            // Get the database connection
            Connection conn = Db.getConnection();

            // Create a PreparedStatement for the INSERT statement
            PreparedStatement pstmt = conn.prepareStatement(
                    "INSERT INTO exercise (name, met, intensity) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            // Set the parameters
            pstmt.setString(1, name);
            pstmt.setFloat(2, met);
            pstmt.setString(3, intensity);

            // Execute the INSERT statement
            pstmt.executeUpdate();

            // Get the generated keys
            ResultSet rs = pstmt.getGeneratedKeys();

        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento del cibo nel database");
            e.printStackTrace();
        }
    }

    public static void remove_exercise_withId(int id) {
        try {
            // Get the database connection
            Connection conn = Db.getConnection();

            // Create a PreparedStatement for the DELETE statement
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM exercise WHERE id_exercise = ?"
            );

            // Set the parameters
            pstmt.setInt(1, id);

            // Execute the DELETE statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'eliminazione del cibo dal database");
            e.printStackTrace();
        }
    }
    public static void remove_exercise_withName(String name) {
        try {
            // Get the database connection
            Connection conn = Db.getConnection();

            // Create a PreparedStatement for the DELETE statement
            PreparedStatement pstmt = conn.prepareStatement(
                    "DELETE FROM exercise WHERE name= ?"
            );
            // Set the parameters
            pstmt.setString(1, name);

            // Execute the DELETE statement
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errore durante l'eliminazione del cibo dal database");
            e.printStackTrace();
        }
    }

    public int selectId (String name, String intensity) throws SQLException {
        String query = "SELECT id_exercise FROM exercise WHERE name = '" + name + "' AND intensity = '" + intensity + "'";
        ResultSet rs = Db.result(query);
        int id = 0;
        if (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    public double selectMet (String name, String intensity) throws SQLException {
        String query = "select met from exercise where name = '" + name + "' and intensity =  '" + intensity + "'";
        ResultSet rs = Db.select(query);
        double met = 0;
        if (rs.next()) {
            met = rs.getInt(1);
        }
        return met;
    }
}
