package ru.tinkoff.edu.configuration.receiver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.model.Bot;
import ru.tinkoff.edu.service.HttpLinkUpdateReceiver;
import ru.tinkoff.edu.service.LinkUpdateReceiver;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpLinkUpdateReceiverConfig {
    @Bean
    public LinkUpdateReceiver linkUpdateReceiver(
            Bot bot
    ) {
        return new HttpLinkUpdateReceiver(bot);
    }
}
