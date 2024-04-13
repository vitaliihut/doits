package ua.insultape.hub.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ua.insultape.hub.dto.ProcessedAgentData;

import java.util.List;

@Service
public class StoreApiService {

    private static final String API_STORE_SAVE_URI = "/processed_agent_data";
    private final WebClient webClient;

    public StoreApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public void saveData(List<ProcessedAgentData> processedAgentDataList) {
        Flux<ProcessedAgentData> processedAgentDataFlux = Flux.fromIterable(processedAgentDataList);

        processedAgentDataFlux.flatMap(processedAgentData ->
            webClient.post()
                    .uri(API_STORE_SAVE_URI)
                    .body(Mono.just(processedAgentData), ProcessedAgentData.class)
                    .retrieve()
                    .toEntity(ProcessedAgentData.class))
                .subscribe();
    }
}
