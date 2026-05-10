package dev.br.platform.events;

import dev.br.platform.agent.AgentDecision;
import dev.br.platform.agent.Reasoner;
import dev.br.platform.domain.EventLog;
import dev.br.platform.service.EventLogService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventPipeline {

    private final EventLogService service;
    private final EventEnricher enricher;
    private final Reasoner reasoner;

    public EventPipeline(EventLogService service,
                         EventEnricher enricher,
                         Reasoner reasoner) {
        this.service = service;
        this.enricher = enricher;
        this.reasoner = reasoner;
    }

    public EventLog process(AgentEvent event) {

        // 1. Enrich event with metadata
        Map<String, Object> enriched = enricher.enrich(event);

        // 2. Apply reasoning (pluggable implementation)
        AgentDecision decision = reasoner.decide(enriched);

        // 3. Compose final payload (temporary simple representation)
        String finalPayload = enriched.toString() +
                " | decision=" + decision.getAction() +
                " | reason=" + decision.getReason();

        // 4. Persist event
        return service.create(event.getType(), finalPayload);
    }
}