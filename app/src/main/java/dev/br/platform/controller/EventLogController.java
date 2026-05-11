package dev.br.platform.controller;

import dev.br.platform.domain.EventLog;
import dev.br.platform.events.AgentEvent;
import dev.br.platform.events.EventPipeline;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventLogController {

    private final EventPipeline pipeline;

    public EventLogController(EventPipeline pipeline) {
        this.pipeline = pipeline;
    }

    @PostMapping
    public EventLog create(@RequestBody AgentEvent event) {

        return pipeline.process(event);
    }
}