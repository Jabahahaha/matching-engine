package edu.harbourspace.university.matchingengine.manager.model;

public class CancelMessage {
    private final String messageId; // If you don't reassign messageId, it should be final

    public CancelMessage(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}
