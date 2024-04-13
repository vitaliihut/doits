package ua.insultape.hub.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.insultape.hub.dto.ProcessedAgentData;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AccumulationService {
    private static final int MAX_LIST_SIZE = 5;
    private final List<ProcessedAgentData> processedAgentDataList = new ArrayList<>();

    private final StoreApiService storeApiService;

    public AccumulationService(StoreApiService storeApiService) {
        this.storeApiService = storeApiService;
    }

    public void accumulateData(ProcessedAgentData processedAgentData) {
        processedAgentDataList.add(processedAgentData);
        if (processedAgentDataList.size() >= MAX_LIST_SIZE) {
            saveAccumulatedData();
        }
    }

    private void saveAccumulatedData() {
        storeApiService.saveData(processedAgentDataList);
        processedAgentDataList.clear();
        log.info("Accumulated data was successfully sent");
    }
}
