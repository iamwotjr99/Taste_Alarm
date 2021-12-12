package org.techtown.tastealarm;

import java.io.Serializable;

public class Restaurant implements Serializable{
    private int id;
    private String picture;
    private String restaurant_name;
    private String restaurant_address;
    private String restaurant_classificationfamous_restaurant;
    private double latitude;
    private double longitude;

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
        return restaurant_classificationfamous_restaurant;
    }

    public void setCategory(String category) {
        this.restaurant_classificationfamous_restaurant = category;
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Restaurant(int id, String name, String address, String category, String picture, double latitude, double longitude) {
        this.id = id;
        this.restaurant_name = name;
        this.restaurant_address = address;
        this.restaurant_classificationfamous_restaurant = category;
        this.picture = picture;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Restaurant(int id, String restaurant_name) {
        this.id = id;
        this.restaurant_name = restaurant_name;
    }
}
