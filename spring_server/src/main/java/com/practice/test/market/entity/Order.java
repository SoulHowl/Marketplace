package com.practice.test.market.entity;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Nullable
    @Column(name = "purhased_at ")
    private Date purchaseDate;
    @Column(name = "sum")
    private double sum;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    @Nullable
    @OneToMany(cascade = { CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private List<OrderedItem> orderedItems;
    public Order(){}


    public Order(@Nullable Date purchaseDate, double sum, User user, Status status, @Nullable List<OrderedItem> orderedItems) {
        this.purchaseDate = purchaseDate;
        this.sum = sum;
        this.user = user;
        this.status = status;
        this.orderedItems = orderedItems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Nullable
    public Date  getPurchaseDate() {
        return purchaseDate;
    }
    @Nullable
    public String  getPurchaseDateString() {
        assert purchaseDate != null;
        return purchaseDate.toString();
    }

    public void setPurchaseDate(@Nullable Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Nullable
    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }


    public void setOrderedItems(@Nullable List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", purchaseDate='" + purchaseDate + '\'' +
                ", sum=" + sum +
                ", user=" + user +
                ", status=" + status +
                ", orderedItems=" + orderedItems +
                '}';
    }
}
