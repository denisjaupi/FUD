package BusinessLogic.Service;



import ORM.EntityDao.ExerciseDao;
import java.sql.*;

public class ExerciseService {

    private static ExerciseDao exerciseDao;

    public ExerciseService(ExerciseDao exerciseDao) {
        this.exerciseDao = exerciseDao;
    }

    public ResultSet getExercise() {
        try {
            return exerciseDao.getAllExercises();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getExercise_withId(int id) {
        try {
            return exerciseDao.getExerciseById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public  ResultSet getExercise_withName(String name) {
        try {
            return exerciseDao.getExerciseByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public  void add_exercise_fromDb(String name, float met, String intensity) {
        try {
            exerciseDao.addExercise(name, met, intensity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void remove_exercise_withId(int id) {
        try {
            exerciseDao.removeExerciseById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void remove_exercise_withName(String name) {
        try {
            exerciseDao.removeExerciseByName(name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int selectId(String name, String intensity) {
        try {
            return exerciseDao.getExerciseId(name, intensity);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public double selectMet(String name, String intensity) {
        try {
            return exerciseDao.getExerciseMet(name, intensity);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}

