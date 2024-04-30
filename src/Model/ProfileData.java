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
        double caloricIntake;

        switch (activityLevel) {
            case "Sedentary":
                caloricIntake = bmr * 1.2;
                break;
            case "Lightly Active":
                caloricIntake = bmr * 1.375;
                break;
            case "Moderately Active":
                caloricIntake = bmr * 1.55;
                break;
            case "Very Active":
                caloricIntake = bmr * 1.725;
                break;
            case "Super Active":
                caloricIntake = bmr * 1.9;
                break;
            default:
                caloricIntake = bmr * 1.2;
        }

        switch (goal) {
            case "Weight Loss":
                caloricIntake -= 500;
                break;
            case "Slight Weight Loss":
                caloricIntake -= 200;
                break;
            case "Maintain Weight":
                break;
            case "Slight Weight Gain":
                caloricIntake += 200;
                break;
            case "Weight Gain":
                caloricIntake += 500;
                break;
        }

        return caloricIntake;
    }
}