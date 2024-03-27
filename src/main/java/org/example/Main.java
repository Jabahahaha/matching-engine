package org.example;

import manager.TradeManager;
import processor.MessageProcessor;

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
                    processor.processMessage(line);
                    if ("FINISH".equals(line)) break;
                }
            }
        }
        System.out.println("Application finished.");
    }
}
