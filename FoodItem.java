
public class FoodItem {
    private String name;
    private int quantity;
    private boolean perishable;
    private String expiryDate;
    private String location;

    public FoodItem(String name, int quantity, boolean perishable, String expiryDate, String location) {
        this.name = name;
        this.quantity = quantity;
        this.perishable = perishable;
        this.expiryDate = expiryDate;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isPerishable() {
        return perishable;
    }

    public void setPerishable(boolean perishable) {
        this.perishable = perishable;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String toString() {
        return name + " | " + quantity + " | " + (perishable ? "Yes" : "No") + " | " + (expiryDate == null ? "N/A" : expiryDate) + " | " + location;
    }
    // Simple method to check if the food item is expired
    public boolean isExpired() {
        if (expiryDate == null || expiryDate.isEmpty()) {
            return false;
        }
        // Compare expiryDate to today's date (format: YYYY-MM-DD)
        String today = java.time.LocalDate.now().toString();
        return expiryDate.compareTo(today) < 0;
    }
}