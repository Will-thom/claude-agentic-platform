package dev.br.platform.events;

import dev.br.platform.domain.EventLog;
import dev.br.platform.service.EventLogService;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EventPipeline {

    private final EventLogService service;
    private final EventEnricher enricher;

    public EventPipeline(EventLogService service, EventEnricher enricher) {
        this.service = service;
        this.enricher = enricher;
    }

    public EventLog process(AgentEvent event) {

        Map<String, Object> enriched = enricher.enrich(event);

        String type = (String) enriched.get("type");
        String payload = enriched.toString();

        return service.create(type, payload);
    }
}