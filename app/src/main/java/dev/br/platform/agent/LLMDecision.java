package dev.br.platform.agent;

public class LLMDecision {

    private String action;
    private String reason;
    private double confidence;

    public LLMDecision() {}

    public LLMDecision(String action, String reason, double confidence) {
        this.action = action;
        this.reason = reason;
        this.confidence = confidence;
    }

    public String getAction() {
        return action;
    }

    public String getReason() {
        return reason;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }
}