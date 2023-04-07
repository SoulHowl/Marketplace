package Models;

public class OrderedItem {
    private long id;
    private String name;
    private double price;
    private long quantity;
    private String status;
    private String value = "";
    private String shop;

    public OrderedItem(long id, String name, double price, long quantity, String status, String shop, String value) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.status = status;
        this.value = value;
        this.shop = shop;
    }
    public long getId(){
        return id;
    }

    public void setQuantity(long q){
        this.quantity = q;
    }

    public void print(){
        System.out.printf("id:%2d\n%s\n%s \n%s \n%s\n%2d \n%2f\n",
                id, name, value, status, shop, quantity, price);
    }
}
