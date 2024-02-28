package ua.insultape.store.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;
import ua.insultape.store.config.WebSocketHandlerConfig;
import ua.insultape.store.dto.ProcessedAgentData;
import ua.insultape.store.entity.ProcessedAgentDataDb;
import ua.insultape.store.repository.ProcessedAgentDataRepository;

import java.util.List;

@RestController
@RequestMapping("/processed_agent_data")
public class ProcessedAgentDataController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ProcessedAgentDataRepository processedAgentDataRepository;
    private final WebSocketHandlerConfig webSocketHandlerConfig;

    public ProcessedAgentDataController(ProcessedAgentDataRepository processedAgentDataRepository, WebSocketHandlerConfig webSocketHandlerConfig) {
        this.processedAgentDataRepository = processedAgentDataRepository;
        this.webSocketHandlerConfig = webSocketHandlerConfig;
    }

    @PostMapping
    public ProcessedAgentDataDb createProcessedAgentData(@RequestBody ProcessedAgentData processedAgentData) throws JsonProcessingException {
        ProcessedAgentDataDb processedAgentDataDb = new ProcessedAgentDataDb();
        processedAgentDataDb.setRoadState(processedAgentData.getRoadState());
        processedAgentDataDb.setUserId(processedAgentData.getAgentData().getUserId());
        processedAgentDataDb.setX(processedAgentData.getAgentData().getAccelerometer().getX());
        processedAgentDataDb.setY(processedAgentData.getAgentData().getAccelerometer().getY());
        processedAgentDataDb.setZ(processedAgentData.getAgentData().getAccelerometer().getZ());
        processedAgentDataDb.setLatitude(processedAgentData.getAgentData().getGps().getLatitude());
        processedAgentDataDb.setLongitude(processedAgentData.getAgentData().getGps().getLongitude());
        processedAgentDataDb.setTimestamp(processedAgentData.getAgentData().getTimestamp());

        webSocketHandlerConfig.sendDataToSubscribers(
                processedAgentData.getAgentData().getUserId(),
                objectMapper.writeValueAsString(processedAgentData)
        );

        return processedAgentDataRepository.save(processedAgentDataDb);
    }

    @GetMapping
    public List<ProcessedAgentDataDb> getAllProcessedAgentData() {
        return processedAgentDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ProcessedAgentDataDb getProcessedAgentDataById(@PathVariable Integer id) {
        return processedAgentDataRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ProcessedAgentDataDb updateProcessedAgentData(@PathVariable Integer id, @RequestBody ProcessedAgentData processedAgentData) throws JsonProcessingException {
        ProcessedAgentDataDb processedAgentDataDb = new ProcessedAgentDataDb();
        processedAgentDataDb.setId(id);
        processedAgentDataDb.setRoadState(processedAgentData.getRoadState());
        processedAgentDataDb.setUserId(processedAgentData.getAgentData().getUserId());
        processedAgentDataDb.setX(processedAgentData.getAgentData().getAccelerometer().getX());
        processedAgentDataDb.setY(processedAgentData.getAgentData().getAccelerometer().getY());
        processedAgentDataDb.setZ(processedAgentData.getAgentData().getAccelerometer().getZ());
        processedAgentDataDb.setLatitude(processedAgentData.getAgentData().getGps().getLatitude());
        processedAgentDataDb.setLongitude(processedAgentData.getAgentData().getGps().getLongitude());
        processedAgentDataDb.setTimestamp(processedAgentData.getAgentData().getTimestamp());

        webSocketHandlerConfig.sendDataToSubscribers(
                processedAgentData.getAgentData().getUserId(),
                objectMapper.writeValueAsString(processedAgentData)
        );

        return processedAgentDataRepository.save(processedAgentDataDb);
    }

    @DeleteMapping("/{id}")
    public void deleteProcessedAgentData(@PathVariable Integer id) {
        processedAgentDataRepository.deleteById(id);
    }
}
