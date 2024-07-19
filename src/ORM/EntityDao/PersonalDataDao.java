package ORM.EntityDao;
import Model.Entities.PersonalData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonalDataDao {

    public int getUserInfoId(int userId) throws SQLException {
        String query = "SELECT id_info FROM users WHERE id_user = " + userId;
        ResultSet rs = ManagerDao.result(query);
        if (rs != null && rs.next()) {
            return rs.getInt("id_info");
        }
        return 0;
    }

    public void updatePersonalData(PersonalData pd) throws SQLException {
        String query = "UPDATE personaldata SET height = " + pd.getHeight() + ", weight = " + pd.getWeight() +
                ", gender = '" + pd.getGender() + "', age = " + pd.getAge() +
                ", activity = '" + pd.getActivity() + "', goals = '" + pd.getGoal() +
                "', meal_count = " + pd.getMealCount() + " WHERE id_info = " + pd.getId();
        ManagerDao.update(query);
    }

    public int insertPersonalData(PersonalData pd) throws SQLException {
        String query = "INSERT INTO personaldata (height, weight, gender, age, activity, goals, meal_count) " +
                "VALUES (" + pd.getHeight() + ", " + pd.getWeight() + ", '" + pd.getGender() +
                "', " + pd.getAge() + ", '" + pd.getActivity() + "', '" + pd.getGoal() +
                "', " + pd.getMealCount() + ") RETURNING id_info;";
        ResultSet rs = ManagerDao.select(query);
        if (rs != null && rs.next()) {
            return rs.getInt("id_info");
        }
        return 0;
    }

    public void deletePersonalData(int id) throws SQLException {
        String query = "DELETE FROM personaldata WHERE id_info = " + id;
        ManagerDao.update(query);
    }

    public PersonalData selectPersonalData(int id) throws SQLException {
        String query = "SELECT * FROM personaldata WHERE id_info = " + id;
        ResultSet rs = ManagerDao.result(query);
        if (rs.next()) {
            PersonalData pd = new PersonalData(rs.getFloat("height"), rs.getFloat("weight"), rs.getInt("age"),
                    rs.getString("gender"), rs.getString("activity"), rs.getString("goals"));
            pd.setMealCount(rs.getInt("meal_count"));
            pd.setId(rs.getInt("id_info"));
            return pd;
        }
        return null;
    }
}
