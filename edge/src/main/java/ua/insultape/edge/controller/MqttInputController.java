package ua.insultape.edge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Controller;
import ua.insultape.edge.dto.AgentData;
import ua.insultape.edge.service.DataProcessingService;

@Slf4j
@Controller
public class MqttInputController {

    private final DataProcessingService dataProcessingService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MqttInputController(DataProcessingService dataProcessingService) {
        this.dataProcessingService = dataProcessingService;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            log.info("Payload: {}", message.getPayload());
            try {
                AgentData agentData = objectMapper.readValue((String) message.getPayload(), AgentData.class);
                dataProcessingService.process(agentData);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
