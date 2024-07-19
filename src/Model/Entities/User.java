package Model.Entities;

public class User {
    private int id;
    private String email;
    private String password;
    private String userName;
    private PersonalData pd = new PersonalData();
    private RecipeList recipe;
    private DailyCount dailyCount;
    int id_persnalData=0;

    public User() {
        dailyCount= new DailyCount(this.userName);
        recipe= new RecipeList(this.userName);
    }

    public User(String email, String password, String userName) {
        this.email = email;
        this.password = password;
        this.userName = userName;
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

    public void setId_persnalData(int id_persnalData) {
        this.id_persnalData = id_persnalData;
    }

    public int getId_persnalData() {
        return id_persnalData;
    }
}

