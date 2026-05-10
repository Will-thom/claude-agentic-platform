package dev.br.platform.agent;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReasonerRouter implements Reasoner {

    private final AgentReasoner agentReasoner;
    private final ClaudeReasoner claudeReasoner;

    @Value("${agent.reasoning.mode:RULES}")
    private String mode;

    @Value("${agent.reasoning.confidence-threshold:0.8}")
    private double confidenceThreshold;

    public ReasonerRouter(AgentReasoner agentReasoner,
                          ClaudeReasoner claudeReasoner) {
        this.agentReasoner = agentReasoner;
        this.claudeReasoner = claudeReasoner;
    }

    @Override
    public AgentDecision decide(Map<String, Object> enrichedEvent) {

        if ("CLAUDE".equalsIgnoreCase(mode)) {

            AgentDecision aiDecision =
                    claudeReasoner.decide(enrichedEvent);

            if (aiDecision.getConfidence() >= confidenceThreshold) {
                return aiDecision;
            }

            return fallbackDecision(enrichedEvent, aiDecision);
        }

        return agentReasoner.decide(enrichedEvent);
    }

    private AgentDecision fallbackDecision(
            Map<String, Object> enrichedEvent,
            AgentDecision aiDecision) {

        AgentDecision fallback =
                agentReasoner.decide(enrichedEvent);

        return new AgentDecision(
                fallback.getAction(),
                fallback.getReason()
                        + " | fallback triggered from low AI confidence="
                        + aiDecision.getConfidence(),
                "HYBRID_FALLBACK",
                fallback.getConfidence()
        );
    }
}