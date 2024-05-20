package Model.Util;

public class CalculatedProfileData {
    private double bmi;
    private double waterRequirement;
    private double bmr;
    private double caloricIntake;

    public CalculatedProfileData(){}

    public CalculatedProfileData(double bmi, double waterRequirement, double bmr, double caloricIntake) {
        this.bmi = bmi;
        this.waterRequirement = waterRequirement;
        this.bmr = bmr;
        this.caloricIntake = caloricIntake;
    }

    // getters
    public double getBmi() {
        return bmi;
    }

    public double getWaterRequirement() {
        return waterRequirement;
    }

    public double getBmr() {
        return bmr;
    }

    public double getCaloricIntake() {
        return caloricIntake;
    }
}
