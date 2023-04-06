package Models;

import java.util.ArrayList;

public class Order {
    private long id;
    private double sum;

    private String date;
    public ArrayList<OrderedItem> orderedItems;
    public Order(long id , double sum, String date){
        this.id = id;
        this.sum= sum;
        this.date = date;
        orderedItems = new ArrayList<OrderedItem>();
    }

    public void print(){
        System.out.printf("\norder number: %2d\n2f\n%s\n",
                id, sum, date);
        for(OrderedItem it : orderedItems){
            it.print();
        }
    }

}

