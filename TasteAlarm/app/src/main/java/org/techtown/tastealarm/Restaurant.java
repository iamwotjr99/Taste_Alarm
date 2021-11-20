package org.techtown.tastealarm;

public class Restaurant {
    private String picture;
    private String restaurant_name;
    private String restaurant_address;
    private String category;

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
}
