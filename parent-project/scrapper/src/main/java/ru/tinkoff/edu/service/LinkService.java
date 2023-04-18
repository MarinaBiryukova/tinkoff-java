package ru.tinkoff.edu.service;

import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.repository.jdbc.dto.TgChat;
import ru.tinkoff.edu.response.LinkResponse;
import ru.tinkoff.edu.response.ListLinksResponse;

import java.net.URI;
import java.util.List;

public interface LinkService {
    LinkResponse add(Long tgChatId, URI url);
    LinkResponse remove(Long tgChatId, URI url);
    ListLinksResponse listAll(Long tgChatId);
    List<Link> findLinksForUpdate();
    List<TgChat> getChatsForLink(Link link);
    void updateLink(Link link);
}
