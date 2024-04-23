package Model.Entities;

public class PersonalData{
    private double height;
    private double weight;
    private int age;
    private String gender;
    private int activity;  //quante volte ti alleni
    private String goal;

    public PersonalData(double height, double weight, int age, String gender, int activity, String goal){
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.gender = gender;
        this.activity = activity;
        this.goal = goal;
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
    public int getActivity(){
        return activity;
    }
    public void setActivity(int activity){
        this.activity = activity;
    }
    public String getGoal(){
        return goal;
    }
    public void setGoal(String goal){
        this.goal = goal;
    }
}

