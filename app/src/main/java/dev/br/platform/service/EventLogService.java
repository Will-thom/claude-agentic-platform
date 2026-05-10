package dev.br.platform.service;

import dev.br.platform.domain.EventLog;
import dev.br.platform.repository.EventLogRepository;
import org.springframework.stereotype.Service;

@Service
public class EventLogService {

    private final EventLogRepository repository;

    public EventLogService(EventLogRepository repository) {
        this.repository = repository;
    }

    public EventLog create(String eventType, String message) {
        return repository.save(new EventLog(eventType, message));
    }
}