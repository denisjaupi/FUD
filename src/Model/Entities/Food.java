package Model.Entities;

public class Food {
    private long id;
    private long idType;
    private final String name;
    NutritionalInfo info;
    private int quantity;

    public Food(int id, int idType, String n, NutritionalInfo i, int q) {
        this.id = id;
        this.idType = idType;
        this.name = n;
        this.info=i; //Nutritional info for 100g
    }
    public void setQuantity(int q) {
        this.quantity = q;
    }

    public long getId(){
        return id;
    }
    public long getId_Type(){
        return idType;
    }
    public NutritionalInfo countNutritionalInfo(NutritionalInfo oldI){
        double calories = (oldI.calories / 100.0) * quantity;
        double proteins=(oldI.proteins / 100.0) * quantity;
        double fats=(oldI.fats / 100.0) * quantity;
        double carbohydrates=(oldI.carbohydrates / 100.0) * quantity;
        return new NutritionalInfo(calories,proteins,fats,carbohydrates);
    }

}
