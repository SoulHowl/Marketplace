package com.practice.test.market.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shop_id", referencedColumnName = "id")
    private Shop shop;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "platform_id", referencedColumnName = "id")
    private Platform platform;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "quantity")
    private long quantity;
    @Column(name = "price")
    private double price;

    @Column(name = "hidden")
    private boolean hidden;
    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER)
    private List<OrderedItem> orderedItems;

    public Item() {
    }

    public Item(Shop shop, Category category, Platform platform, String title, String description, long quantity, double price, boolean hidden, List<OrderedItem> orderedItems) {
        this.shop = shop;
        this.category = category;
        this.platform = platform;
        this.title = title;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.hidden = hidden;
        this.orderedItems = orderedItems;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }



    public List<OrderedItem> getOrderedItems() {
        return orderedItems;
    }

    public void setOrderedItems(List<OrderedItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", shop=" + shop +
                ", category=" + category +
                ", platform=" + platform +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", hidden=" + hidden +
                "odItems= " + orderedItems+
                '}';
    }
}