package manager;

import model.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradeManager {
    private final Map<String, Order> activeOrders = new HashMap<>();
    private final List<Order> executedTrades = new ArrayList<>();

    public void processOrder(Order order) {
        // If the order is from DF, store it in the active orders.
        if ("DF".equals(order.getOriginator())) {
            activeOrders.put(order.getMessageId(), order);
        } else { // Else, try to match with an active DF order.
            matchOrder(order);
        }
    }

    private void matchOrder(Order externalOrder) {
        for (Order dfOrder : activeOrders.values()) {
            if (dfOrder.getProductId().equals(externalOrder.getProductId())
                    && !dfOrder.getMessageId().equals(externalOrder.getMessageId())
                    && ((dfOrder.getSide().equals("BUY") && externalOrder.getSide().equals("SELL")
                    && dfOrder.getPrice() >= externalOrder.getPrice())
                    || (dfOrder.getSide().equals("SELL") && externalOrder.getSide().equals("BUY")
                    && dfOrder.getPrice() <= externalOrder.getPrice()))) {
                // Execute the trade at the price of the DF order.
                Order executedOrder = new Order("DF", dfOrder.getMessageId(), dfOrder.getSide(),
                        externalOrder.getSize(), dfOrder.getPrice(),
                        dfOrder.getProductId());
                executedTrades.add(executedOrder);

                // Remove the matched DF order as it's executed.
                activeOrders.remove(dfOrder.getMessageId());
                break; // Assuming one DF order matches with one external order.
            }
        }
    }

    public void processCancelMessage(String messageId) {
        activeOrders.remove(messageId);
    }

    public void printTrades() {
        for (Order trade : executedTrades) {
            System.out.println(trade.getSide() + "\t" + trade.getSize() + "\t" +
                    trade.getPrice() + "\t" + trade.getProductId());
        }
    }
}