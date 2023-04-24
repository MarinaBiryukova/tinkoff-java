package ru.tinkoff.edu.service.sender;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.tinkoff.edu.repository.jdbc.dto.Link;
import ru.tinkoff.edu.repository.jdbc.dto.TgChat;
import ru.tinkoff.edu.request.LinkUpdate;
import ru.tinkoff.edu.service.LinkService;

@AllArgsConstructor
public class QueueLinkUpdateSender implements LinkUpdateSender {
    private final LinkService linkService;
    private final RabbitTemplate template;
    private final Queue queue;

    @Override
    public void sendUpdate(Link link, String description) {
        LinkUpdate request = new LinkUpdate();
        request.setId(link.getId());
        request.setUrl(link.getLink());
        request.setDescription(description);
        request.setTgChatIds(linkService.getChatsForLink(link).stream().map(TgChat::getTgChatId).toList());
        template.convertAndSend(queue.getName(), request);
    }
}
