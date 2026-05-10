package dev.br.platform.agent;

import dev.br.platform.events.AgentEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AgentReasoner {

    public AgentDecision decide(Map<String, Object> enrichedEvent) {

        String type = (String) enrichedEvent.get("type");

        if (type.contains("ERROR")) {
            return new AgentDecision("ALERT", "Error event detected");
        }

        if (type.contains("ENRICH")) {
            return new AgentDecision("STORE", "Normal enrichment flow");
        }

        return new AgentDecision("IGNORE", "No relevant action");
    }
}