package ua.insultape.edge.service.hub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.insultape.edge.config.MqttGateway;
import ua.insultape.edge.dto.ProcessedAgentData;

@Slf4j
@Service
public class MqttHubService implements HubService {

    private final MqttGateway mqttGateway;
    private final ObjectMapper objectMapper;

    public MqttHubService(MqttGateway mqttGateway, ObjectMapper objectMapper) {
        this.mqttGateway = mqttGateway;
        this.objectMapper = objectMapper;
    }

    @Override
    public void saveProcessedAgentData(ProcessedAgentData processedAgentData) {
        try {
            String json = objectMapper.writeValueAsString(processedAgentData);
            log.info("Mqtt hub service");
            mqttGateway.sendToMqtt(json);
        } catch (Exception e) {
            log.error("Unexpected exception occurred:", e);
        }

    }
}
