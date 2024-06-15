package com.s22010334.finalproject.Domain;

public class PlantDomain {
    String id;
    String name;
    String price;
    String description;
    String address;
    Long PhoneNumber;
    String imageUrl;
    String quantity;

    public PlantDomain() {
    }

    public PlantDomain(String id, String name, String price, String description, String address,Long PhoneNumber, String imageUrl, String quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.address = address;
        this.PhoneNumber = PhoneNumber;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}

