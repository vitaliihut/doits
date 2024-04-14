package ua.insultape.edge.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.insultape.edge.dto.AgentData;
import ua.insultape.edge.dto.ProcessedAgentData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class DataProcessingService {

    private static final int PAST_READINGS_LIMIT = 5; // Number of past readings to keep
    private static final int SMOOTH_ROAD_RANGE = 3000; // road metric

    private final List<Integer> pastReadings = new ArrayList<>();

    public void process(AgentData agentData) {
        ProcessedAgentData processedData = processRoadState(agentData);
        log.info("Processed agent data: {}", processedData);
        // TODO: store data using hub
    }

    private ProcessedAgentData processRoadState(AgentData agentData) {
        int currentYReading = agentData.getAccelerometer().getZ();

        addToPastReadings(currentYReading);

        int minReading = Collections.min(pastReadings);
        int maxReading = Collections.max(pastReadings);
        int range = maxReading - minReading;

        String roadState = analyzeRoadState(range);
        log.info("Road range: {}", range);
        log.info("past len: {}", pastReadings.size());
        log.info("first {} and last element {}", pastReadings.getFirst(), pastReadings.getLast());

        ProcessedAgentData processedData = new ProcessedAgentData();
        processedData.setRoadState(roadState);
        processedData.setAgentData(agentData);

        return processedData;
    }

    private void addToPastReadings(int currentYReading) {
        pastReadings.add(currentYReading);
        if (pastReadings.size() > PAST_READINGS_LIMIT) {
            pastReadings.remove(0);
        }
    }

    private String analyzeRoadState(int range) {
        if (range < SMOOTH_ROAD_RANGE) {
            return "Smooth";
        } else {
            return "Rough";
        }
    }
}

