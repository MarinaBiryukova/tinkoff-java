package ru.tinkoff.edu.service;

import ru.tinkoff.edu.response.LinkResponse;
import ru.tinkoff.edu.response.ListLinksResponse;

import java.net.URI;

public interface LinkService {
    LinkResponse add(Long tgChatId, URI url);
    LinkResponse remove(Long tgChatId, URI url);
    ListLinksResponse listAll(Long tgChatId);
}
