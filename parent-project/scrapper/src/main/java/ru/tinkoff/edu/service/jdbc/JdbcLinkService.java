package ru.tinkoff.edu.service.jdbc;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.converter.Converter;
import ru.tinkoff.edu.repository.jdbc.ChatLinkRepository;
import ru.tinkoff.edu.repository.jdbc.LinkRepository;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.repository.jdbc.dto.TgChat;
import ru.tinkoff.edu.response.LinkResponse;
import ru.tinkoff.edu.response.ListLinksResponse;
import ru.tinkoff.edu.service.LinkManipulator;
import ru.tinkoff.edu.service.LinkService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
public class JdbcLinkService implements LinkService {
    private final ChatLinkRepository repository;
    private final LinkRepository linkRepository;
    private final Converter converter;
    private final LinkManipulator linkManipulator;

    @Override
    public LinkResponse add(Long tgChatId, URI url) {
        return converter.linkToLinkResponse(repository.trackLink(tgChatId, linkManipulator.createLink(url)));
    }

    @Override
    public LinkResponse remove(Long tgChatId, URI url) {
        return converter.linkToLinkResponse(repository.untrackLink(tgChatId, url));
    }

    @Override
    public ListLinksResponse listAll(Long tgChatId) {
        return converter.linksToListLinksResponse(repository.getAllLinks(tgChatId));
    }

    @Override
    public List<Link> findLinksForUpdate() {
        return linkRepository.findAllForUpdate();
    }

    @Override
    public List<TgChat> getChatsForLink(Link link) {
        return repository.getChatsForLink(link);
    }

    @Override
    public void updateLink(Link link) {
        linkRepository.update(link);
    }
}
