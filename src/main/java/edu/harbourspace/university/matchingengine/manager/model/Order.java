package edu.harbourspace.university.matchingengine.manager.model;

public class Order {
    private String originator;
    private String messageId;
    private Side side;
    private int size;
    private double price;
    private String productId;

    public Order(String originator, String messageId, String side, int size, double price, String productId) {
        this.originator = originator;
        this.messageId = messageId;
        this.side = side;
        this.size = size;
        this.price = price;
        this.productId = productId;
    }

    // Getters
    public String getOriginator() {
        return originator;
    }

    public String getMessageId() {
        return messageId;
    }

    public String getSide() {
        return side;
    }

    public int getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public String getProductId() {
        return productId;
    }
}
