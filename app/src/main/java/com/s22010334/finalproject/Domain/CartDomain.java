package com.s22010334.finalproject.Domain;

public class CartDomain {
    private String id;
    private String plantId;
    private String uploadId;
    private String name;
    private double price;
    private String imageUrl;
    private int quantity;
    private long timestamp;

    public CartDomain() {
        // Default constructor required for calls to DataSnapshot.getValue(CartItem.class)
    }

    public CartDomain(String id,String plantId,String uploadId, String name, double price, String imageUrl, int quantity, long timestamp) {
        this.plantId = plantId;
        this.name = name;
        this.uploadId = uploadId;
        this.price = price;
        this.imageUrl = imageUrl;
        this.quantity = quantity;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getUploadId() {
        return uploadId;
    }

    public void setUploadId(String uploadId) {
        this.uploadId = uploadId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

