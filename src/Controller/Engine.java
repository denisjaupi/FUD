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
        dbActiv=new dbActivitiesManager();
        dbEM=new dbExerciseManager();
        dbIM=new dbIngredientsManager();
        dbFMM=new dbFoodsMealManager();
        dbRMM=new dbRecipeMealManager();
        dbRM=new dbRecipeManager();
        dbDM=new dbDietsManager();
        dbMM=new dbMealManager();
        dbFM=new dbFoodManager();
        dbNIM=new dbNutritionalInfoManager();
        dbCPDM=new dbCalculateProfileDataManager();
        dbUM=new dbUserManager();
        dbPDM=new dbPersonalDataManager();

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
