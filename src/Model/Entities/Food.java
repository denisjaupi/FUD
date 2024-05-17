package Model.Entities;

public class Food {
    private long id;
    private final String name;
    private  NutritionalInfo info;
    private int quantity;

    public Food(int id,  String n, NutritionalInfo i) {
        this.id = id;
        this.name = n;
        this.info=i; //Nutritional info for 100g
    }
    public NutritionalInfo getNutritionalInfo(){
        return info;
    }
    public void setQuantity(int q) {
        this.quantity = q;
    }
    public int getQuantity() {
        return quantity;
    }
    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }

    public NutritionalInfo countNutritionalInfo(NutritionalInfo oldI){
        double calories = (oldI.calories / 100.0) * quantity;
        double proteins=(oldI.proteins / 100.0) * quantity;
        double fats=(oldI.fats / 100.0) * quantity;
        double carbohydrates=(oldI.carbohydrates / 100.0) * quantity;
        return new NutritionalInfo(calories,proteins,fats,carbohydrates);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Food food = (Food) obj;
        return id == food.id;
    }

}
