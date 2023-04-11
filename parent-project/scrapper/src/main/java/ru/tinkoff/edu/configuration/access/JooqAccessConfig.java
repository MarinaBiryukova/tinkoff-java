package ru.tinkoff.edu.configuration.access;

import org.jooq.DSLContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.client.BotClient;
import ru.tinkoff.edu.client.GitHubClient;
import ru.tinkoff.edu.client.StackOverflowClient;
import ru.tinkoff.edu.converter.Converter;
import ru.tinkoff.edu.service.LinkCreator;
import ru.tinkoff.edu.service.LinkService;
import ru.tinkoff.edu.service.LinkUpdater;
import ru.tinkoff.edu.service.TgChatService;
import ru.tinkoff.edu.service.jooq.JooqLinkService;
import ru.tinkoff.edu.service.jooq.JooqLinkUpdater;
import ru.tinkoff.edu.service.jooq.JooqTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jooq")
public class JooqAccessConfig {
    @Bean
    public LinkService linkService(
            DSLContext context,
            Converter converter,
            LinkCreator linkCreator
    ) {
        return new JooqLinkService(context, converter, linkCreator);
    }

    @Bean
    public TgChatService tgChatService(
            DSLContext context
    ) {
        return new JooqTgChatService(context);
    }

    @Bean
    public LinkUpdater linkUpdater(
            BotClient botClient,
            GitHubClient gitHubClient,
            StackOverflowClient stackOverflowClient,
            DSLContext context
    ) {
        return new JooqLinkUpdater(botClient, gitHubClient, stackOverflowClient, context);
    }
}
