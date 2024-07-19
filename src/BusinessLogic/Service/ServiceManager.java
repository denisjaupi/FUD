package BusinessLogic.Service;
import Model.Entities.User;
import ORM.EntityDao.*;


public class ServiceManager {
    private static ServiceManager instance;
    private UserService us;
    private PersonalDataService psservice;
    private CalculateProfileDataService cpservice;
    private ActivityService acservice;
    private RecipeService recipeservice;
    private MealService mealservice;
    private FoodService foodservice;
    private ExerciseService exerciseservice;
    private DietService dietService;
    private FoodsMealService foodsMealService;
    private IngredientsService ingredientsService;
    private NutritionalInfoService nutritionalInfoService;
    private RecipeMealService recipeMealService;
    private User user;

    private ServiceManager() {
        UserDao userdao=new UserDao();
        RecipeMealDao recipemealdao= new RecipeMealDao();
        NutritionalInfoDao nutritionalInfoDao = new NutritionalInfoDao();
        IngredientsDao ingredientsDao = new IngredientsDao();
        DietsDao dietsDao = new DietsDao();
        FoodsMealDao foodsMealDao = new FoodsMealDao();
        FoodDao foodDao= new FoodDao();
        ExerciseDao exerciseDao= new ExerciseDao();
        ActivityDao activityDao= new ActivityDao();
        PersonalDataDao personalDataDao = new PersonalDataDao();
        CalculateProfileDataDao calculateProfileDataDao = new CalculateProfileDataDao();
        RecipeDao recipeDao = new RecipeDao();
        MealDao mealDao= new MealDao();
        acservice = new ActivityService(activityDao);
        cpservice = new CalculateProfileDataService(calculateProfileDataDao);
        recipeMealService= new RecipeMealService(recipemealdao);
        nutritionalInfoService= new NutritionalInfoService(nutritionalInfoDao);
        ingredientsService = new IngredientsService(ingredientsDao);
        foodsMealService= new FoodsMealService(foodsMealDao);
        exerciseservice= new ExerciseService(exerciseDao);
        foodservice= new FoodService(foodDao, nutritionalInfoService);
        recipeservice = new RecipeService(recipeDao, nutritionalInfoService, ingredientsService, foodservice);
        psservice= new PersonalDataService(personalDataDao, cpservice);
        us = new UserService(userdao, psservice);
        psservice.setUserService(us);
        mealservice = new MealService(mealDao, nutritionalInfoService,  dietService, foodsMealService, recipeservice, foodservice, recipeMealService, ingredientsService);
        dietService = new DietService(dietsDao, mealservice);
    }


    public static ServiceManager getInstance() {
        if (instance == null)
            instance = new ServiceManager();
        return instance;
    }

    public UserService getUserService() {
        return us;
    }
    public RecipeService getRecipeService() {
        return recipeservice;
    }
    public PersonalDataService getPersonalDataService() {
        return psservice;
    }
    public CalculateProfileDataService getCalculateProfileDataService() {
        return cpservice;
    }
    public ActivityService getActivityService(){
        return acservice;
    }
    public MealService getMealService(){
        return mealservice;
    }
    public FoodService getFoodService(){
        return foodservice;
    }
    public ExerciseService getExerciseService(){
        return exerciseservice;
    }
    public DietService getDietService(){
        return dietService;
    }
    public FoodsMealService getFoodsMealService(){
        return foodsMealService;
    }
    public IngredientsService getIngredientsService(){
        return ingredientsService;
    }

    public NutritionalInfoService getNutritionalInfoService() {
        return nutritionalInfoService;
    }

    public RecipeMealService getRecipeMealService(){
        return recipeMealService;
    }

}
