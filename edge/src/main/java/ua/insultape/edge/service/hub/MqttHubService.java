package ua.insultape.edge.service.hub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.insultape.edge.dto.ProcessedAgentData;

@Slf4j
@Service
public class MqttHubService implements HubService {
    @Override
    public void saveProcessedAgentData(ProcessedAgentData processedAgentData) {
        log.info("Mqtt hub service");
        //TODO: data save via mqtt
    }
}
