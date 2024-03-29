package edu.harbourspace.university.matchingengine.manager.model;

public class Trade {
    private final Side side;
    private final int size;
    private final double price;
    private final String productId;

    public Trade(Side side, int size, double price, String productId) {
        this.side = side;
        this.size = size;
        this.price = price;
        this.productId = productId;
    }

    @Override
    public String toString() {
        // Format as per the output requirements from your assignment
        return String.format("%s\t%d\t%.3f\t%s\n", side, size, price, productId);
    }
}
