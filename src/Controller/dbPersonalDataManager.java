package Controller;

import Model.Database.Db;
import Model.Entities.PersonalData;
import Model.Entities.User;
import Model.Util.CalculatedProfileData;

import java.sql.ResultSet;
import java.sql.SQLException;

public class dbPersonalDataManager {
    private static PersonalData pd;
    private static User user;


    public void setUser(User u){
        user = u;
    }

    public static void addPersonalData(int age, int weight, int height, String gender, String activity, String goals, int meal_count) throws SQLException {
        String query="insert into personaldata (height, weight, gender, age, activity, goals, meal_count) values("+height+", "+weight+","+gender+","+age+","+activity+","+goals+","+meal_count+")";
        Db.result(query);
        ResultSet rs = Db.result("SELECT LAST_INSERT_ID()");
        pd=new PersonalData(height, weight, age, gender, activity, goals, meal_count);
        pd.setId(rs.getInt(1));
        CalculatedProfileData cpd =pd.getCalculatedProfileData();
        dbCalculateProfileDataManager.addCalculateProfileData(cpd.getBmi(), cpd.getWaterRequirement(), cpd.getBmr(), cpd.getCaloricIntake(), pd.getId());
        user.setPersonalData(pd);
        dbUserManager.addinfo(user.getId(), rs.getInt(1));
    }


    public static void deletePersonalData(int id){
        String query="delete from personaldata where id_info="+id;
        Db.result(query);
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
            pd = new PersonalData(rs.getDouble("height"), rs.getDouble("weight"), rs.getInt("age"), rs.getString("gender"), rs.getString("activity"), rs.getString("goals"), rs.getInt("meal_count"));
            pd.setId(rs.getInt("id_info"));
            user.setPersonalData(pd);
        }
    }
}
