package ru.tinkoff.edu.client;

import java.time.Duration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.tinkoff.edu.request.LinkUpdate;

public class BotClient {
    private final String baseUrl = "http://localhost:8080/";
    private final WebClient webClient;
    private final int timeout = 10;

    public BotClient() {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public BotClient(String url) {
        webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    public void sendUpdate(LinkUpdate request) {
        webClient.post()
                .uri("updates")
                .body(Mono.just(request), LinkUpdate.class)
                .retrieve()
                .bodyToMono(Void.class)
                .timeout(Duration.ofSeconds(timeout))
                .block();
    }
}
