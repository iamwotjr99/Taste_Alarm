package org.techtown.tastealarm;

import java.io.Serializable;

public class Menu implements Serializable {
    private int id;
    private String restaurant_name;
    private String menu;
    private String price;
    private String picture;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return restaurant_name;
    }

    public void setName(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Menu(String restaurant_name, String menu, String price, String picture) {
        this.restaurant_name = restaurant_name;
        this.menu = menu;
        this.price = price;
        this.picture = picture;
    }
}
