package ORM.EntityDao;



import java.sql.SQLException;

public class CalculateProfileDataDao {

    public void addCalculateProfileData(double bmi, double water, double bmr, double caloricIntake, int idInfo) throws SQLException {
        String query = "INSERT INTO calculateprofiledata (bmi, water, bmr, caloricintake, id_info) VALUES (" + bmi + ", " + water + ", " + bmr + ", " + caloricIntake + ", " + idInfo + ")";
        ManagerDao.result(query);
    }

    public void updateValues(double bmi, double water, double bmr, double caloricIntake, int idInfo) throws SQLException {
        String query = "UPDATE calculateprofiledata SET bmi = " + bmi + ", water = " + water + ", bmr = " + bmr + ", caloricintake = " + caloricIntake + " WHERE id_info = " + idInfo;
        ManagerDao.result(query);
    }

    public void deleteCalculateProfileData(int idInfo) throws SQLException {
        String query = "DELETE FROM calculateprofiledata WHERE id_info = " + idInfo;
        ManagerDao.result(query);
    }
}

