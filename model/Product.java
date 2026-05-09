package model;


public abstract class Product {
    // Private fields 
    private int id;
    private String name;
    private double price;

    // Constructor 
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    //  constructor 
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters and Setters 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        
        if (price > 0) {
            this.price = price;
        }
    }

  
    public abstract String getDescription();
}