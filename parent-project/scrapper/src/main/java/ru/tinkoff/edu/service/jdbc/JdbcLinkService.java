package ru.tinkoff.edu.service.jdbc;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.converter.Converter;
import ru.tinkoff.edu.repository.ChatLinkRepository;
import ru.tinkoff.edu.response.LinkResponse;
import ru.tinkoff.edu.response.ListLinksResponse;
import ru.tinkoff.edu.service.LinkCreator;
import ru.tinkoff.edu.service.LinkService;

import java.net.URI;

@AllArgsConstructor
public class JdbcLinkService implements LinkService {
    private final ChatLinkRepository repository;
    private final Converter converter;
    private final LinkCreator linkCreator;

    @Override
    public LinkResponse add(Long tgChatId, URI url) {
        return converter.linkToLinkResponse(repository.trackLink(tgChatId, linkCreator.createLink(url)));
    }

    @Override
    public LinkResponse remove(Long tgChatId, URI url) {
        return converter.linkToLinkResponse(repository.untrackLink(tgChatId, url));
    }

    @Override
    public ListLinksResponse listAll(Long tgChatId) {
        return converter.linksToListLinksResponse(repository.getAllLinks(tgChatId));
    }
}
