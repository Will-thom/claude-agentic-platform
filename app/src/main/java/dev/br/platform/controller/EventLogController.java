package dev.br.platform.controller;

import dev.br.platform.domain.EventLog;
import dev.br.platform.repository.EventLogRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventLogController {

    private final EventLogRepository repository;

    public EventLogController(EventLogRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public EventLog create(@RequestBody EventLog event) {
        return repository.save(
            new EventLog(event.getEventType(), event.getMessage())
        );
    }
}