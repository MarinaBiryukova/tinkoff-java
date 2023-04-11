package ru.tinkoff.edu.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.client.BotClient;
import ru.tinkoff.edu.client.GitHubClient;
import ru.tinkoff.edu.client.StackOverflowClient;
import ru.tinkoff.edu.converter.Converter;
import ru.tinkoff.edu.repository.ChatLinkRepository;
import ru.tinkoff.edu.repository.LinkRepository;
import ru.tinkoff.edu.service.LinkCreator;
import ru.tinkoff.edu.service.LinkService;
import ru.tinkoff.edu.service.LinkUpdater;
import ru.tinkoff.edu.service.TgChatService;
import ru.tinkoff.edu.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.service.jdbc.JdbcLinkUpdater;
import ru.tinkoff.edu.service.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {
    @Bean
    public LinkService linkService(
            ChatLinkRepository repository,
            Converter converter,
            LinkCreator linkCreator
    ) {
        return new JdbcLinkService(repository, converter, linkCreator);
    }

    @Bean
    public TgChatService tgChatService(
            ChatLinkRepository repository
    ) {
        return new JdbcTgChatService(repository);
    }

    @Bean
    public LinkUpdater linkUpdater(
            ChatLinkRepository chatLinkRepository,
            LinkRepository linkRepository,
            BotClient botClient,
            GitHubClient gitHubClient,
            StackOverflowClient stackOverflowClient
    ) {
        return new JdbcLinkUpdater(chatLinkRepository, linkRepository, botClient, gitHubClient, stackOverflowClient);
    }
}
