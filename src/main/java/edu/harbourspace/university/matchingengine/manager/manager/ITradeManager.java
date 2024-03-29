package edu.harbourspace.university.matchingengine.manager.manager;

import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.model.Trade;
import java.util.List;

public interface ITradeManager {
    void processOrder(Order order);
    void processCancelMessage(String messageId);
    List<Trade> getExecutedTrades();
}
