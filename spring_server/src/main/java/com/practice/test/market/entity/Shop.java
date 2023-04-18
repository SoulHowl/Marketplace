package com.practice.test.market.entity;

import jakarta.persistence.*;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="shop")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Nullable
    @Column(name = "overall_score")
    private String overallScore;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    private List<UserScore> rates ;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Shop(){}

    public Shop(String name, @Nullable String overallScore, List<UserScore> rates, User user) {
        this.name = name;
        this.overallScore = overallScore;
        this.rates = rates;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserScore> getRates() {
        return rates;
    }

    public void setRates(List<UserScore> rates) {
        this.rates = rates;
    }

    @Nullable
    public String getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(@Nullable String overallScore) {
        this.overallScore = overallScore;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name=" + name + '\'' +
                ", overallScore='" + overallScore + '\'' +
                ", seller=" + user.getNickname() +
                '}';
    }
}
