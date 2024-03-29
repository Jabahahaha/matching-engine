package edu.harbourspace.university.matchingengine.manager.processor;

import edu.harbourspace.university.matchingengine.manager.manager.ITradeManager;
import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.model.CancelMessage;
import edu.harbourspace.university.matchingengine.manager.util.InputParser;
import edu.harbourspace.university.matchingengine.manager.util.TradePrinter;

public class MessageProcessor implements IMessageProcessor {
    private final ITradeManager tradeManager;
    private final TradePrinter tradePrinter;
    private final InputParser inputParser;

    public MessageProcessor(ITradeManager tradeManager, TradePrinter tradePrinter) {
        this.tradeManager = tradeManager;
        this.tradePrinter = tradePrinter;
        this.inputParser = new InputParser();
    }

    @Override
    public void processMessage(String message) {
        Object parsedInput = inputParser.parse(message);

        if (parsedInput instanceof Order) {
            tradeManager.processOrder((Order) parsedInput);
        } else if (parsedInput instanceof CancelMessage) {
            tradeManager.processCancelMessage(((CancelMessage) parsedInput).getMessageId());
        } else if ("FINISH".equalsIgnoreCase(message)) {
            tradePrinter.printTrades(tradeManager.getExecutedTrades());
        } else {
            System.out.println("Unrecognized message format: " + message);
        }
    }

    public void printTrades() {
        tradePrinter.printTrades(tradeManager.getExecutedTrades());
    }

}
