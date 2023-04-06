package Models;

public class Item {
    private long id;
    private String title;
    private String description;
    private String platform;
    private double price;
    private int quantity;
    private String category;
    private String shop;

    public Item(long id, String title, String description, double price,
                int quantity, String platform, String category, String shop){
        this.title = title;
        this.id = id;
        this.description = description;
        this.platform = platform;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.shop = shop;
    }

    public long getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getPlatform() {
        return platform;
    }
    public String getCategory() {
        return category;
    }
    public double getPrice() {
        return price;
    }
    public int getQuantity() {
        return quantity;
    }

    public void print(){
        System.out.printf("\nnumber: %2d\n%s \n%s \n%s \n%s \n%s \n%2d \n%2f\n",
               id, title, category, platform, description, shop, quantity, price);
    }




}
