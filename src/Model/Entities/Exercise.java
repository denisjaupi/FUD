package Model.Entities;

public class Exercise {
    private int id;
    private final String name;
    private String intensity;
    private float time;
    private double calories;
    final double met;

    public Exercise(int id, String name, double met) {
        this.id = id;
        this.name = name;
        this.met = met;
    }

    public String getIntensity(){
        return intensity;
    }

    public float getTime(){
        return time;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public void setCalories(double calories){
        this.calories=calories;
    }

    public double countBurnCalories(double weight){  // da moltiplicare con il peso
        calories= met* time * weight*1.05;
        return calories;
    }

    public double getCalories(){
        return calories;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


}