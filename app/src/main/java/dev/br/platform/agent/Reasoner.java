package dev.br.platform.agent;

import java.util.Map;

public interface Reasoner {

    AgentDecision decide(Map<String, Object> enrichedEvent);
}