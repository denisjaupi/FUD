package Controller;

import Model.Database.Db;
import Model.Entities.DailyCount;
import Model.Entities.Exercise;
import Model.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dbActivitiesManager {
    private static User user;
    DailyCount dailyCount;
    Exercise e;

    public void setUser(User u){
        user = u;
        dailyCount=user.getDailyCount();
    }

    public  void select() throws SQLException {
        String query = "SELECT COUNT (*) FROM activities WHERE id_user = " + user.getId();
        ResultSet ris =Db.result(query);
        if(ris.next()) {
            int count = ris.getInt(1);
            if(count> 0) {
                query = "SELECT * FROM activities WHERE id_user = " + user.getId();
                ResultSet rs=Db.result(query);
                if(rs.next()) {
                    do{
                        int id_exercise = rs.getInt("id_exercise");
                        double calories = rs.getDouble("calories");
                        int time = rs.getInt("time");
                        query="select * from exercises where id_exercise="+id_exercise;
                        rs=Db.result(query);
                        if(rs.next()) {
                            e = new Exercise(id_exercise, rs.getString("name"), rs.getDouble("met"));
                            e.setCalories(calories);
                            e.setIntensity(rs.getString("intensity"));
                            e.setTime(time);
                            dailyCount.addExercise(e);
                        }
                    } while(rs.next());
                }

            }
        }
    }

    public void addActivity(int id_exercise, double calories, int time) throws SQLException {
        String query = "INSERT INTO activities (id_exercise, calories, time, id_user) VALUES (" + id_exercise+ ", " + calories + ", " + time+ "," + user.getId() + ")";
        Db.result(query);
        query = "select * from exercises where id_exercise =" + id_exercise;
        ResultSet rs = Db.result(query);
        if (rs.next()) {
            Exercise e = new Exercise(id_exercise, rs.getString("name"), rs.getDouble("met"));
            e.setCalories(calories);
            e.setIntensity(rs.getString("intensity"));
            e.setTime(time);
            user.getDailyCount().addExercise(e);
        }

    }

    public static void deleteActivities(int id_activity) {
        String query = "DELETE FROM activities WHERE id_activity = " + id_activity;
        Db.result(query);
    }

    public static void deleteActivities_withUser(int id_user) {

        try {
            String query = "SELECT COUNT (*) FROM activities WHERE id_user = " + id_user;
            ResultSet ris = Db.result(query);
            if (ris.next()) {
                int count = ris.getInt(1);
                if (count > 0) {
                    query = "DELETE FROM activities WHERE id_user = " + id_user;
                    Db.result(query);
                }
            }
        }catch (SQLException e){
            System.err.println("Errore durante la cancellazione delle attività: " + e.getMessage());
        }
    }
}
