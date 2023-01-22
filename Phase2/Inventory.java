import java.util.ArrayList;

public class Inventory {
	private ArrayList<Item> inventory = new ArrayList<>();
	private String category;
	private String statusMessage = "";

	public Inventory(String category) {
		this.category = category;
	}

	public boolean newItem(String type, int quantity, double price) { // creates a new Brand item if the list doesn't
																		// contain it already
		int index = findItem(type, true);
		if (index == -1) {
			inventory.add(new Item(type).setQuantity(quantity).setPrice(price));
			return true;
		}
		return false;
	}

	public boolean newItem(String brand, String type, int quantity, double price) { // creates a new Brand item if the
																					// list doesn't contain it already
		int index = findItem(type, true, brand);
		if (index == -1) {
			inventory.add(new Brand(brand, type).setQuantity(quantity).setPrice(price));
			return true;
		}
		return false;
	}

	public void setQuantity(String type, int quantity) { // sets the quantity of an item if found
		int index = findItem(type, false);
		if (index != -1) {
			inventory.get(index).setQuantity(quantity);
		}
	}

	public void setQuantity(String brand, String type, int quantity) { // sets the quantity of an item if found
		int index = findItem(type, false, brand);
		if (index != -1) {
			inventory.get(index).setQuantity(quantity);
		}
	}

	public void setPrice(String type, double price) { // sets the price of an item if found
		int index = findItem(type, false);
		if (index != -1) {
			inventory.get(index).setPrice(price);
		}
	}

	public void setPrice(String brand, String type, double price) { // sets the price of an item if found
		int index = findItem(type, false, brand);
		if (index != -1) {
			inventory.get(index).setPrice(price);
		}
	}

	public int getQuantity(String type) { // returns the quantity of an item if found
		int index = findItem(type, false);
		if (index != -1) {
			return inventory.get(index).getQuantity();
		}
		return -1;
	}

	public int getQuantity(String brand, String type) { // returns the quantity of an item if found
		int index = findItem(type, false, brand);
		if (index != -1) {
			return inventory.get(index).getQuantity();
		}
		return 0;
	}

	public double getPrice(String type) { // returns the price of an item if found
		int index = findItem(type, false);
		if (index != -1) {
			return inventory.get(index).getPrice();
		}

		return Double.NaN;
	}

	public double getPrice(String brand, String type) { // returns the price of an item if found
		int index = findItem(type, false, brand);
		if (index != -1) {
			return inventory.get(index).getPrice();
		}

		return Double.NaN;
	}

	public void update(String type, int qtyIncrease) { // updates the item's quantity according to qtyIncrease value
		int index = findItem(type, false);
		if (index >= 0) {
			inventory.get(index).update(qtyIncrease);
		}
	}

	public void update(String brand, String type, int qtyIncrease) { // updates the item's quantity according to
																		// qtyIncrease value
		int index = findItem(type, false, brand);
		if (index >= 0) {
			inventory.get(index).update(qtyIncrease);
		}
	}

	public void update(String brand, double adjustmentFactor) { // updates the item according to the adjustment factor
		int index = findItem(brand, false);
		if (index >= 0) {
			inventory.get(index).update(adjustmentFactor);
		}
	}

	public void update(String brand, String type, double adjustmentFactor) { // updates the item according to the
																				// adjustment factor
		int index = findItem(type, false, brand);
		if (index >= 0) {
			inventory.get(index).update(adjustmentFactor);
		}
	}

	protected int findItem(String type, boolean warningIfFound) { // finds an item with the specified type
		statusMessage = "";
		int index = -1;
		int itemsFound = 0;
		for (int i = 0; i < inventory.size(); i++) {
			Item currentItem = inventory.get(i);
			if (type.equals(currentItem.getType())) {
				index = i;
				itemsFound++;
			}
		}

		if (itemsFound == 0 && !warningIfFound) { // check for warning cases first to avoid wrong output
			System.out.println("Cannot find " + type);
			statusMessage = "Cannot find " + type;
		} else if (itemsFound > 0 && warningIfFound) {
			System.out.println(type + " already exists");
			statusMessage = type + " already exists";
			return index; // returns a positive value to avoid duplicate items in newItem()
		} else if (itemsFound > 1) {
			System.out.println("found more than one brand of " + type);
			statusMessage = "found more than one brand of " + type;
		} else if (itemsFound == 1) {
			return index;
		}

		return -1;
	}

	protected int findItem(String type, boolean warningIfFound, String brand) { // finds an item with specified type and
																				// brand
		statusMessage = "";
		int index = -1;
		int itemsFound = 0;
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Brand) { // check if the object is an instance of brand first to avoid
														// conflict with Item
				Brand currentBrand = (Brand) inventory.get(i);
				if (type.equals(currentBrand.getType()) && brand.equals(currentBrand.getBrand())) {
					index = i;
					itemsFound++;
				}
			}
		}

		if (itemsFound == 0 && !warningIfFound) { // check for warning cases first to avoid wrong output
			System.out.println("Cannot find " + brand + " " + type);
			statusMessage = "Cannot find " + brand + " " + type;
			return -1;
		} else if (itemsFound > 0 && warningIfFound) {
			System.out.println(brand + " " + type + " already exists");
			statusMessage = brand + " " + type + " already exists";
			return index; // returns a positive value to avoid duplicate items in newItem()
		} else if (itemsFound == 1) {
			return index;
		}
		return -1;
	}

	public String stockReport() {
		double value = 0;
		String report = "";
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Brand) { // check from subclasses first in ascending order to super class to
														// avoid wrong output (Polymorphism)
				Brand brand = (Brand) inventory.get(i);
				System.out.println(brand.getBrand() + " " + brand.getType() + " - " + "in stock: " + brand.getQuantity()
						+ ", price: $" + String.format("%.2f", brand.getPrice()));
				report += brand.getBrand() + " " + brand.getType() + " - " + "in stock: " + brand.getQuantity()
						+ ", price: $" + String.format("%.2f", brand.getPrice()) + "\n";
				value += brand.getPrice() * brand.getQuantity(); // value of an item is its price times its quantity
																	// (value = price * quantity)
			} else if (inventory.get(i) instanceof Item) {
				Item item = inventory.get(i);
				System.out.println(item.getType() + " - " + "in stock: " + item.getQuantity() + ", price: $"
						+ String.format("%.2f", item.getPrice()));
				report += item.getType() + " - " + "in stock: " + item.getQuantity() + ", price: $"
						+ String.format("%.2f", item.getPrice()) + "\n";
				value += item.getPrice() * item.getQuantity();
			}

		}
		System.out.println("Total Value: $" + String.format("%.2f", value));
		System.out.println();
		report += "Total Value: $" + String.format("%.2f", value) + "\n";
		report += "\n";
		return report;
	}

	public boolean remove(String brand, String type) {
		int index = findItem(type, true, brand);
		if (index >= 0) {
			inventory.remove(index);
			return true;
		}
		return false;
	}

	public boolean remove(String type) {
		int index = findItem(type, true);
		if (index >= 0) {
			inventory.remove(index);
			return true;
		}
		return false;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

}
