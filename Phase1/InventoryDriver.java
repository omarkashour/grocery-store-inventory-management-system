public class InventoryDriver {

    public static void main(String[] args) {
        Inventory store = new Inventory("groceries");
        store.newItem("bread", 15, 9.99);
        store.newItem("al-jebrini", "milk", 2, 2.00);
        store.newItem("eggs", 3, 1.50);
        store.newItem("bread", 2, 1.25); // warning: in stock
        store.stockReport();
        store.update("al-jebrini", "milk", .25); // raise price 25%
        store.update("eggs", -1); // lower quantity by 1
        store.update("juice", 3); // warning: not stocked
        store.newItem("juneidi", "milk", 4, 1.95);
        store.stockReport();
        store.setPrice("cola", 10); // warning: not stocked
        store.setQuantity("al-jebrini", "milk", 3);
        store.setPrice("eggs", 2.00);
        System.out.println("milk quantity: " + store.getQuantity("alsafi",
                "milk")); // not stocked
        System.out.println("milk quantity: " + store.getQuantity("milk")); // ambiguity
        System.out.println("eggs price: " + store.getPrice("eggs"));
        System.out.println("milk price: " + store.getPrice("milk")); //ambiguity
        System.out.println((new Item("cola").setPrice(5)).compareTo(new
                Item("milk").setPrice(8)));

    }
}
