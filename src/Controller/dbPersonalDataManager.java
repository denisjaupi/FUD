package Controller;

import Model.Database.Db;

public class dbPersonalDataManager {


    public static void addPersonalData( int age, int weight, int height, String gender, String activity, String goals, int meal_count){
        String query="insert into personaldata (height, weight, gender, age, activity, goals, meal_count) values("+height+", "+weight+","+gender+","+age+","+activity+","+goals+","+meal_count+")";
        Db.result(query);
    }
    public static void deletePersonalData(int id){
        String query="delete from personaldata where id_info="+id;
        Db.result(query);
    }
    public static void updatePersonalData(int id, int age, int weight, int height, String activity, String gender, String goals, int meal_count) {
        String query = "update personaldata set height=" + height + ", weight=" + weight + ", activity=" + activity + ", gender=" + gender + ", goals=" + goals + ", meal_count=" + meal_count + " where id_info=" + id;
        Db.result(query);
    }
}
