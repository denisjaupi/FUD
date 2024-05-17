package Controller;

import Model.Database.Db;

public class dbCalculateProfileDataManager {


    public static void addCalculateProfileData(double bmi, double water, double bmr, double caloricintake, int id_info){
        String query = "INSERT INTO calculateprofiledata (bmi, water, bmr, caloricintake, id_info) VALUES (" + bmi + ", " + water + ", " + bmr + ", " + caloricintake + ", " + id_info + ")";
        Db.result(query);
    }

    public static void updateValues(double bmi, double water, double bmr, double caloricintake, int id_info){
        String query = "UPDATE calculateprofiledata SET bmi = " + bmi + ", water = " + water + ", bmr = " + bmr + ", caloricintake = " + caloricintake + " WHERE id_info = " + id_info;
        Db.result(query);
    }

    public static void deleteCalculateProfileData(int id_info) {
        String query = "DELETE FROM calculateprofiledata WHERE id_info = " + id_info;
        Db.result(query);
    }
}
