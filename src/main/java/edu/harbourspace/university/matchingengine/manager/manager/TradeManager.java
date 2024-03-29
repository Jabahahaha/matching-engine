package edu.harbourspace.university.matchingengine.manager.manager;

import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.model.Originator;
import edu.harbourspace.university.matchingengine.manager.model.Side;
import edu.harbourspace.university.matchingengine.manager.model.Trade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;

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
        if (order.getOriginator() == Originator.DF || order.getOriginator() == Originator.VE) {
            if (order.getOriginator() == Originator.DF) {
                activeOrders.put(order.getMessageId(), order);
                updatePosition(order.getProductId(), order.getSide(), order.getSize());
            } else {
                matchOrder(order);
            }
        }
    }

    private void matchOrder(Order externalOrder) {
        List<Order> matchedOrders = new ArrayList<>(activeOrders.values());
        if (externalOrder.getSide() == Side.SELL) {
            matchedOrders.sort(Comparator.comparing(Order::getPrice).reversed().thenComparing(Order::getMessageId));
        } else {
            matchedOrders.sort(Comparator.comparing(Order::getPrice).thenComparing(Order::getMessageId));
        }

        for (Order dfOrder : matchedOrders) {
            if (isMatch(dfOrder, externalOrder) && isPositionValid(dfOrder, externalOrder)) {
                int tradeSize = Math.min(dfOrder.getSize(), externalOrder.getSize());
                executeTrade(dfOrder, externalOrder, tradeSize);
                if (externalOrder.getSize() <= 0) {
                    break;
                }
            }
        }
    }

    private boolean isMatch(Order dfOrder, Order externalOrder) {
        return dfOrder.getProductId().equals(externalOrder.getProductId()) &&
                ((dfOrder.getSide() == Side.BUY && externalOrder.getSide() == Side.SELL && dfOrder.getPrice() >= externalOrder.getPrice()) ||
                        (dfOrder.getSide() == Side.SELL && externalOrder.getSide() == Side.BUY && dfOrder.getPrice() <= externalOrder.getPrice()));
    }

    private boolean isPositionValid(Order dfOrder, Order externalOrder) {
        int potentialPositionChange = externalOrder.getSize() * (dfOrder.getSide() == Side.BUY ? 1 : -1);
        int newPosition = positions.getOrDefault(dfOrder.getProductId(), 0) + potentialPositionChange;
        return Math.abs(newPosition) <= maximumPosition;
    }

    private void executeTrade(Order dfOrder, Order externalOrder, int tradeSize) {
        Trade executedTrade = new Trade(dfOrder.getSide(), tradeSize, dfOrder.getPrice(), dfOrder.getProductId());
        executedTrades.add(executedTrade);
        activeOrders.remove(dfOrder.getMessageId());
        dfOrder.setSize(dfOrder.getSize() - tradeSize);
        if (dfOrder.getSize() > 0) {
            activeOrders.put(dfOrder.getMessageId(), dfOrder);
        }
        updatePosition(dfOrder.getProductId(), dfOrder.getSide(), tradeSize);
    }

    private void updatePosition(String productId, Side side, int size) {
        int positionChange = size * (side == Side.BUY ? 1 : -1);
        positions.put(productId, positions.getOrDefault(productId, 0) + positionChange);
    }

    @Override
    public void processCancelMessage(String messageId) {
        Order order = activeOrders.remove(messageId);
        if (order != null) {
            updatePosition(order.getProductId(), order.getSide() == Side.BUY ? Side.SELL : Side.BUY, order.getSize());
        }
    }

    @Override
    public List<Trade> getExecutedTrades() {
        return new ArrayList<>(executedTrades);
    }
}
