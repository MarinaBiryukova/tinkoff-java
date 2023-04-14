package ru.tinkoff.edu.service;

import ru.tinkoff.edu.repository.jdbc.dto.Link;

public interface LinkUpdater {
    void update(Link link);
}
