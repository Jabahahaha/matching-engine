package edu.harbourspace.university.matchingengine.manager.model;

public class CancelMessage {
    private final String messageId;

    public CancelMessage(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}
