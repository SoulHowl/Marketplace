package com.practice.test.market.entity;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

@Entity
@Table(name="ordered_items")
public class OrderedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "price")
    private double price;

    @Column(name = "quantity")
    private long quantity;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "ordered_item_status_id", referencedColumnName = "id")
    private OrderedItemStatus orderedItemStatus;

    //@Nullable
    @ManyToOne(cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name="order_id", referencedColumnName = "id")
    private Order order;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name="item_id", referencedColumnName = "id")
    private Item item;

    @JoinColumn(name = "value")
    private String value;

    public OrderedItem(){};

    public OrderedItem(double price, long quantity, OrderedItemStatus orderedItemStatus, Order order, Item item, String value) {
        this.price = price;
        this.quantity = quantity;
        this.orderedItemStatus = orderedItemStatus;
        this.order = order;
        this.item = item;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public OrderedItemStatus getOrderedItemStatus() {
        return orderedItemStatus;
    }

    public void setOrderedItemStatus(OrderedItemStatus orderedItemStatus) {
        this.orderedItemStatus = orderedItemStatus;
    }

    @Nullable
    public Order getOrder() {
        return order;
    }

    public void setOrder(@Nullable Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

//    @Override
//    public String toString() {
//        return "OrderedItem{" +
//                "id=" + id +
//                ", price=" + price +
//                ", quantity=" + quantity +
//                ", orderedItemStatus=" + orderedItemStatus +
//                ", order=" + order +
//                ", item=" + item +
//                '}';
//    }
}
