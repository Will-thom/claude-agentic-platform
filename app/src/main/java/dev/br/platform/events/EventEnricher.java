package dev.br.platform.events;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class EventEnricher {

    public Map<String, Object> enrich(AgentEvent event) {

        Map<String, Object> enriched = new HashMap<>();

        enriched.put("type", event.getType());
        enriched.put("payload", event.getPayload());
        enriched.put("timestamp", Instant.now().toString());
        enriched.put("source", "claude-agentic-platform");

        return enriched;
    }
}