package dev.br.platform.controller;

import dev.br.platform.domain.EventLog;
import dev.br.platform.service.EventLogService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventLogController {

    private final EventLogService service;

    public EventLogController(EventLogService service) {
        this.service = service;
    }

    @PostMapping
    public EventLog create(@RequestBody EventLog event) {
        return service.create(event.getEventType(), event.getMessage());
    }
}