package task2.toys;

public class SoftToy {
    private int id;
    private String name;
    private int quantity;
    private int dropFrequency;
    

    public SoftToy(int id, String name, int quantity, int dropFrequency) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.dropFrequency = dropFrequency;
    }
    
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getQuantity() {
        return quantity;
    }
    public int getDropFrequency() {
        return dropFrequency;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDropFrequency(int dropFrequency) {
        this.dropFrequency = dropFrequency;
    }

    @Override
    public String toString() {
        return "id - " + id + ", name - " + name + ", quantity - " + quantity + ", dropFrequency - " + dropFrequency + "%";
    }

}
