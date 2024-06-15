package com.s22010334.finalproject.Domain;

import java.io.Serializable;
import java.util.ArrayList;

public class itemDomain implements Serializable {
    private static String title;
    private String description;
    private ArrayList<String> picUrl;
    private double price;
    //private int review;
    private double rating;
    private int numberInCart;

    public itemDomain() {
    }

    public itemDomain(String title, String description, ArrayList<String> picUrl, double price, double rating) {
        this.title = title;
        this.description = description;
        this.picUrl = picUrl;
        this.price = price;
        this.rating = rating;
    }

    public static String getTitle() {
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

    public ArrayList<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(ArrayList<String> picUrl) {
        this.picUrl = picUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getNumberInCart() {
        return numberInCart;
    }

    public void setNumberInCart(int numberInCart) {
        this.numberInCart = numberInCart;
    }
}
