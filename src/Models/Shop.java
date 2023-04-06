package Models;

public class Shop {
    private long id;
    private double rating;
    private String name;

    public void setShop(long id, double rating, String name){
        this.id =id;
        this.rating = rating;
        this.name = name;
    }
    public long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public void print(){
        System.out.printf("\nShop %s - \nRating ~ %s\n",
                name, rating > 0 ? "" + rating: "N/A");
    }

}
