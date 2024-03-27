package edu.harbourspace.university.matchingengine.manager;

import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.processor.MessageProcessor;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TradeManager tradeManager = new TradeManager();
        MessageProcessor processor = new MessageProcessor(tradeManager);

        System.out.println("Application started. Enter messages:");
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    Order order = processor.processMessage(line);
                    tradeManager.processOrder(order);
                    if ("FINISH".equals(line)) break;
                }
            }
        }
        System.out.println("Application finished.");
    }
}
