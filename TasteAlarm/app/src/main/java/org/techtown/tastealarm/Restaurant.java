package org.techtown.tastealarm;

public class Restaurant {
    private int id;
    private String picture;
    private String restaurant_name;
    private String restaurant_address;
    private String category;
    private String restaurant_classificationfamous_restaurant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return restaurant_name;
    }

    public void setName(String name) {
        this.restaurant_name = name;
    }

    public String getAddress() {
        return restaurant_address;
    }

    public void setAddress(String address) {
        this.restaurant_address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Restaurant(String name, String address, String picture) {
        this.restaurant_name = name;
        this.restaurant_address = address;
        this.picture = picture;
    }

    public Restaurant(String name, String address) {
        this.restaurant_name = name;
        this.restaurant_address = address;
    }

    public Restaurant(int id, String name, String address, String category, String picture) {
        this.id = id;
        this.restaurant_name = name;
        this.restaurant_address = address;
        this.restaurant_classificationfamous_restaurant = category;
        this.picture = picture;
    }
}
