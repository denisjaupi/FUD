package BusinessLogic.Service;


import ORM.EntityDao.CalculateProfileDataDao;

import java.sql.SQLException;

public class CalculateProfileDataService {

    private CalculateProfileDataDao calculateProfileDataDao;

    public CalculateProfileDataService(CalculateProfileDataDao calculateProfileDataDao) {
        this.calculateProfileDataDao = calculateProfileDataDao;
    }

    public void addCalculateProfileData(double bmi, double water, double bmr, double caloricIntake, int idInfo) {
        try {
            calculateProfileDataDao.addCalculateProfileData(bmi, water, bmr, caloricIntake, idInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestisci l'errore, ad esempio lanciando un'eccezione personalizzata
        }
    }

    public void updateValues(double bmi, double water, double bmr, double caloricIntake, int idInfo) {
        try {
            calculateProfileDataDao.updateValues(bmi, water, bmr, caloricIntake, idInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestisci l'errore, ad esempio lanciando un'eccezione personalizzata
        }
    }

    public void deleteCalculateProfileData(int idInfo) {
        try {
            calculateProfileDataDao.deleteCalculateProfileData(idInfo);
        } catch (SQLException e) {
            e.printStackTrace();
            // Gestisci l'errore, ad esempio lanciando un'eccezione personalizzata
        }
    }
}
