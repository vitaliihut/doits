package ua.insultape.agent.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.insultape.agent.dto.AggregatedData;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Slf4j
@Service
public class DataProcessScheduler {

    private final FileDatasource fileDatasource;
    private final ObjectMapper objectMapper;

    public DataProcessScheduler(FileDatasource fileDatasource, ObjectMapper objectMapper) {
        this.fileDatasource = fileDatasource;
        this.objectMapper = objectMapper;
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
            log.info("data: {}", objectMapper.writeValueAsString(aggregatedData));
        }
    }
}
