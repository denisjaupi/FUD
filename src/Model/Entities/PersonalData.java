package Model.Entities;

import Model.Util.CalculatedProfileData;

public class PersonalData{
    private double height;
    private double weight;
    private int age;
    private String gender;
    private String activity;  //quante volte ti alleni
    private String goal;
    private CalculatedProfileData calculateProfileData;
    private int count_meal;

    public PersonalData(double height, double weight, int age, String gender, String activity, String goal, int count_meal){
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.activity = activity;
        this.goal = goal;
        calculateAndProfileData();
        this.count_meal = count_meal;
    }
    public int getCount_meal(){
        return count_meal;
    }

    public double getHeight(){
        return height;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public String getGender(){
        return gender;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getActivity(){
        return activity;
    }

    public void setActivity(String activity){
        this.activity = activity;
    }

    public String getGoal(){
        return goal;
    }

    public void setGoal(String goal){
        this.goal = goal;
    }

    public CalculatedProfileData getCalculatedProfileData(){
        return calculateProfileData;
    }

    private void calculateAndProfileData(){
        String bmr=String.format("%.3f", calculateBMR());
        String bmi=String.format("%.3f", calculateBMI());
        String waterRequirement=String.format("%.3f", calculateWaterRequirement());
        String caloricIntake=String.format("%.3f", calculateCaloricIntake());
        calculateProfileData=new CalculatedProfileData(bmi, waterRequirement,bmr, caloricIntake);
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
        switch (activity) {
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

