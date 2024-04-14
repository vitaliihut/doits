package ua.insultape.edge.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ProcessedAgentData {
    private String roadState;
    private AgentData agentData;
}
