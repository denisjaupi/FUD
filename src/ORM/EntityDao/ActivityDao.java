package ORM.EntityDao;


import Model.Entities.Exercise;
import Model.Entities.DailyCount;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityDao {
    private Exercise e;
    private DailyCount dailyCount;

    public int countActivitiesForUser(int userId) throws SQLException {
        String query = "SELECT COUNT(*) FROM activities WHERE id_user = " + userId;
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    public  void select(int id_user) throws SQLException {
        String query = "SELECT COUNT (*) FROM activities WHERE id_user = " + id_user;
        ResultSet ris =ManagerDao.result(query);
        if(ris.next()) {
            int count = ris.getInt(1);
            if(count> 0) {
                query = "SELECT * FROM activities WHERE id_user = " + id_user;
                ResultSet rs=ManagerDao.result(query);
                if(rs.next()) {
                    do{
                        int id_exercise = rs.getInt("id_exercise");
                        double calories = rs.getDouble("calories");
                        int time = rs.getInt("time");
                        query="select * from exercise where id_exercise="+id_exercise;
                        rs=ManagerDao.result(query);
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

    public ResultSet getActivitiesForUser(int userId) throws SQLException {
        String query = "SELECT * FROM activities WHERE id_user = " + userId;
        return ManagerDao.result(query);
    }

    public ResultSet getExerciseById(int exerciseId) throws SQLException {
        String query = "SELECT * FROM exercise WHERE id_exercise = " + exerciseId;
        return ManagerDao.result(query);
    }

    public void addActivity(int exerciseId, double calories, float time, int userId) throws SQLException {
        String query = "INSERT INTO activities (id_exercise, calories, time, id_user) VALUES (" + exerciseId + ", " + calories + ", " + time + ", " + userId + ")";
        ManagerDao.result(query);
    }

    public boolean activityExists(int exerciseId) throws SQLException {
        String query = "SELECT COUNT(*) FROM activities WHERE id_exercise = " + exerciseId;
        ResultSet rs = ManagerDao.select(query);
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
        return false;
    }

    public void deleteActivity(int activityId) throws SQLException {
        String query = "DELETE FROM activities WHERE id_activity = " + activityId;
        ManagerDao.result(query);
    }

    public void deleteActivitiesForUser(int userId) throws SQLException {
        String query = "DELETE FROM activities WHERE id_user = " + userId;
        ManagerDao.result(query);
    }
}
