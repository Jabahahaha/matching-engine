package edu.harbourspace.university.matchingengine;

import edu.harbourspace.university.matchingengine.manager.model.CancelMessage;
import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.model.Originator;
import edu.harbourspace.university.matchingengine.manager.model.Side;
import edu.harbourspace.university.matchingengine.manager.util.InputParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputParserTest {

    private final InputParser inputParser = new InputParser();

    @Test
    void parse_ShouldReturnOrder_WhenGivenValidOrderInput() {
        // Arrange
        String inputLine = "DF MSG123 BUY 100 50.0 ProductABC";

        // Act
        Object result = inputParser.parse(inputLine);

        // Assert
        assertTrue(result instanceof Order);
        Order order = (Order) result;
        assertAll("Order properties",
                () -> assertEquals(Originator.DF, order.getOriginator()),
                () -> assertEquals("MSG123", order.getMessageId()),
                () -> assertEquals(Side.BUY, order.getSide()),
                () -> assertEquals(100, order.getSize()),
                () -> assertEquals(50.0, order.getPrice()),
                () -> assertEquals("ProductABC", order.getProductId())
        );
    }

    @Test
    void parse_ShouldReturnCancelMessage_WhenGivenValidCancelInput() {
        // Arrange
        String inputLine = "DF MSG123 CANCEL";

        // Act
        Object result = inputParser.parse(inputLine);

        // Assert
        assertTrue(result instanceof CancelMessage);
        CancelMessage cancelMessage = (CancelMessage) result;
        assertEquals("MSG123", cancelMessage.getMessageId());
    }

    @Test
    void parse_ShouldReturnString_WhenGivenFinishInput() {
        // Arrange
        String inputLine = "FINISH";

        // Act
        Object result = inputParser.parse(inputLine);

        // Assert
        assertEquals("FINISH", result);
    }

    @Test
    void parse_ShouldReturnNull_WhenGivenInvalidInput() {
        // Arrange
        String inputLine = "INVALID INPUT";

        // Act
        Object result = inputParser.parse(inputLine);

        // Assert
        assertNull(result);
    }
}
