package edu.harbourspace.university.matchingengine;

import edu.harbourspace.university.matchingengine.manager.model.CancelMessage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CancelMessageTest {

    @Test
    void cancelMessage_ShouldContainCorrectMessageId() {
        // Arrange
        String messageId = "MSG123";

        // Act
        CancelMessage cancelMessage = new CancelMessage(messageId);

        // Assert
        assertEquals(messageId, cancelMessage.getMessageId());
    }
}
