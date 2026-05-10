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

    public ReasonerRouter(AgentReasoner agentReasoner,
                          ClaudeReasoner claudeReasoner) {
        this.agentReasoner = agentReasoner;
        this.claudeReasoner = claudeReasoner;
    }

    @Override
    public AgentDecision decide(Map<String, Object> enrichedEvent) {

        if ("CLAUDE".equalsIgnoreCase(mode)) {
            return claudeReasoner.decide(enrichedEvent);
        }

        return agentReasoner.decide(enrichedEvent);
    }
}