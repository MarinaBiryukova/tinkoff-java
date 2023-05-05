package ru.tinkoff.edu.service.sender;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.client.BotClient;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.repository.jdbc.dto.TgChat;
import ru.tinkoff.edu.request.LinkUpdate;
import ru.tinkoff.edu.service.LinkService;

@AllArgsConstructor
public class HttpLinkUpdateSender implements LinkUpdateSender {
    private final BotClient botClient;
    private final LinkService linkService;

    @Override
    public void sendUpdate(Link link, String description) {
        LinkUpdate request = new LinkUpdate();
        request.setId(link.getId());
        request.setUrl(link.getLink());
        request.setDescription(description);
        request.setTgChatIds(linkService.getChatsForLink(link).stream().map(TgChat::getTgChatId).toList());
        botClient.sendUpdate(request);
    }
}
