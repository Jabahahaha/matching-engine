package edu.harbourspace.university.matchingengine.manager.model;

public class CancelMessage {
    private String messageId;

    public CancelMessage(String messageId) {
        this.messageId = messageId;
    }

    // Getter
    public String getMessageId() {
        return messageId;
    }
}
