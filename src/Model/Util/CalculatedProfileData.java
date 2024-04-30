package Model.Util;

public class CalculatedProfileData {
    private String bmi;
    private String waterRequirement;
    private String bmr;
    private String caloricIntake;

    public CalculatedProfileData(String bmi, String waterRequirement, String bmr, String caloricIntake) {
        this.bmi = bmi;
        this.waterRequirement = waterRequirement;
        this.bmr = bmr;
        this.caloricIntake = caloricIntake;
    }

    // getters
    public String getBmi() {
        return bmi;
    }

    public String getWaterRequirement() {
        return waterRequirement;
    }

    public String getBmr() {
        return bmr;
    }

    public String getCaloricIntake() {
        return caloricIntake;
    }
}
