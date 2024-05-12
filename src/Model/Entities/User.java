package Model.Entities;

public class User {
    private long id;
    private String email;
    private String password;
    private String userName;
    private PersonalData pd;
    private RecipeList recipe;
    private DailyCount dailyCount;

    public User(long id, String email, String password, String userName, PersonalData pd) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.pd = pd;
        dailyCount= new DailyCount(this.userName);
        recipe= new RecipeList(this.userName);
    }
    public long getId() {
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

