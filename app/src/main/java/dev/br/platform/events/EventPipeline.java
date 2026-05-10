package dev.br.platform.events;

import dev.br.platform.agent.AgentDecision;
import dev.br.platform.agent.AgentReasoner;
import dev.br.platform.domain.EventLog;
import dev.br.platform.service.EventLogService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventPipeline {

    private final EventLogService service;
    private final EventEnricher enricher;
    private final AgentReasoner reasoner;

    public EventPipeline(EventLogService service,
                         EventEnricher enricher,
                         AgentReasoner reasoner) {
        this.service = service;
        this.enricher = enricher;
        this.reasoner = reasoner;
    }

    public EventLog process(AgentEvent event) {

        Map<String, Object> enriched = enricher.enrich(event);

        AgentDecision decision = reasoner.decide(enriched);

        String finalPayload = enriched + " | decision=" + decision.getAction();

        return service.create(event.getType(), finalPayload);
    }
}