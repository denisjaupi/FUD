package Model.Entities;

public class NutritionalInfo {

    double calories;
    double proteins;
    double fats;
    double carbohydrates;

    public NutritionalInfo(double cal, double p, double f, double car) {
        this.calories = cal;
        this.proteins = p;
        this.fats = f;
        this.carbohydrates = car;
    }
    public double getCalories(){
        return calories;
    }
    public double getProteins(){
        return proteins;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getFats() {
        return fats;
    }
}

