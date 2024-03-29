package edu.harbourspace.university.matchingengine;

import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.model.Originator;
import edu.harbourspace.university.matchingengine.manager.model.Side;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void createOrder_ShouldCorrectlyAssignFields() {
        // Arrange
        Originator originator = Originator.DF;
        String messageId = "MSG123";
        Side side = Side.BUY;
        int size = 100;
        double price = 50.0;
        String productId = "ProductABC";

        // Act
        Order order = new Order(originator, messageId, side, size, price, productId);

        // Assert
        assertAll("Order properties",
                () -> assertEquals(originator, order.getOriginator()),
                () -> assertEquals(messageId, order.getMessageId()),
                () -> assertEquals(side, order.getSide()),
                () -> assertEquals(size, order.getSize()),
                () -> assertEquals(price, order.getPrice()),
                () -> assertEquals(productId, order.getProductId())
        );
    }
}
