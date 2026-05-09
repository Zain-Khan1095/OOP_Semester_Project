package model;


public class Medicine extends Product {
    //  private fields 
    private int stock;
    private String expiryDate;

    // Constructor 
    public Medicine(int id, String name, double price, int stock, String expiryDate) {
        super(id, name, price); // Calls the parent Product constructor
        this.stock = stock;
        this.expiryDate = expiryDate;
    }

    // Constructor 
    public Medicine(String name, double price, int stock, String expiryDate) {
        super(name, price);
        this.stock = stock;
        this.expiryDate = expiryDate;
    }

    // Getters and Setters

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
        }
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

// Implementation Of getDescription Method 
    @Override
    public String getDescription() {
        return "Medicine: " + getName() + " | Price: " + getPrice() + " | Stock: " + stock + " | Expires: " + expiryDate;
    }
}