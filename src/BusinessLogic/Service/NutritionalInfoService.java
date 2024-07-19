package BusinessLogic.Service;


import ORM.EntityDao.NutritionalInfoDao;
import Model.Entities.NutritionalInfo;

import java.sql.SQLException;

public class NutritionalInfoService {

    private NutritionalInfoDao nutritionalInfoDao;

    public NutritionalInfoService(NutritionalInfoDao nutritionalInfoDao) {
        this.nutritionalInfoDao = nutritionalInfoDao;
    }

    public int addNutritionalInfo(NutritionalInfo info) {
        try {
            return nutritionalInfoDao.addNutritionalInfo(info);
        } catch (SQLException e) {
            System.out.println("Errore durante l'inserimento delle informazioni nutrizionali nel database");
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteNutritionalInfo(int id) {
        try {
            nutritionalInfoDao.deleteNutritionalInfo(id);
        } catch (SQLException e) {
            System.out.println("Errore durante l'eliminazione delle informazioni nutrizionali dal database");
            e.printStackTrace();
        }
    }
}

