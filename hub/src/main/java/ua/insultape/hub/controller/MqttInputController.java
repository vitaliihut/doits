package ua.insultape.hub.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Controller;
import ua.insultape.hub.dto.ProcessedAgentData;
import ua.insultape.hub.service.AccumulationService;

@Slf4j
@Controller
public class MqttInputController {

    private final AccumulationService accumulationService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MqttInputController(AccumulationService accumulationService) {
        this.accumulationService = accumulationService;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            log.info("Payload: {}", message.getPayload());
            try {
                ProcessedAgentData processedAgentData = objectMapper.readValue((String) message.getPayload(), ProcessedAgentData.class);
                accumulationService.accumulateData(processedAgentData);
                log.info("Data was successfully accumulated");
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
