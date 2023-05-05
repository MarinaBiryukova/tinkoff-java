package ru.tinkoff.edu.service;

import lombok.AllArgsConstructor;
import ru.tinkoff.edu.model.Bot;
import ru.tinkoff.edu.request.LinkUpdate;

@AllArgsConstructor
public abstract class AbstractLinkUpdateReceiver {
    private Bot bot;

    public abstract void receiveUpdate(LinkUpdate request);

    protected void sendUpdates(LinkUpdate request) {
        bot.sendMessages(request.getDescription(), request.getTgChatIds());
    }
}
