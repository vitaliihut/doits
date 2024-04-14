package ua.insultape.hub.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.insultape.hub.dto.ProcessedAgentData;
import ua.insultape.hub.service.AccumulationService;

@Slf4j
@RestController
@RequestMapping("/hub")
public class HubController {

    private final AccumulationService accumulationService;

    public HubController(AccumulationService accumulationService) {
        this.accumulationService = accumulationService;
    }

    @PostMapping
    public void saveProcessedAgentData(@RequestBody ProcessedAgentData processedAgentData) {
        accumulationService.accumulateData(processedAgentData);
        log.info("Data was successfully accumulated");
    }
}