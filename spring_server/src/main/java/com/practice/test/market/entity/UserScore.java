package com.practice.test.market.entity;

import jakarta.persistence.*;

@Entity
@Table(name="rating")
public class UserScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    //OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="shop_id", referencedColumnName = "id")
    private Shop shop;
    @ManyToOne(cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "rate_number")
    private int rateScore;

    public UserScore(){};

    public UserScore(Shop shop, User user, int rateScore) {
        this.shop = shop;
        this.user = user;
        this.rateScore = rateScore;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getRateScore() {
        return rateScore;
    }

    public void setRateScore(int rateScore) {
        this.rateScore = rateScore;
    }
}
