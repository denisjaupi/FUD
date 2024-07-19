import Controller.Engine;
import Model.Entities.User;
import BusinessLogic.Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EngineTest {
    private Engine engine;
    private UserService mockUserService;
    private PersonalDataService mockPersonalDataService;
    private ActivityService mockActivityService;
    private RecipeService mockRecipeService;
    private MealService mockMealService;
    private DietService mockDietService;
    private ExerciseService mockExerciseService;
    private FoodService mockFoodService;

    @BeforeEach
    public void setUp() {
        engine = Engine.getInstance();

        // Mocking services
        mockUserService = mock(UserService.class);
        mockPersonalDataService = mock(PersonalDataService.class);
        mockActivityService = mock(ActivityService.class);
        mockRecipeService = mock(RecipeService.class);
        mockMealService = mock(MealService.class);
        mockDietService = mock(DietService.class);
        mockExerciseService = mock(ExerciseService.class);
        mockFoodService = mock(FoodService.class);

        ServiceFactory mockServiceFactory = mock(ServiceFactory.class);

        when(mockServiceFactory.getService("UserService")).thenReturn(mockUserService);
        when(mockServiceFactory.getService("PersonalDataService")).thenReturn(mockPersonalDataService);
        when(mockServiceFactory.getService("ActivityService")).thenReturn(mockActivityService);
        when(mockServiceFactory.getService("RecipeService")).thenReturn(mockRecipeService);
        when(mockServiceFactory.getService("MealService")).thenReturn(mockMealService);
        when(mockServiceFactory.getService("DietService")).thenReturn(mockDietService);
        when(mockServiceFactory.getService("ExerciseService")).thenReturn(mockExerciseService);
        when(mockServiceFactory.getService("FoodService")).thenReturn(mockFoodService);

        // Using reflection to set the private ServiceFactory instance in Engine
        try {
            java.lang.reflect.Field sfField = Engine.class.getDeclaredField("sf");
            sfField.setAccessible(true);
            sfField.set(engine, mockServiceFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testLogin_Success() throws SQLException {
        String email = "dario";
        String password = "dario";

        when(mockUserService.checkCredentials(email, password)).thenReturn(true);
        User mockUser = new User();
        when(mockUserService.getCurrentUser()).thenReturn(mockUser);

        boolean result = engine.login(email, password);

        assertTrue(result);
        assertNotNull(engine.getUser());
        verify(mockUserService).login(email, password);
        verify(mockActivityService).setUser(mockUser);
        verify(mockRecipeService).setUser(mockUser);
        verify(mockMealService).setUser(mockUser);
        verify(mockDietService).setUser(mockUser);
        verify(mockActivityService).selectActivities();
        verify(mockDietService).select();
        verify(mockRecipeService).loadUserRecipes();
        verify(mockPersonalDataService).setUser();
    }

    @Test
    public void testLogin_Failure() throws SQLException {
        String email = "dario";
        String password = "Pippo";

        when(mockUserService.checkCredentials(email, password)).thenReturn(false);

        boolean result = engine.login(email, password);

        assertFalse(result);
        assertNull(engine.getUser());
    }

    @Test
    public void testRegister_Success() {
        String email = "newuser@example.com";
        String password = "NewPassword123!";
        String username = "NewUser123!";

        when(mockUserService.checkCredentials(email, password)).thenReturn(false);
        when(mockUserService.checkUserName(username)).thenReturn(false);

        boolean result = engine.register(email, password, username);

        assertTrue(result);
        verify(mockUserService).register(email, password, username);
    }

    @Test
    public void testRegister_Failure() {
        String email = "dario";
        String password = "password";
        String username = "dario";

        when(mockUserService.checkCredentials(email, password)).thenReturn(true);

        boolean result = engine.register(email, password, username);

        assertFalse(result);

        when(mockUserService.checkCredentials(email, password)).thenReturn(false);
        when(mockUserService.checkUserName(username)).thenReturn(true);

        result = engine.register(email, password, username);

        assertFalse(result);
    }

    @Test
    public void testSelectId_exercise() {
        String name = "Calcio";
        String intensity = "Alta";
        int expectedId = 1;

        when(mockExerciseService.selectId(name, intensity)).thenReturn(expectedId);

        int result = engine.selectId_exercise(name, intensity);

        assertEquals(expectedId, result);
    }

    @Test
    public void testSelectMet_exercise() {
        String name = "Surf";
        String intensity = "Moderata";
        double expectedMet = 9.8;

        when(mockExerciseService.selectMet(name, intensity)).thenReturn(expectedMet);

        double result = engine.selectMet_exercise(name, intensity);

        assertEquals(expectedMet, result);
    }

    @Test
    public void testAddActivity() throws SQLException {
        int id = 1;
        double calories = 500.0;
        float time = 30.0f;

        when(mockActivityService.addActivity(id, calories, time)).thenReturn(true);

        boolean result = engine.addActivity(id, calories, time);

        assertTrue(result);
    }

    @Test
    public void testAddFood_db() {
        String name = "Tacchino";
        double calories = 100.0;
        double proteins = 12.0;
        double carbohydrates = 0.5;
        double fats = 2.0;

        engine.addFood_db(name, calories, proteins, carbohydrates, fats);

        verify(mockFoodService).addFood_Db(name, calories, proteins, carbohydrates, fats);
    }

    @Test
    public void testSelectId_food() {
        String name = "Mela";
        int expectedId = 1;

        when(mockFoodService.selectId(name)).thenReturn(expectedId);

        int result = engine.selectId_food(name);

        assertEquals(expectedId, result);
    }

    @Test
    public void testGetAll_food() {
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockFoodService.getFood()).thenReturn(mockResultSet);

        ResultSet result = engine.getAll_food();

        assertEquals(mockResultSet, result);
    }

    @Test
    public void testDeleteUser() throws SQLException {
        int userId = 1;

        engine.deleteUser(userId);

        verify(mockUserService).deleteUser(userId);
    }

    @Test
    public void testAddPersonalData() throws SQLException {
        float height = 180.0f;
        float weight = 75.0f;
        int age = 30;
        String gender = "M";
        String activityLevel = "Moderately Active";
        String goal = "Maintain Weight";
        int mealCount = 3;

        engine.addPersonalData(height, weight, age, gender, activityLevel, goal, mealCount);

        verify(mockPersonalDataService).addOrUpdatePersonalData(age, weight, height, gender, activityLevel, goal, mealCount);
    }

    @Test
    public void testLogout() {
        engine.logout();
        assertNull(engine.getUser());
    }

    @Test
    public void testGetAll_exercise() {
        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockExerciseService.getExercise()).thenReturn(mockResultSet);

        ResultSet result = engine.getAll_exercise();

        assertEquals(mockResultSet, result);
    }
}
