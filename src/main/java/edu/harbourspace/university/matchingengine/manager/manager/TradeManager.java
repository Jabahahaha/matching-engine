package edu.harbourspace.university.matchingengine.manager.manager;

import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.model.Originator;
import edu.harbourspace.university.matchingengine.manager.model.Side;
import edu.harbourspace.university.matchingengine.manager.model.Trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeManager implements ITradeManager {
    private final Map<String, Order> activeOrders = new HashMap<>();
    private final List<Trade> executedTrades = new ArrayList<>();
    private final Map<String, Integer> positions = new HashMap<>();
    private final int maximumPosition;

    public TradeManager(int maximumPosition) {
        this.maximumPosition = maximumPosition;
    }

    @Override
    public void processOrder(Order order) {
        // Adjusted to handle orders from VE as well
        if (order.getOriginator() == Originator.DF || order.getOriginator() == Originator.VE) {
            // Check if the order can be matched or needs to be stored
            if (order.getOriginator() == Originator.DF) {
                // Store DF orders and update position
                activeOrders.put(order.getMessageId(), order);
                updatePosition(order.getProductId(), order.getSide(), order.getSize());
            } else {
                // Attempt to match VE orders with active DF orders
                matchOrder(order);
            }
        }
    }

    private void matchOrder(Order externalOrder) {
        for (Order dfOrder : new ArrayList<>(activeOrders.values())) {
            if (isMatch(dfOrder, externalOrder) && isPositionValid(dfOrder, externalOrder)) {
                executeTrade(dfOrder, externalOrder);
                break;
            }
        }
    }

    private boolean isMatch(Order dfOrder, Order externalOrder) {
        return dfOrder.getProductId().equals(externalOrder.getProductId())
                && ((dfOrder.getSide() == Side.BUY && externalOrder.getSide() == Side.SELL
                && dfOrder.getPrice() >= externalOrder.getPrice())
                || (dfOrder.getSide() == Side.SELL && externalOrder.getSide() == Side.BUY
                && dfOrder.getPrice() <= externalOrder.getPrice()));
    }

    private boolean isPositionValid(Order dfOrder, Order externalOrder) {
        // Calculate what the new position would be
        int potentialPositionChange = externalOrder.getSize() * (dfOrder.getSide() == Side.BUY ? 1 : -1);
        int newPosition = positions.getOrDefault(dfOrder.getProductId(), 0) + potentialPositionChange;

        // Check if the new position exceeds the maximum allowed position
        return Math.abs(newPosition) <= maximumPosition;
    }

    private void executeTrade(Order dfOrder, Order externalOrder) {
        Trade executedTrade = new Trade(dfOrder.getSide(), externalOrder.getSize(),
                dfOrder.getPrice(), dfOrder.getProductId());

        executedTrades.add(executedTrade);
        activeOrders.remove(dfOrder.getMessageId());
        updatePosition(dfOrder.getProductId(), dfOrder.getSide(), externalOrder.getSize());
    }

    private void updatePosition(String productId, Side side, int size) {
        int positionChange = size * (side == Side.BUY ? 1 : -1);
        positions.put(productId, positions.getOrDefault(productId, 0) + positionChange);
    }

    @Override
    public void processCancelMessage(String messageId) {
        Order order = activeOrders.remove(messageId);
        if (order != null) {
            // Revert position
            updatePosition(order.getProductId(), order.getSide() == Side.BUY ? Side.SELL : Side.BUY, order.getSize());
        }
    }

    @Override
    public List<Trade> getExecutedTrades() {
        return new ArrayList<>(executedTrades);
    }

}
