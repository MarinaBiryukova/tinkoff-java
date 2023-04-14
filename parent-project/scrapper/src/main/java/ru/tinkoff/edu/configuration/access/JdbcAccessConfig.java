package ru.tinkoff.edu.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.converter.Converter;
import ru.tinkoff.edu.repository.ChatLinkRepository;
import ru.tinkoff.edu.repository.LinkRepository;
import ru.tinkoff.edu.service.LinkManipulator;
import ru.tinkoff.edu.service.LinkService;
import ru.tinkoff.edu.service.TgChatService;
import ru.tinkoff.edu.service.jdbc.JdbcLinkService;
import ru.tinkoff.edu.service.jdbc.JdbcTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfig {
    @Bean
    public LinkService linkService(
            ChatLinkRepository chatLinkRepository,
            LinkRepository linkRepository,
            Converter converter,
            LinkManipulator linkManipulator
    ) {
        return new JdbcLinkService(chatLinkRepository, linkRepository, converter, linkManipulator);
    }

    @Bean
    public TgChatService tgChatService(
            ChatLinkRepository repository
    ) {
        return new JdbcTgChatService(repository);
    }
}
