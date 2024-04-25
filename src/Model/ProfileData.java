package Model;

public class ProfileData {
    private int height;
    private int weight;
    private int age;
    private String gender;
    private String activityLevel;
    private String goal;
    private CalculatedProfileData calculatedProfileData;

    public ProfileData(int height, int weight, int age, String gender, String activityLevel, String goal) {
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.activityLevel = activityLevel;
        this.goal = goal;
        calculateAndStoreProfileData();
    }

    // getters
    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getActivityLevel() {
        return activityLevel;
    }

    public String getGoal() {
        return goal;
    }

    public CalculatedProfileData getCalculatedProfileData() {
        return calculatedProfileData;
    }

    private void calculateAndStoreProfileData() {
        String bmr = String.format("%.3f", calculateBMR());
        String bmi = String.format("%.3f", calculateBMI());
        String waterRequirement = String.format("%.3f", calculateWaterRequirement());
        String caloricIntake = String.format("%.3f", calculateCaloricIntake());
        calculatedProfileData = new CalculatedProfileData(bmi, waterRequirement, bmr, caloricIntake);
    }

    // Calcolo del BMR usando la formula di Mifflin-St Jeor
    private double calculateBMR() {
        if ("M".equals(gender)) {
            return 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }
    }

    // Calcolo del BMI
    private double calculateBMI() {
        double heightInMeters = height / 100.0;
        return weight / (heightInMeters * heightInMeters);
    }

    // Calcolo del fabbisogno d'acqua
    private double calculateWaterRequirement() {
        return weight * 0.033; // in litri
    }

    // Calcolo dell'intake calorico giornaliero basato sul livello di attività
    private double calculateCaloricIntake() {
        double bmr = calculateBMR();
        switch (activityLevel) {
            case "Sedentary":
                return bmr * 1.2;
            case "Lightly Active":
                return bmr * 1.375;
            case "Moderately Active":
                return bmr * 1.55;
            case "Very Active":
                return bmr * 1.725;
            case "Super Active":
                return bmr * 1.9;
            default:
                return bmr * 1.2; // caso di default
        }
    }
}