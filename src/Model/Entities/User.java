package Model.Entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String userName;
    private PersonalData pd;
    private RecipeList recipe;
    private DailyCount dailyCount;

    public User() {}
    public User(int id, String email, String password, String userName, PersonalData pd) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.pd = pd;
        dailyCount= new DailyCount(this.userName);
        recipe= new RecipeList(this.userName);
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPersonalData(PersonalData pd) {
        this.pd = pd;
    }
    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getUserName() {
        return userName;
    }
    public PersonalData getPersonalData() {
        return pd;
    }
    public DailyCount getDailyCount() {
        return dailyCount;
    }
    public RecipeList getRecipe() {
        return recipe;
    }


}

