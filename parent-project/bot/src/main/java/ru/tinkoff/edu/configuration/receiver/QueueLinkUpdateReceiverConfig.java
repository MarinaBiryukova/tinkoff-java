package ru.tinkoff.edu.configuration.receiver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.model.Bot;
import ru.tinkoff.edu.service.AbstractLinkUpdateReceiver;
import ru.tinkoff.edu.service.QueueLinkUpdateReceiver;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class QueueLinkUpdateReceiverConfig {
    @Bean
    public AbstractLinkUpdateReceiver linkUpdateReceiver(
            Bot bot
    ) {
        return new QueueLinkUpdateReceiver(bot);
    }
}
