package edu.xupt.cs.core;

public class Interactive {
    private String sourceId;
    private String targetId;
    private String message;

    public String getSourceId() {
        return sourceId;
    }

    public String getMessage() {
        return message;
    }

    public Interactive setMessage(String message) {
        this.message = message;
        return this;
    }

    public Interactive setSourceId(String sourceId) {
        this.sourceId = sourceId;
        return this;
    }


    public String getTargetId() {
        return targetId;
    }

    public Interactive setTargetId(String targetId) {
        this.targetId = targetId;
        return this;
    }
}
