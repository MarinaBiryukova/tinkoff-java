package ru.tinkoff.edu.configuration.sender;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.client.BotClient;
import ru.tinkoff.edu.service.LinkService;
import ru.tinkoff.edu.service.sender.HttpLinkUpdateSender;
import ru.tinkoff.edu.service.sender.LinkUpdateSender;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpUpdateSenderConfig {
    @Bean
    public LinkUpdateSender linkUpdateSender(
            BotClient botClient,
            LinkService linkService
    ) {
        return new HttpLinkUpdateSender(botClient, linkService);
    }
}
