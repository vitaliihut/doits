package ua.insultape.edge.service.hub;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.insultape.edge.dto.ProcessedAgentData;

@Slf4j
@Service
public class HttpHubService implements HubService {
    private static final String API_HUB_SAVE_URI = "/hub";
    private final WebClient webClient;

    public HttpHubService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public void saveProcessedAgentData(ProcessedAgentData processedAgentData) {
        log.info("Http hub save");
        webClient.post()
                .uri(API_HUB_SAVE_URI)
                .body(Mono.just(processedAgentData), ProcessedAgentData.class)
                .retrieve()
                .toEntity(ProcessedAgentData.class)
                .block();
    }
}
