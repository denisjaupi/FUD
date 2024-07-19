package BusinessLogic.Service;

import Model.Entities.User;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        if (instance == null)
            instance = new ServiceFactory();
        return instance;
    }

    public Object getService(String service) {
        if ("UserService".equals(service)) {
            return ServiceManager.getInstance().getUserService();
        } else if ("PersonalDataService".equals(service)) {
            return ServiceManager.getInstance().getPersonalDataService();
        } else if ("CalculateProfileDataService".equals(service)) {
            return ServiceManager.getInstance().getCalculateProfileDataService();
        } else if ("ActivityService".equals(service)) {
            return ServiceManager.getInstance().getActivityService();
        } else if ("RecipeService".equals(service)) {
            return ServiceManager.getInstance().getRecipeService();
        } else if ("MealService".equals(service)) {
            return ServiceManager.getInstance().getMealService();
        } else if ("FoodService".equals(service)) {
            return ServiceManager.getInstance().getFoodService();
        } else if ("ExerciseService".equals(service)) {
            return ServiceManager.getInstance().getExerciseService();
        } else if ("DietService".equals(service)) {
            return ServiceManager.getInstance().getDietService();
        } else if ("FoodsMealService".equals(service)) {
            return ServiceManager.getInstance().getFoodsMealService();
        } else if ("IngredientsService".equals(service)) {
            return ServiceManager.getInstance().getIngredientsService();
        } else if ("NutritionalInfoService".equals(service)) {
            return ServiceManager.getInstance().getNutritionalInfoService();
        } else if ("RecipeMealService".equals(service)) {
            return ServiceManager.getInstance().getRecipeMealService();
        } else {
            throw new IllegalArgumentException("Invalid service");
        }
    }

}
