package Model.Entities;

public class Exercise {
    private int id;
    private final String name;
    private String intensity;
    private double time;
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
    public double getTime(){
        return time;
    }
    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }
    public void setTime(double time) {
        this.time = time;
    }
    public void setCalories(double calories){
        this.calories=calories;
    }
    public double countBurnCalories(int weight){  // da moltiplicare con il peso
        calories= met* time* weight*1.05;
        return calories;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }


}
