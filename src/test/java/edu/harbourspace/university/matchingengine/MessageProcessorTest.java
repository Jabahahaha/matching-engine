package edu.harbourspace.university.matchingengine;

import edu.harbourspace.university.matchingengine.manager.model.Order;
import edu.harbourspace.university.matchingengine.manager.processor.MessageProcessor;

public class MessageProcessorTest {
    private MessageProcessor messageProcessor;

    public void testMessageProcessor() {
        // given
        String order = "kjasbfjka";

        // when
        Order order = messageProcessor.processMessage(order);

        // then
        assertEquals("")
    }
}
