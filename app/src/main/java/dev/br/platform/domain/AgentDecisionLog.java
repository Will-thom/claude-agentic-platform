package dev.br.platform.domain;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "agent_decision_log")
public class AgentDecisionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventType;
    private String action;
    private String reason;
    private String source;
    private double confidence;

    private boolean fallbackUsed;
    private long processingTimeMs;

    private Instant createdAt = Instant.now();

    public AgentDecisionLog() {}

    public AgentDecisionLog(String eventType,
                            String action,
                            String reason,
                            String source,
                            double confidence,
                            boolean fallbackUsed,
                            long processingTimeMs) {

        this.eventType = eventType;
        this.action = action;
        this.reason = reason;
        this.source = source;
        this.confidence = confidence;
        this.fallbackUsed = fallbackUsed;
        this.processingTimeMs = processingTimeMs;
    }

    public Long getId() {
        return id;
    }
}