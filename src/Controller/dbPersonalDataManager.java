package Controller;

import Model.Database.Db;
import Model.Entities.PersonalData;
import Model.Entities.User;
import Model.Util.CalculatedProfileData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dbPersonalDataManager {
    private static PersonalData pd;
    private static User user;


    public void setUser(User u){
        user = u;
    }

    public static void addPersonalData(int age, double weight, double height, String gender, String activity, String goals, int meal_count) throws SQLException {
        String selectQuery = "SELECT id_info FROM users WHERE id_user = " + user.getId();
        ResultSet rs = Db.result(selectQuery);
        String query;
        Connection connection = null;

        try {
            if (rs != null && rs.next()) {
                int userIdInfo = rs.getInt("id_info");
                if (userIdInfo != 0) { // Controlla se l'ID_info non è nullo
                    // L'utente ha già un id_info non nullo, quindi aggiorna i dati esistenti
                    query = "UPDATE personaldata SET height = " + height + ", weight = " + weight + ", gender = '" + gender + "', age = " + age + ", activity = '" + activity + "', goals = '" + goals + "', meal_count = " + meal_count + " WHERE id_info = " + userIdInfo;
                    Db.update(query);

                    // Aggiorna i valori nella tabella CalculateProfileData
                    PersonalData pd = new PersonalData(height, weight, age, gender, activity, goals);
                    pd.setMealCount(meal_count);
                    pd.setId(userIdInfo);
                    CalculatedProfileData cpd = pd.getCalculatedProfileData();
                    dbCalculateProfileDataManager.updateValues(cpd.getBmi(), cpd.getWaterRequirement(), cpd.getBmr(), cpd.getCaloricIntake(), userIdInfo);
                    user.setPersonalData(pd);
                } else {
                    // L'utente non ha un id_info o è nullo, quindi inserisci nuovi dati
                    query = "INSERT INTO personaldata (height, weight, gender, age, activity, goals, meal_count) " +
                            "VALUES (" + height + ", " + weight + ", '" + gender + "', " + age + ", '" + activity + "', '" + goals + "', " + meal_count + ") RETURNING id_info;";
                    ResultSet insertResult = Db.select(query);
                    if (insertResult != null && insertResult.next()) {
                        int lastId = insertResult.getInt("id_info");
                        PersonalData pd = new PersonalData(height, weight, age, gender, activity, goals);
                        pd.setMealCount(meal_count);
                        pd.setId(lastId);
                        CalculatedProfileData cpd = pd.getCalculatedProfileData();
                        dbCalculateProfileDataManager.addCalculateProfileData(cpd.getBmi(), cpd.getWaterRequirement(), cpd.getBmr(), cpd.getCaloricIntake(), lastId);
                        user.setPersonalData(pd);
                        dbUserManager.addinfo(user.getId(), lastId);
                    }
                }
            }
        } finally {
            if (rs != null) rs.close();
            if (connection != null) connection.close();
        }
    }





    public static void deletePersonalData(int id) throws SQLException {
        String query="delete from personaldata where id_info="+id;
        Db.update(query);
    }


    public static void updatePersonalData(int id, int age, int weight, int height, String activity, String gender, String goals, int meal_count) {
        String query = "update personaldata set height=" + height + ", weight=" + weight + ", activity=" + activity + ", gender=" + gender + ", goals=" + goals + ", meal_count=" + meal_count + " where id_info=" + id;
        Db.result(query);
        user.getPersonalData().setHeight(height);
        user.getPersonalData().setWeight(weight);
        user.getPersonalData().setAge(age);
        user.getPersonalData().setGender(gender);
        user.getPersonalData().setActivity(activity);
        user.getPersonalData().setId(id);
        user.getPersonalData().setCount_meal(meal_count);
        user.getPersonalData().setGoal(goals);
        user.getPersonalData().calculateAndProfileData();
        dbCalculateProfileDataManager.updateValues(user.getPersonalData().getCalculatedProfileData().getBmi(),
                user.getPersonalData().getCalculatedProfileData().getWaterRequirement(), user.getPersonalData().getCalculatedProfileData().getBmr(),
                user.getPersonalData().getCalculatedProfileData().getCaloricIntake(), id);
    }

    public void selectPersonalData(int id) throws SQLException {
        String query = "SELECT * FROM personaldata WHERE id_info = " + id;
        ResultSet rs = Db.result(query);
        if (rs.next()) {
            pd = new PersonalData(rs.getDouble("height"), rs.getDouble("weight"), rs.getInt("age"), rs.getString("gender"), rs.getString("activity"), rs.getString("goals"));
            pd.setMealCount(rs.getInt("meal_count"));
            pd.setId(rs.getInt("id_info"));
            user.setPersonalData(pd);
        }
    }
}
