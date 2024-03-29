package edu.harbourspace.university.matchingengine;

import edu.harbourspace.university.matchingengine.manager.manager.TradeManager;
import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.model.Originator;
import edu.harbourspace.university.matchingengine.manager.model.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TradeManagerTest {

    private TradeManager tradeManager;

    @BeforeEach
    void setUp() {
        int maximumPosition = 100;
        tradeManager = new TradeManager(maximumPosition);
    }

    @Test
    void processOrder_shouldAddOrder_whenOrderIsFromDF() {
        // Given
        Order dfOrder = new Order(Originator.DF, "ID1", Side.BUY, 100, 10.0, "PRODUCT1");

        // When
        tradeManager.processOrder(dfOrder);

        // Then
        assertEquals(1, tradeManager.getExecutedTrades().size());
    }

    @Test
    void processCancelMessage_shouldCancelOrder_whenOrderExists() {
        // Given
        Order dfOrder = new Order(Originator.DF, "ID1", Side.BUY, 100, 10.0, "PRODUCT1");
        tradeManager.processOrder(dfOrder);

        // When
        tradeManager.processCancelMessage("ID1");

        // Then
        assertTrue(tradeManager.getExecutedTrades().isEmpty());
    }

    // Add more tests here for each method you want to test
}
