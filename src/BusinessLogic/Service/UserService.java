package BusinessLogic.Service;

import ORM.EntityDao.UserDao;
import Model.Entities.User;
import Model.Entities.PersonalData;

import java.sql.SQLException;

public class UserService {
    private UserDao userDao;
    private User user;
    PersonalDataService personaldataS;

    public UserService(UserDao userDao, PersonalDataService pds) {
        this.userDao = userDao;
        this.personaldataS=pds;
        user=new User();
    }

    public boolean checkCredentials(String email, String psw){
        try {
             boolean a=  userDao.checkCredentials(email, psw);
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public void login(String email, String password) throws SQLException {
            user = userDao.selectUserByEmail(email);
            if (user.getId_persnalData() != 0) {
                PersonalData pd= personaldataS.selectPersonalData(user.getId_persnalData());
                user.setPersonalData(pd);
            }
    }
    public void addInfo(int id_user, int id_info) {
        userDao.addInfo(id_user, id_info);
    }


    public void updateUser(int id_user,String username, String email, String password) {
        userDao.updateUser( id_user, username, email, password);
    }

    public void deleteUser(int id) throws SQLException {
        userDao.deleteUser(id);
        personaldataS.deletePersonalData(id);  // Chiamare una funzione di un altro servizio
    }

    public User getCurrentUser(){
        return user;
    }

    public void register(String email, String psw, String userName) {
        userDao.addUser(email, psw, userName);
    }

    public boolean checkUserName(String userName) {
        try {
            return userDao.checkUserName(userName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}