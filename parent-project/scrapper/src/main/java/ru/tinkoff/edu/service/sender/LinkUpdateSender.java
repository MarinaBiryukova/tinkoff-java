package ru.tinkoff.edu.service.sender;

import ru.tinkoff.edu.repository.jdbc.dto.Link;

public interface LinkUpdateSender {
    void sendUpdate(Link link, String description);
}
