package dev.br.platform.controller;

import dev.br.platform.domain.AgentDecisionLog;
import dev.br.platform.repository.AgentDecisionLogRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/decisions")
public class DecisionLogController {

    private final AgentDecisionLogRepository repository;

    public DecisionLogController(AgentDecisionLogRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<AgentDecisionLog> latest() {
        return repository.findTop20ByOrderByCreatedAtDesc();
    }
}