package dev.br.platform.agent;

public class AgentDecision {

    private String action;
    private String reason;
    private String source;
    private double confidence;

    public AgentDecision(String action,
                         String reason,
                         String source,
                         double confidence) {

        this.action = action;
        this.reason = reason;
        this.source = source;
        this.confidence = confidence;
    }

    public String getAction() {
        return action;
    }

    public String getReason() {
        return reason;
    }

    public String getSource() {
        return source;
    }

    public double getConfidence() {
        return confidence;
    }
}