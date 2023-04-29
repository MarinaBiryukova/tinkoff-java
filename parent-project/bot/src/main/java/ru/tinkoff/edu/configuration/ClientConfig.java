package ru.tinkoff.edu.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.client.ScrapperClient;

@Configuration
public class ClientConfig {
    @Bean
    public ScrapperClient scrapperClient() {
        return new ScrapperClient();
    }
}
