package dev.br.platform.agent;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class ClaudeReasoner implements Reasoner {

    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ClaudeReasoner(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public AgentDecision decide(Map<String, Object> enrichedEvent) {

        String response = callClaude(enrichedEvent);

        LLMDecision llmDecision = parse(response);

        return new AgentDecision(
                llmDecision.getAction(),
                llmDecision.getReason()
        );
    }

    private String callClaude(Map<String, Object> event) {

        String prompt = buildPrompt(event);

        return webClient.post()
                .uri("https://api.anthropic.com/v1/messages")
                .header("content-type", "application/json")
                .bodyValue(prompt)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private String buildPrompt(Map<String, Object> event) {

        return """
        You are an AI reasoning engine.

        Analyze the event and return ONLY valid JSON:
        {
          "action": "STORE|ALERT|IGNORE",
          "reason": "short explanation",
          "confidence": 0.0 to 1.0
        }

        Event:
        """ + event.toString();
    }

    private LLMDecision parse(String response) {

        try {
            return objectMapper.readValue(response, LLMDecision.class);
        } catch (Exception e) {
            return new LLMDecision("ERROR", "Failed to parse LLM response", 0.0);
        }
    }
}