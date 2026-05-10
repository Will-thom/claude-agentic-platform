package dev.br.platform.agent;

public class AgentDecision {

    private String action;
    private String reason;

    public AgentDecision(String action, String reason) {
        this.action = action;
        this.reason = reason;
    }

    public String getAction() {
        return action;
    }

    public String getReason() {
        return reason;
    }
}