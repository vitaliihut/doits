package ua.insultape.store.controller;

import org.springframework.web.bind.annotation.*;
import ua.insultape.store.entity.ProcessedAgentData;
import ua.insultape.store.repository.ProcessedAgentDataRepository;

import java.util.List;

@RestController
@RequestMapping("/processed_agent_data")
public class ProcessedAgentDataController {

    private final ProcessedAgentDataRepository processedAgentDataRepository;

    public ProcessedAgentDataController(ProcessedAgentDataRepository processedAgentDataRepository) {
        this.processedAgentDataRepository = processedAgentDataRepository;
    }

    @PostMapping
    public ProcessedAgentData createProcessedAgentData(@RequestBody ProcessedAgentData processedAgentData) {
        return processedAgentDataRepository.save(processedAgentData);
    }

    @GetMapping
    public List<ProcessedAgentData> getAllProcessedAgentData() {
        return processedAgentDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ProcessedAgentData getProcessedAgentDataById(@PathVariable Integer id) {
        return processedAgentDataRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ProcessedAgentData updateProcessedAgentData(@PathVariable Integer id, @RequestBody ProcessedAgentData processedAgentData) {
        processedAgentData.setId(id);
        return processedAgentDataRepository.save(processedAgentData);
    }

    @DeleteMapping("/{id}")
    public void deleteProcessedAgentData(@PathVariable Integer id) {
        processedAgentDataRepository.deleteById(id);
    }
}
