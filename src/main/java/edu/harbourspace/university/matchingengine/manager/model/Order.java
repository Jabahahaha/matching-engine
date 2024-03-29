package edu.harbourspace.university.matchingengine.manager.model;

public class Order {
    private final Originator originator;
    private final String messageId;
    private final Side side;
    private final int size;
    private final double price;
    private final String productId;

    public Order(Originator originator, String messageId, Side side, int size, double price, String productId) {
        this.originator = originator;
        this.messageId = messageId;
        this.side = side;
        this.size = size;
        this.price = price;
        this.productId = productId;
    }

    // Getters
    public Originator getOriginator() {
        return originator;
    }

    public String getMessageId() {
        return messageId;
    }

    public Side getSide() {
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
