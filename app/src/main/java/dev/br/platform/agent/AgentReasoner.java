package dev.br.platform.agent;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AgentReasoner implements Reasoner {

    @Override
    public AgentDecision decide(Map<String, Object> enrichedEvent) {

        String type = (String) enrichedEvent.get("type");

        if (type.contains("ERROR")) {

            return new AgentDecision(
                    "ALERT",
                    "Error event detected",
                    "RULES",
                    1.0
            );
        }

        if (type.contains("ENRICH")) {

            return new AgentDecision(
                    "STORE",
                    "Normal enrichment flow",
                    "RULES",
                    1.0
            );
        }

        return new AgentDecision(
                "IGNORE",
                "No relevant action",
                "RULES",
                1.0
        );
    }
}