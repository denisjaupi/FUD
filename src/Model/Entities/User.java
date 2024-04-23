package Model.Entities;

public class User {
    private long id;
    private String email;
    private String password;
    private String userName;
    private PersonalData pd;

    public User(long id, String email, String password, String userName, PersonalData pd) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.pd = pd;
    }
    public long getId() {
        return id;
    }

}

