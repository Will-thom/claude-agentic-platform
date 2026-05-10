package dev.br.platform.agent;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClaudeReasoner implements Reasoner {

    @Override
    public AgentDecision decide(Map<String, Object> enrichedEvent) {

        String type = (String) enrichedEvent.get("type");
        String payload = enrichedEvent.toString();

        // Simulated LLM prompt reasoning
        String simulatedResponse = simulateLLM(type, payload);

        return parseDecision(simulatedResponse);
    }

    private String simulateLLM(String type, String payload) {

        // This simulates how a Claude-like model would respond
        if (type.contains("ERROR")) {
            return "ACTION=ALERT;REASON=LLM detected critical failure pattern";
        }

        if (type.contains("REASONING")) {
            return "ACTION=STORE;REASON=LLM classified as structured test event";
        }

        return "ACTION=STORE;REASON=LLM default classification";
    }

    private AgentDecision parseDecision(String response) {

        String[] parts = response.split(";");

        String action = parts[0].split("=")[1];
        String reason = parts[1].split("=")[1];

        return new AgentDecision(action, reason);
    }
}