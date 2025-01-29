package SetGetMethods;

class Item {
    private String name;
    private int price;
    private int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

public class ItemClass {
    public static void main(String[] args) {
        Item item = new Item();  // Correct object creation

        item.setName("Snickers");   // Correct method calls
        item.setPrice(45);
        item.setQuantity(3);

        // Properly formatted output
        System.out.println("Item: " + item.getName() + 
                           ", Price: " + item.getPrice() + 
                           ", Quantity: " + item.getQuantity());
    }
}
