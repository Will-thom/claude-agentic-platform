package dev.br.platform.repository;

import dev.br.platform.domain.AgentDecisionLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgentDecisionLogRepository
        extends JpaRepository<AgentDecisionLog, Long> {

    List<AgentDecisionLog> findTop20ByOrderByCreatedAtDesc();
}