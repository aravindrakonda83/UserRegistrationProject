package SetGetMethods;

class Vehicle {
    private String make;
    private String model;
    private int year;

    // Getter and Setter for make
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    // Getter and Setter for model
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Getter and Setter for year
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
public class VechileClass {
    public static void main(String[] args) {
        // Example for Vehicle class
        Vehicle vehicle = new Vehicle();
        vehicle.setMake("Toyota");
        vehicle.setModel("Camry");
        vehicle.setYear(2020);
        System.out.println("Vehicle: " + vehicle.getMake() + " " + vehicle.getModel() + " (" + vehicle.getYear() + ")");
    }
}