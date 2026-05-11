package dev.br.platform.agent;

import dev.br.platform.domain.AgentDecisionLog;
import dev.br.platform.repository.AgentDecisionLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Primary
@Component
public class ReasonerRouter implements Reasoner {

	private final AgentReasoner agentReasoner;
	private final ClaudeReasoner claudeReasoner;
	private final AgentDecisionLogRepository repository;

	@Value("${agent.reasoning.mode:RULES}")
	private String mode;

	@Value("${agent.reasoning.confidence-threshold:0.8}")
	private double confidenceThreshold;

	public ReasonerRouter(AgentDecisionLogRepository repository, WebClient webClient) {

		this.repository = repository;

		this.agentReasoner = new AgentReasoner();
		this.claudeReasoner = new ClaudeReasoner(webClient);
	}

	@Override
	public AgentDecision decide(Map<String, Object> enrichedEvent) {

		long start = System.currentTimeMillis();

		String eventType = (String) enrichedEvent.get("type");

		AgentDecision decision;
		boolean fallbackUsed = false;

		// 1. routing strategy
		if ("CLAUDE".equalsIgnoreCase(mode)) {

			AgentDecision aiDecision = claudeReasoner.decide(enrichedEvent);

			if (aiDecision.getConfidence() >= confidenceThreshold) {

				decision = aiDecision;

			} else {

				fallbackUsed = true;

				AgentDecision fallback = agentReasoner.decide(enrichedEvent);

				decision = new AgentDecision(fallback.getAction(),
						fallback.getReason() + " | fallback from AI confidence=" + aiDecision.getConfidence(),
						"HYBRID_FALLBACK", fallback.getConfidence());
			}

		} else {

			decision = agentReasoner.decide(enrichedEvent);
		}

		long duration = System.currentTimeMillis() - start;

		// 2. persist audit log
		repository.save(new AgentDecisionLog(eventType != null ? eventType : "UNKNOWN", decision.getAction(),
				decision.getReason(), decision.getSource(), decision.getConfidence(), fallbackUsed, duration));

		return decision;
	}
}