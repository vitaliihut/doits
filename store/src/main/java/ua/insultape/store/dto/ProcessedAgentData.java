package ua.insultape.store.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessedAgentData {
    private String roadState;
    private AgentData agentData;
}
