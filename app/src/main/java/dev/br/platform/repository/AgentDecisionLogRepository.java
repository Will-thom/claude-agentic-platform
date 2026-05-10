package dev.br.platform.repository;

import dev.br.platform.entity.AgentDecisionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentDecisionLogRepository
        extends JpaRepository<AgentDecisionLog, Long> {
}