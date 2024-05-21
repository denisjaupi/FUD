package Controller;

import Model.Entities.User;

import java.sql.SQLException;

public class Engine {
    private User user;
    dbActivitiesManager dbActiv;
    dbPersonalDataManager dbPDM;
    dbUserManager dbUM;
    dbCalculateProfileDataManager dbCPDM;
    dbNutritionalInfoManager dbNIM;
    dbFoodManager dbFM;
    dbMealManager dbMM;
    dbDietsManager dbDM;
    dbRecipeManager dbRM;
    dbRecipeMealManager dbRMM;
    dbFoodsMealManager dbFMM;
    dbIngredientsManager dbIM;
    dbExerciseManager dbEM;


    public Engine() {
        dbFM=new dbFoodManager();
        dbNIM=new dbNutritionalInfoManager();
        dbCPDM=new dbCalculateProfileDataManager();
        dbUM=new dbUserManager();
        dbPDM=new dbPersonalDataManager();
        dbEM=new dbExerciseManager();
        dbActiv=new dbActivitiesManager();
        dbRM=new dbRecipeManager();
        dbMM=new dbMealManager();
        dbDM=new dbDietsManager();

    }

    public boolean register(User u) {
        try {
            if (dbUserManager.checkCredentials(u.getEmail(), u.getPassword())) {
                return false;
            }
            dbUserManager.addUser(u.getUserName(), u.getEmail(), u.getPassword());
            return true;
        }catch (SQLException e){
            System.err.println("Errore durante la registrazione: " + e.getMessage());
            return false;
        }
    }

    public boolean login(String email, String password) {
        try {
            if (dbUserManager.checkCredentials(email, password)) {
                user = new User();
                dbUM.setUser(user);
                dbPDM.setUser(user);

                dbUM.selectData(email);
                user = dbUM.getUser();

                dbActiv.setUser(user);
                dbActiv.select();

                dbRM.setUser(user);
                dbMM.setUser(user);
                dbDM.setUser(user);
                dbDM.select();

                dbRM.select();

                return true;
            }
            return false;
        }catch (SQLException e){
            System.err.println("Errore durante il login: " + e.getMessage());
            return false;
        }
    }


}
