package BusinessLogic.Service;

import ORM.EntityDao.ActivityDao;
import Model.Entities.DailyCount;
import Model.Entities.Exercise;
import Model.Entities.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActivityService {
    private static User user;
    private DailyCount dailyCount;
    private Exercise exercise;
    private ActivityDao activityDao;

    public ActivityService(ActivityDao activityDao) {
        this.activityDao = activityDao;
    }
    public void setUser(User u) {
        user = u;
        dailyCount = new DailyCount(user.getUserName());
        dailyCount = user.getDailyCount();
    }

    public void selectActivities() throws SQLException {
        int activityCount = activityDao.countActivitiesForUser(user.getId());
        if (activityCount > 0) {
            ResultSet rs = activityDao.getActivitiesForUser(user.getId());
            while (rs.next()) {
                int exerciseId = rs.getInt("id_exercise");
                double calories = rs.getDouble("calories");
                int time = rs.getInt("time");

                ResultSet exerciseResult = activityDao.getExerciseById(exerciseId);
                if (exerciseResult.next()) {
                    exercise = new Exercise(exerciseId, exerciseResult.getString("name"), exerciseResult.getDouble("met"));
                    exercise.setCalories(calories);
                    exercise.setIntensity(exerciseResult.getString("intensity"));
                    exercise.setTime(time);
                    dailyCount.addExercise(exercise);
                }
            }
        }
    }

    public boolean addActivity(int exerciseId, double calories, float time) throws SQLException {
        if (!activityDao.activityExists(exerciseId)) {
            activityDao.addActivity(exerciseId, calories, time, user.getId());
            ResultSet rs = activityDao.getExerciseById(exerciseId);
            if (rs.next()) {
                Exercise exercise = new Exercise(exerciseId, rs.getString("name"), rs.getDouble("met"));
                exercise.setCalories(calories);
                exercise.setIntensity(rs.getString("intensity"));
                exercise.setTime(time);
                user.getDailyCount().addExercise(exercise);
            }
            return true;
        }
        return false;
    }

    public void deleteActivity(int activityId) {
        try {
            activityDao.deleteActivity(activityId);
        } catch (SQLException e) {
            System.err.println("Errore durante la cancellazione dell'attività: " + e.getMessage());
        }
    }

    public void deleteActivitiesForUser(int userId) {
        try {
            int activityCount = activityDao.countActivitiesForUser(userId);
            if (activityCount > 0) {
                activityDao.deleteActivitiesForUser(userId);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante la cancellazione delle attività: " + e.getMessage());
        }
    }
}

