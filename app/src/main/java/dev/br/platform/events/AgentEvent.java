package dev.br.platform.events;

public class AgentEvent {

    private String type;
    private String payload;

    public AgentEvent(String type, String payload) {
        this.type = type;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }
}