public class Brand extends Item {
    private String brand;

    public Brand() {

    }

    public Brand(String brand, String type) {
        super(type);
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }
}
