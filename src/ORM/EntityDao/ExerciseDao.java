package ORM.EntityDao;


import java.sql.*;

public class ExerciseDao {

    public ResultSet getAllExercises() throws SQLException {
        String query = "SELECT name, met, intensity FROM exercise ORDER BY name ASC";
        return ManagerDao.result(query);
    }

    public ResultSet getExerciseById(int id) throws SQLException {
        String query = "SELECT name, met, intensity FROM exercise WHERE id_exercise = " + id;
        return ManagerDao.result(query);
    }

    public ResultSet getExerciseByName(String name) throws SQLException {
        String query = "SELECT name, met, intensity FROM exercise WHERE name = '" + name + "'";
        return ManagerDao.result(query);
    }

    public void addExercise(String name, float met, String intensity) throws SQLException {
        String query = "INSERT INTO exercise (name, met, intensity) VALUES (?, ?, ?)";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, name);
        pstmt.setFloat(2, met);
        pstmt.setString(3, intensity);
        pstmt.executeUpdate();
    }

    public void removeExerciseById(int id) throws SQLException {
        String query = "DELETE FROM exercise WHERE id_exercise = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public void removeExerciseByName(String name) throws SQLException {
        String query = "DELETE FROM exercise WHERE name = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.executeUpdate();
    }

    public int getExerciseId(String name, String intensity) throws SQLException {
        String query = "SELECT id_exercise FROM exercise WHERE name = ? AND intensity = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, intensity);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public double getExerciseMet(String name, String intensity) throws SQLException {
        String query = "SELECT met FROM exercise WHERE name = ? AND intensity = ?";
        PreparedStatement pstmt = ManagerDao.getConnection().prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, intensity);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0;
    }
}
