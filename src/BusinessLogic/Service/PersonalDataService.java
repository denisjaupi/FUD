package BusinessLogic.Service;

import ORM.EntityDao.PersonalDataDao;
import Model.Entities.PersonalData;
import Model.Entities.User;
import Model.Util.CalculatedProfileData;

import java.sql.SQLException;

public class PersonalDataService {
    private User user;
    private PersonalDataDao personalDataDao ;
    private CalculateProfileDataService calculateProfileDataS;
    private UserService userService;

    public PersonalDataService(PersonalDataDao personalDataDao, CalculateProfileDataService calculateProfileDataS) {
        this.personalDataDao= personalDataDao;
        this.calculateProfileDataS = calculateProfileDataS;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    public void setUser() {
        this.user = userService.getCurrentUser();
        ;
    }

    public void addOrUpdatePersonalData(int age, float weight, float height, String gender, String activity, String goals, int meal_count) throws SQLException {
        int userIdInfo = personalDataDao.getUserInfoId(user.getId());
        PersonalData pd = new PersonalData(height, weight, age, gender, activity, goals);
        pd.setMealCount(meal_count);

        if (userIdInfo != 0) {
            // Aggiorna i dati esistenti
            pd.setId(userIdInfo);
            personalDataDao.updatePersonalData(pd);

            // Aggiorna i valori nella tabella CalculateProfileData
            CalculatedProfileData cpd = pd.getCalculatedProfileData();
            calculateProfileDataS.updateValues(cpd.getBmi(), cpd.getWaterRequirement(), cpd.getBmr(), cpd.getCaloricIntake(), userIdInfo);
        } else {
            // Inserisci nuovi dati
            int lastId = personalDataDao.insertPersonalData(pd);
            pd.setId(lastId);
            CalculatedProfileData cpd = pd.getCalculatedProfileData();
            calculateProfileDataS.addCalculateProfileData(cpd.getBmi(), cpd.getWaterRequirement(), cpd.getBmr(), cpd.getCaloricIntake(), lastId);
            userService.addInfo(user.getId(), lastId);
        }
        user.setPersonalData(pd);
    }

    public void deletePersonalData(int id) throws SQLException {
        personalDataDao.deletePersonalData(id);
    }

    public void updatePersonalData(int id, int age, int weight, int height, String activity, String gender, String goals, int meal_count) throws SQLException {
        PersonalData pd = new PersonalData(height, weight, age, gender, activity, goals);
        pd.setMealCount(meal_count);
        pd.setId(id);

        personalDataDao.updatePersonalData(pd);

        // Aggiorna i valori nella tabella CalculateProfileData
        user.setPersonalData(pd);
        CalculatedProfileData cpd = pd.getCalculatedProfileData();
        calculateProfileDataS.updateValues(cpd.getBmi(), cpd.getWaterRequirement(), cpd.getBmr(), cpd.getCaloricIntake(), id);
    }

    public PersonalData selectPersonalData(int id) throws SQLException {
        return personalDataDao.selectPersonalData(id);
    }
}

