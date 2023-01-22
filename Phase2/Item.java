public class Item implements Comparable<Item> {
    private String type;
    private int quantity;
    private double price;
    String error = "";
    public Item() {

    }

    public Item(String type) {
        this.type = type;
    }

    public Item setQuantity(int quantity) {
        if (quantity >= 0)  // quantity cannot be negative
            this.quantity = quantity;
        else {
            System.out.println("Error: quantity cannot be negative");
            error = "Error: quantity cannot be negative";
        }
        return this;
    }

    public Item setPrice(double price) {
        if (price >= 0) // price cannot be negative
            this.price = price;
        else {
            System.out.println("Error: price cannot be negative");
            error = "Error: price cannot be negative";
        }
        return this;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Item update(int qtyIncrease) {
        this.quantity += qtyIncrease;
        return this;
    }

    public Item update(double adjustmentFactor) {
        this.price *= (1 + adjustmentFactor);
        return this;
    }

    @Override
    public int compareTo(Item o) {
        return (int) (this.price - o.price); // compare two objects of type Item according to their price difference
    }
}
