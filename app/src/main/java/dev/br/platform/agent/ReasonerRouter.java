package dev.br.platform.agent;

import dev.br.platform.entity.AgentDecisionLog;
import dev.br.platform.repository.AgentDecisionLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReasonerRouter implements Reasoner {

    private final AgentReasoner agentReasoner;
    private final ClaudeReasoner claudeReasoner;
    private final AgentDecisionLogRepository repository;

    @Value("${agent.reasoning.mode:RULES}")
    private String mode;

    @Value("${agent.reasoning.confidence-threshold:0.8}")
    private double confidenceThreshold;

    public ReasonerRouter(AgentReasoner agentReasoner,
                          ClaudeReasoner claudeReasoner,
                          AgentDecisionLogRepository repository) {
        this.agentReasoner = agentReasoner;
        this.claudeReasoner = claudeReasoner;
        this.repository = repository;
    }

    @Override
    public AgentDecision decide(Map<String, Object> enrichedEvent) {

        long start = System.currentTimeMillis();

        String eventType = (String) enrichedEvent.get("type");

        AgentDecision decision;
        boolean fallbackUsed = false;

        if ("CLAUDE".equalsIgnoreCase(mode)) {

            AgentDecision aiDecision = claudeReasoner.decide(enrichedEvent);

            if (aiDecision.getConfidence() >= confidenceThreshold) {

                decision = aiDecision;

            } else {

                fallbackUsed = true;

                AgentDecision fallback = agentReasoner.decide(enrichedEvent);

                decision = new AgentDecision(
                        fallback.getAction(),
                        fallback.getReason()
                                + " | fallback from AI confidence="
                                + aiDecision.getConfidence(),
                        "HYBRID_FALLBACK",
                        fallback.getConfidence()
                );
            }

        } else {

            decision = agentReasoner.decide(enrichedEvent);
        }

        long duration = System.currentTimeMillis() - start;

        repository.save(new AgentDecisionLog(
                eventType != null ? eventType : "UNKNOWN",
                decision.getAction(),
                decision.getReason(),
                decision.getSource(),
                decision.getConfidence(),
                fallbackUsed,
                duration
        ));

        return decision;
    }
}