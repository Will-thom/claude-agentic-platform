package dev.br.platform.events;

import dev.br.platform.domain.EventLog;
import dev.br.platform.service.EventLogService;
import org.springframework.stereotype.Component;

@Component
public class EventPipeline {

    private final EventLogService service;

    public EventPipeline(EventLogService service) {
        this.service = service;
    }

    public EventLog process(AgentEvent event) {

        // aqui futuramente entram regras de AI / Claude / routing
        return service.create(event.getType(), event.getPayload());
    }
}