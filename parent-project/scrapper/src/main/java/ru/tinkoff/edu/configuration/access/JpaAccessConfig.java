package ru.tinkoff.edu.configuration.access;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.tinkoff.edu.converter.Converter;
import ru.tinkoff.edu.repository.jpa.ChatLinkEntityRepository;
import ru.tinkoff.edu.repository.jpa.LinkEntityRepository;
import ru.tinkoff.edu.repository.jpa.TgChatEntityRepository;
import ru.tinkoff.edu.service.LinkManipulator;
import ru.tinkoff.edu.service.LinkService;
import ru.tinkoff.edu.service.TgChatService;
import ru.tinkoff.edu.service.jpa.JpaLinkService;
import ru.tinkoff.edu.service.jpa.JpaTgChatService;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfig {
    @Bean
    public LinkService linkService(
            TgChatEntityRepository tgChatEntityRepository,
            LinkEntityRepository linkEntityRepository,
            ChatLinkEntityRepository chatLinkEntityRepository,
            LinkManipulator linkManipulator,
            Converter converter
    ) {
        return new JpaLinkService(tgChatEntityRepository, linkEntityRepository, chatLinkEntityRepository, linkManipulator, converter);
    }

    @Bean
    public TgChatService tgChatService(
            TgChatEntityRepository tgChatEntityRepository,
            LinkEntityRepository linkEntityRepository,
            ChatLinkEntityRepository chatLinkEntityRepository
    ) {
        return new JpaTgChatService(tgChatEntityRepository, linkEntityRepository, chatLinkEntityRepository);
    }
}
