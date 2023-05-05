package ru.tinkoff.edu.service;

import ru.tinkoff.edu.model.Bot;
import ru.tinkoff.edu.request.LinkUpdate;

public class HttpLinkUpdateReceiver extends AbstractLinkUpdateReceiver {
    public HttpLinkUpdateReceiver(Bot bot) {
        super(bot);
    }

    @Override
    public void receiveUpdate(LinkUpdate request) {
        this.sendUpdates(request);
    }
}
