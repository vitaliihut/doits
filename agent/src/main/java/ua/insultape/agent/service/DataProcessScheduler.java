package ua.insultape.agent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.insultape.agent.config.MqttGateway;
import ua.insultape.agent.dto.AggregatedData;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Slf4j
@Service
public class DataProcessScheduler {
//    private static final String MQTT_TOPIC = System.getProperty("MQTT_TOPIC", "agent");

    private final FileDatasource fileDatasource;
    private final ObjectMapper objectMapper;
    private final MqttGateway mqttGateway;

    public DataProcessScheduler(FileDatasource fileDatasource, ObjectMapper objectMapper, MqttGateway mqttGateway) {
        this.fileDatasource = fileDatasource;
        this.objectMapper = objectMapper;
        this.mqttGateway = mqttGateway;

        try {
            this.fileDatasource.openFiles();
        } catch (IOException e) {
            log.error("Can`t open file ", e);
        }

    }

    @Scheduled(fixedDelay = 10000)
    public void readDataAndProcess() throws IOException {
        List<AggregatedData> aggregatedDataList = fileDatasource.read();
        log.info("Read data:");
        for (AggregatedData aggregatedData : aggregatedDataList) {
            String json = objectMapper.writeValueAsString(aggregatedData);
            log.info("data: {}", json);
            mqttGateway.sendToMqtt(json);
        }
    }
}
