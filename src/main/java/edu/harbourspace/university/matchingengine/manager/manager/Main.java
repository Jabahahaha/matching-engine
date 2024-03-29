package edu.harbourspace.university.matchingengine.manager.manager;

import edu.harbourspace.university.matchingengine.manager.processor.IMessageProcessor;
import edu.harbourspace.university.matchingengine.manager.processor.MessageProcessor;
import edu.harbourspace.university.matchingengine.manager.util.InputReader;
import edu.harbourspace.university.matchingengine.manager.util.TradePrinter;

public class Main {
    public static void main(String[] args) {
        InputReader inputReader = new InputReader();

        System.out.println("Enter the maximum position or press Enter for default (100):");
        int maximumPosition = parseMaximumPosition(inputReader.getNextLine());

        ITradeManager tradeManager = new TradeManager(maximumPosition);
        TradePrinter tradePrinter = new TradePrinter();
        IMessageProcessor processor = new MessageProcessor(tradeManager, tradePrinter);

        System.out.println("Application started. Enter messages:");

        String line;
        while (!(line = inputReader.getNextLine()).equalsIgnoreCase("FINISH")) {
            if (!line.isEmpty()) {
                processor.processMessage(line);
            }
        }

        inputReader.close();
        tradePrinter.printTrades(tradeManager.getExecutedTrades());
        System.out.println("Application finished.");
    }

    private static int parseMaximumPosition(String input) {
        int defaultMaxPosition = 100;

        if (input == null || input.isEmpty()) {
            System.out.println("Maximum position not specified. Using default value: " + defaultMaxPosition);
            return defaultMaxPosition;
        }

        if (input.startsWith("maximum-position=")) {
            try {
                return Integer.parseInt(input.split("=")[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid format for maximum-position. Using default value: " + defaultMaxPosition);
            }
        } else {
            System.err.println("Unrecognized command. Using default maximum position: " + defaultMaxPosition);
        }

        return defaultMaxPosition;
    }
}
