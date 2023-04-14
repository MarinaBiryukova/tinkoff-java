package ru.tinkoff.edu.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tinkoff.edu.client.BotClient;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.repository.jdbc.dto.TgChat;
import ru.tinkoff.edu.request.LinkUpdate;

@AllArgsConstructor
@Service
public class LinkUpdateSender {
    private final BotClient botClient;
    private final LinkService linkService;

    public void sendUpdate(Link link, String description) {
        LinkUpdate request = new LinkUpdate();
        request.setId(link.getId());
        request.setUrl(link.getLink());
        request.setDescription(description);
        request.setTgChatIds(linkService.getChatsForLink(link).stream().map(TgChat::getTgChatId).toList());
        botClient.sendUpdate(request);
    }
}
