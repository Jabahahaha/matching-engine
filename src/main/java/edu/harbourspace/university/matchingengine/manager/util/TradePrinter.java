package edu.harbourspace.university.matchingengine.manager.util;

import edu.harbourspace.university.matchingengine.manager.model.Trade;

import java.util.List;

public class TradePrinter {

    public void printTrades(List<Trade> trades) {
        for (Trade trade : trades) {
            System.out.println(trade.toString());
        }
    }
}

