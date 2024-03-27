package manager;

import model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeManager {
    // Stores active orders by their message ID
    private final Map<String, Order> activeOrders = new HashMap<>();

    // Keeps track of executed trades
    private final List<Order> executedTrades = new ArrayList<>();

    // Processes an incoming order by either storing it or attempting a match.
    public void processOrder(Order order) {
        if ("DF".equals(order.getOriginator())) {
            // Stores DF orders as active
            activeOrders.put(order.getMessageId(), order);
        } else {
            // Attempts to match external orders with active DF orders
            matchOrder(order);
        }
    }


    // Matches an external order with active DF orders and executes trade if criteria met.
    private void matchOrder(Order externalOrder) {
        for (Order dfOrder : activeOrders.values()) {
            // Conditions for a match: opposite sides, same product, and crossing prices
            if (dfOrder.getProductId().equals(externalOrder.getProductId())
                    && !dfOrder.getMessageId().equals(externalOrder.getMessageId())
                    && ((dfOrder.getSide().equals("BUY") && externalOrder.getSide().equals("SELL")
                    && dfOrder.getPrice() >= externalOrder.getPrice())
                    || (dfOrder.getSide().equals("SELL") && externalOrder.getSide().equals("BUY")
                    && dfOrder.getPrice() <= externalOrder.getPrice()))) {

                // Create executed order and add to executed trades list
                Order executedOrder = new Order("DF", dfOrder.getMessageId(), dfOrder.getSide(),
                        externalOrder.getSize(), dfOrder.getPrice(), dfOrder.getProductId());
                executedTrades.add(executedOrder);

                // Removes matched DF order from active orders
                activeOrders.remove(dfOrder.getMessageId());
                break; // Stops after first match
            }
        }
    }

    // Removes an order from active orders using its message ID.
    public void processCancelMessage(String messageId) {
        activeOrders.remove(messageId);
    }

    // Prints details of all executed trades to the console.
    public void printTrades() {
        executedTrades.forEach(trade ->
                System.out.println(trade.getSide() + "\t" + trade.getSize() + "\t" +
                        trade.getPrice() + "\t" + trade.getProductId()));
    }
}
