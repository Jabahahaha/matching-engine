package edu.harbourspace.university.matchingengine.manager.manager;

import edu.harbourspace.university.matchingengine.manager.processor.IMessageProcessor;
import edu.harbourspace.university.matchingengine.manager.processor.MessageProcessor;
import edu.harbourspace.university.matchingengine.manager.util.InputReader;
import edu.harbourspace.university.matchingengine.manager.util.TradePrinter;

public class Main {
    public static void main(String[] args) {
        int maximumPosition = parseMaximumPosition(args);

        ITradeManager tradeManager = new TradeManager(maximumPosition);
        TradePrinter tradePrinter = new TradePrinter();
        IMessageProcessor processor = new MessageProcessor(tradeManager, tradePrinter);

        InputReader inputReader = new InputReader();

        System.out.println("Application started. Enter messages:");

        String line;
        while ((line = inputReader.getNextLine()) != null && !line.isEmpty()) {
            processor.processMessage(line);
        }

        inputReader.close();
        System.out.println("Application finished.");
    }

    private static int parseMaximumPosition(String[] args) {
        int defaultMaxPosition = 100;

        for (String arg : args) {
            if (arg.startsWith("maximum-position=")) {
                try {
                    return Integer.parseInt(arg.split("=")[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid format for maximum-position. Using default value: " + defaultMaxPosition);
                }
            }
        }

        System.err.println("Maximum position not specified. Using default value: " + defaultMaxPosition);
        return defaultMaxPosition;
    }
}
