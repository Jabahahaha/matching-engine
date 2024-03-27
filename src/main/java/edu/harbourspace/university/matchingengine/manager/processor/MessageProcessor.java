package edu.harbourspace.university.matchingengine.manager.processor;

import edu.harbourspace.university.matchingengine.manager.TradeManager;
import edu.harbourspace.university.matchingengine.manager.model.Order;

public class MessageProcessor {
    private final TradeManager tradeManager;

    public MessageProcessor(TradeManager tradeManager) {
        this.tradeManager = tradeManager;
    }

    public Order processMessage(String message) {
        message = message.replaceAll(" +", "\t");

        String[] parts = message.trim().split("\\t");

        if (parts.length == 6 && ("DF".equals(parts[0]) || "VE".equals(parts[0]))) {
            try {
                // Parse the size and price to ensure they are in the correct format
                int size = Integer.parseInt(parts[3]);
                double price = Double.parseDouble(parts[4]);

                // Create a new Order object with the parsed data
                Order order = new Order(parts[0], parts[1], parts[2], size, price, parts[5]);
                return order;
                // Process the order
                tradeManager.processOrder(order);
            } catch (NumberFormatException e) {
                System.out.println("Error parsing numerical values for order: " + message);
            }
        }
        // Check for cancel messages, which should have 3 parts
        else if (parts.length == 3 && "DF".equals(parts[0]) && "CANCEL".equals(parts[2])) {
            tradeManager.processCancelMessage(parts[1]);
        }
        // Check for the FINISH command, which should be a single part
        else if (message.trim().equals("FINISH")) {
            tradeManager.printTrades();
        }
        // If none of the above conditions are met, the message is unrecognized
        else {
            System.out.println("Unrecognized message format: " + message);
        }
    }
}
